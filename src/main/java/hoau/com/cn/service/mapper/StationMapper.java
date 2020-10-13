package hoau.com.cn.service.mapper;

import hoau.com.cn.entity.Station;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Description: 场站信息mapper处理
 * @Author: zhaowei
 * @Date: 2020/9/27
 * @Time: 17:14
 */
public class StationMapper extends Mapper<LongWritable, Station, LongWritable, Text> {

    LongWritable k = new LongWritable();
    Text v = new Text();
    /**
     * map方法是提供给map task进程来调用的，map task进程是每读取一行文本来调用一次我们自定义的map方法
     * map task在调用map方法时，传递的参数：
     * 一行的起始偏移量LongWritable作为key
     * 一行的文本内容Text作为value
     */
    @Override
    protected void map(LongWritable key, Station value, Context context) throws IOException, InterruptedException {
        k.set(value.getId());
        v.set(value.toString());
        context.write(k, v);
    }

}