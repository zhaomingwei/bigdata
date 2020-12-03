package hoau.com.cn.service.reducer;

import com.google.gson.Gson;
import hoau.com.cn.common.Constant;
import hoau.com.cn.entity.FhMxAndDhMx;
import hoau.com.cn.entity.FhMxAndDhMxOut;
import hoau.com.cn.entity.Label;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.util.ArrayList;
import java.util.List;

/**
 * @Description: 发货单明细看板数据reducer处理
 * @Author: zhaowei
 * @Date: 2020/10/19
 * @Time: 16:48
 */
public class FhMxOneReducer extends Reducer<Text, Text, Text, Text> {

    Gson gson = new Gson();
    //到货看板数据
    FhMxAndDhMxOut fhMxAndDhMxOut;
    //发货明细
    FhMxAndDhMx fhMxAndDhMx;
    //件信息
    Label label;

    @Override
    protected void reduce(Text key, Iterable<Text> value, Context context) {
        try {
            List<FhMxAndDhMxOut> tmp1 = new ArrayList();
            List<FhMxAndDhMxOut> tmp2 = new ArrayList();
            boolean labelFlag = false;
            String tag = "0";
            for (Text item : value) {
                String val = item.toString().trim();
                fhMxAndDhMxOut = new FhMxAndDhMxOut();
                if (StringUtils.isNotBlank(val) && val.startsWith(Constant.FHMXANDDHMX)) {
                    fhMxAndDhMx = gson.fromJson(val.replaceAll(Constant.FHMXANDDHMX, ""), FhMxAndDhMx.class);
                    if (StringUtils.equals("1", fhMxAndDhMx.getTAG())) {
                        if (!StringUtils.equals("1", tag)) {
                            tag = "1";
                        }
                    }
                    BeanUtils.copyProperties(fhMxAndDhMxOut, fhMxAndDhMx);
                    tmp1.add(fhMxAndDhMxOut);
                } else if (StringUtils.isNotBlank(val) && val.startsWith(Constant.LABEL)) {
                    //如果该次value集合里有label类型说明通过运单号+装卸单号关联上了发货单明细与标签扫描，需要写入HDFS
                    if (!labelFlag) {
                        labelFlag = true;
                    }
                    label = gson.fromJson(val.replaceAll(Constant.LABEL, ""), Label.class);
                    BeanUtils.copyProperties(fhMxAndDhMxOut, label);
                    tmp2.add(fhMxAndDhMxOut);
                }
            }
            //如果该次value集合里有label类型说明通过运单号+装卸单号关联上了发货单明细与标签扫描，需要写入HDFS
            if (labelFlag) {
                if (!tmp1.isEmpty() && !tmp2.isEmpty()) {
                    for (FhMxAndDhMxOut f1 : tmp1) {
                        for (FhMxAndDhMxOut f2 : tmp2) {
                            f1.setLBLID(f2.getLBLID());
                            f1.setRCVMSGDATE(f2.getRCVMSGDATE());
                            f1.setSCANDATE(f2.getSCANDATE());
//                            System.out.println("reduce->:key:" + key + ", value:" + f1.toString());
                            context.write(key, new Text(f1.toString()));
                        }
                    }
                }
            } else{//没有匹配上的且tag为1（1分钟及之内的数据：新增的到车但未卸货没有件扫描信息）
                if (StringUtils.equals("1", tag) && !tmp1.isEmpty()) {
                    for (FhMxAndDhMxOut f : tmp1) {
                        f.setLBLID("");
                        f.setRCVMSGDATE("");
                        f.setSCANDATE("");
//                        System.out.println("reduce->:key:" + key + ", value:" + f.toString());
                        context.write(key, new Text(f.toString()));
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("FhMxAndDhMxReducer->exception:" + e);
        }
    }

}
