package hoau.com.cn.service.format;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

/**
 * @Description: 自定义FilterOutputFormat继承FileOutputFormat，实现其中的getRecordWriter方法；
 *  * 该方法返回一个RecordWriter对象，需先创建此对象，实现其中的write、close方法；
 *  * 文件通过FileSystem在write方法中写出到hdfs自定义文件中
 * @Author: zhaowei
 * @Date: 2020/9/28
 * @Time: 15:05
 */
public class FilterOutputFormat extends FileOutputFormat<Text, Text> {

    @Override
    public RecordWriter<Text, Text> getRecordWriter(TaskAttemptContext taskAttemptContext) {
        FilterRecordWrite frw = new FilterRecordWrite();
        frw.init(taskAttemptContext);
        return frw;
    }

}
