package hoau.com.cn.service.format;

import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.NullWritable;
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
public class FilterRecordWriteWithNull extends RecordWriter<NullWritable, Text> {

    private FSDataOutputStream outPath;

    /**
     * 将job通过参数传递过来
     **/
    public void init(TaskAttemptContext taskAttemptContext) {
        final String output_date_dir = taskAttemptContext.getConfiguration().get("output_date_dir");
        final String output_name = taskAttemptContext.getConfiguration().get("output_name");
        String outDir = taskAttemptContext.getConfiguration().get(FileOutputFormat.OUTDIR);
        System.out.println("outDir:" + outDir);
        System.out.println("output_date_dir:" + output_date_dir);
        System.out.println("output_name:" + output_name);
        try {
            Path path = new Path(outDir + "/" + output_date_dir + "/" + output_name);
            FileSystem fileSystem = path.getFileSystem(taskAttemptContext.getConfiguration());
            outPath = fileSystem.create(path, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FilterRecordWriteWithNull() {
    }

    public FilterRecordWriteWithNull(FSDataOutputStream outPath) {
        this.outPath = outPath;
    }

    @Override
    public void write(NullWritable key, Text value) throws IOException {
        //换行
//        String out = key.toString() + " " + value.toString() + "\r\n";
        String out = value.toString() + "\r\n";
        outPath.write(out.getBytes());
    }

    @Override
    public void close(TaskAttemptContext context) {
        //关流
        IOUtils.closeStream(outPath);
    }

}
