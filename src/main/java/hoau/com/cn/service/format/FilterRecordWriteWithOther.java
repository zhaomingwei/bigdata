package hoau.com.cn.service.format;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020/9/28
 * @Time: 16:47
 */
public class FilterRecordWriteWithOther extends RecordWriter<Text, Text> {

    private FSDataOutputStream outPath;

    /**
     * 将job通过参数传递过来
     **/
    public void init(TaskAttemptContext taskAttemptContext) {
        final String baseDir = taskAttemptContext.getConfiguration().get("baseDir");
        final String outName = taskAttemptContext.getConfiguration().get("outName");
        System.out.println("baseDir:" + baseDir);
        System.out.println("outName:" + outName);
        try {
            Path path = new Path(baseDir + outName);
            FileSystem fileSystem = path.getFileSystem(taskAttemptContext.getConfiguration());
            outPath = fileSystem.create(path, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FilterRecordWriteWithOther() {
    }

    public FilterRecordWriteWithOther(FSDataOutputStream outPath) {
        this.outPath = outPath;
    }

    @Override
    public void write(Text key, Text value) throws IOException {
        //换行
        String out = value.toString() + "\r\n";
        outPath.write(out.getBytes());
    }

    @Override
    public void close(TaskAttemptContext context) {
        //关流
        IOUtils.closeStream(outPath);
    }

}
