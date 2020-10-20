package hoau.com.cn.service.mapper;

import hoau.com.cn.entity.Label;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Description: 件信息mapper处理
 * @Author: zhaowei
 * @Date: 2020/10/19
 * @Time: 16:42
 */
public class LabelMapper extends Mapper<LongWritable, Label, Text, Label> {

    Text k = new Text();

    /**
     * map方法是提供给map task进程来调用的，map task进程是每读取一行文本来调用一次我们自定义的map方法
     * map task在调用map方法时，传递的参数：
     * 一行的起始偏移量LongWritable作为key
     * 一行的文本内容Text作为value
     */
    @Override
    protected void map(LongWritable key, Label value, Context context) throws IOException, InterruptedException {
//        k.set(value.getUNLDPLANID() == null ? "" : value.getUNLDPLANID().trim() + value.getUNLDPLANID() == null ? "" : value.getCSGNID().trim() +
//                value.getCSGNID() == null ? "" : value.getCSGNID().trim());
//        System.out.println("key:" + k + ",value:" + value);
        context.write(k, value);
    }

}
