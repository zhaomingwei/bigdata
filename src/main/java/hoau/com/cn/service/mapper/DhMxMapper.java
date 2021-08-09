package hoau.com.cn.service.mapper;

import com.google.gson.Gson;
import hoau.com.cn.common.Constant;
import hoau.com.cn.entity.FhMxAndDhMx;
import hoau.com.cn.entity.Label;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @Description: 发货单明细看板数据mapper处理
 * @Author: zhaowei
 * @Date: 2020/10/19
 * @Time: 16:42
 */
public class DhMxMapper extends Mapper<LongWritable, Text, Text, Text> {

    Text k = new Text();
    FhMxAndDhMx fhMxAndDhMx = new FhMxAndDhMx();
    Label label = new Label();
    Gson gson = new Gson();
    StringBuilder sb;

    /**
     * map方法是提供给map task进程来调用的，map task进程是每读取一行文本来调用一次我们自定义的map方法
     * map task在调用map方法时，传递的参数：
     * 一行的起始偏移量LongWritable作为key
     * 一行的文本内容Text作为value
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) {
        String val = value.toString().trim();
        sb = new StringBuilder();
        try {
            if (StringUtils.isNotBlank(val) && val.startsWith(Constant.FHMXANDDHMX)) {
                fhMxAndDhMx = gson.fromJson(val.replaceAll(Constant.FHMXANDDHMX, ""), FhMxAndDhMx.class);
                sb.append(fhMxAndDhMx.getYDBH() == null ? "" : fhMxAndDhMx.getYDBH().trim());
                sb.append(fhMxAndDhMx.getZXDH() == null ? "" : fhMxAndDhMx.getZXDH().trim());
                k.set(sb.toString());
            } else if (StringUtils.isNotBlank(val) && val.startsWith(Constant.LABEL)) {
                label = gson.fromJson(val.replaceAll(Constant.LABEL, ""), Label.class);
                sb.append(label.getCSGNID() == null ? "" : label.getCSGNID().trim());
                sb.append(label.getPLANID() == null ? "" : label.getPLANID().trim());
                k.set(sb.toString());
            }
//            System.out.println("DhMxMapper->:key:" + key + ", value:" + val);
            context.write(k, value);
        }catch (Exception e){
            System.out.println("DhMxMapper-exception:" + e);
        }
    }

}
