package hoau.com.cn.service.mapper;

import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @Description: 标签和票数mapper处理
 * @Author: zhaowei
 * @Date: 2020/10/16
 * @Time: 14：18
 * <p>
 * 四个泛型类型分别代表：
 * KeyIn        Mapper的输入数据的Key，这里是每行文字的起始位置（0,11,...）
 * ValueIn      Mapper的输入数据的Value，这里是每行记录
 * KeyOut       Mapper的输出数据的Key，
 * ValueOut     Mapper的输出数据的Value，这里是每行记录
 */
public class LabelAndVoteMapper extends Mapper<LongWritable, Text, Text, Text> {

    Text k = new Text();

    /**
     * map方法是提供给map task进程来调用的，map task进程是每读取一行文本来调用一次我们自定义的map方法
     * map task在调用map方法时，传递的参数：
     * 一行的起始偏移量LongWritable作为key
     * 一行的文本内容Text作为value
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        System.out.println("LabelAndVoteMapper-key:" + key + ",value:" + value);
        context.write(k, value);
    }

}