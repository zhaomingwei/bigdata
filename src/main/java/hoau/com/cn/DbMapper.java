package hoau.com.cn;

import hoau.com.cn.demo.ToggRecord;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020/9/27
 * @Time: 10:36
 */
public class DbMapper extends Mapper<LongWritable, ToggRecord, LongWritable, Text> {

    /**
     * map方法是提供给map task进程来调用的，map task进程是每读取一行文本来调用一次我们自定义的map方法
     * map task在调用map方法时，传递的参数：
     * 一行的起始偏移量LongWritable作为key
     * 一行的文本内容Text作为value
     */
    @Override
    protected void map(LongWritable key, ToggRecord value, Context context) throws IOException, InterruptedException {
        context.write(new LongWritable(value.id), new Text(value.toString()));

        //拿到一行文本内容，转换成String 类型
        //String line = value.toString();
        //将这行文本切分成单词
        //String[] words = line.split(" ");

        //输出<单词，1>
        //for (String word : words) {
        //    context.write(new Text(word), new IntWritable(1));
        //}
    }

}
