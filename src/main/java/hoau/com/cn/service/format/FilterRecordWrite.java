package hoau.com.cn.service.format;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataOutputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.RecordWriter;
import org.apache.hadoop.mapreduce.TaskAttemptContext;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;

/**
 * @Description: 自定义MR输出文件（追加或者新建）
 * @Author: zhaowei
 * @Date: 2020/9/28
 * @Time: 16:47
 */
public class FilterRecordWrite extends RecordWriter<Text, Text> {

    private FSDataOutputStream outPath;

    /**
     * 将job通过参数传递过来
     **/
    public void init(TaskAttemptContext taskAttemptContext) {
        final String baseDir = taskAttemptContext.getConfiguration().get("baseDir");
        final String outName = taskAttemptContext.getConfiguration().get("outName");
        System.out.println("baseDir:" + baseDir);
        System.out.println("outName:" + outName);
        Configuration conf = new Configuration();
        //FileSystem.get()方法如果传入的baseDir是一样的话，不设置此值，默认会从走缓存，缓存key是uri+conf : Key key = new Key(uri, conf);
        //设置此值代表每次都新建一个连接
        conf.set("fs.hdfs.impl.disable.cache", "true");
        conf.setBoolean("dfs.support.append", true);
        conf.setBoolean("dfs.client.block.write.replace-datanode-on-failure.enabled", true);
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
        try {
            Path path = new Path(baseDir + outName);
            FileSystem fileSystem = FileSystem.get(URI.create(baseDir), conf);
            //存在该文件，追加
            if(fileSystem.exists(path)){
                System.out.println("路径:<" + baseDir + outName + ">已存在,执行追加操作");
                outPath = fileSystem.append(new Path(baseDir + outName));
            } else {//不存在该文件创建
                System.out.println("路径:<" + baseDir + outName + ">不存在,执行创建操作");
                outPath = fileSystem.create(path, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public FilterRecordWrite() {
    }

    public FilterRecordWrite(FSDataOutputStream outPath) {
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
