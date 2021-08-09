package hoau.com.cn.service.mapper;

import com.google.gson.Gson;
import hoau.com.cn.common.Constant;
import hoau.com.cn.entity.FhMxAndDhMx;
import hoau.com.cn.entity.FhMxAndDhMxOut;
import hoau.com.cn.entity.Label;
import hoau.com.cn.entity.TimeLineEntity;
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
public class FhMxMapper extends Mapper<LongWritable, Text, Text, Text> {

    Text k = new Text();
    FhMxAndDhMxOut fhMxAndDhMxOut = new FhMxAndDhMxOut();
    TimeLineEntity timeLineEntity = new TimeLineEntity();
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
            if (StringUtils.isNotBlank(val) && val.startsWith(Constant.FHMXANDDHMXOUT)) {
                fhMxAndDhMxOut = gson.fromJson(val.replaceAll(Constant.FHMXANDDHMXOUT, ""), FhMxAndDhMxOut.class);
                sb.append(fhMxAndDhMxOut.getNAMELINE() == null ? "" : fhMxAndDhMxOut.getNAMELINE().trim());
                k.set(sb.toString());
            } else if (StringUtils.isNotBlank(val) && val.startsWith(Constant.TIMELINEENTITY)) {
                timeLineEntity = gson.fromJson(val.replaceAll(Constant.TIMELINEENTITY, ""), TimeLineEntity.class);
                sb.append(timeLineEntity.getNameLine() == null ? "" : timeLineEntity.getNameLine().trim());
                k.set(sb.toString());
            }
//            System.out.println("FhMxMapper->:key:" + key + ", value:" + val);
            context.write(k, value);
        }catch (Exception e){
            System.out.println("FhMxMapper-exception:" + e);
        }
    }

}
