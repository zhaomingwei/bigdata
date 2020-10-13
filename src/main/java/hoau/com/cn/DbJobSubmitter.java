package hoau.com.cn;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020/9/27
 * @Time: 10:41
 */
public class DbJobSubmitter {

    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
        Configuration conf = new Configuration();
        Job wordCountJob = Job.getInstance(conf);

        //重要：指定本job所在的jar包
        wordCountJob.setJarByClass(DbJobSubmitter.class);

        //设置wordCountJob所用的mapper逻辑类为哪个类
        wordCountJob.setMapperClass(DbMapper.class);
        //设置wordCountJob所用的reducer逻辑类为哪个类
        wordCountJob.setReducerClass(DbReducer.class);

        //设置map阶段输出的kv数据类型
        wordCountJob.setMapOutputKeyClass(Text.class);
        wordCountJob.setMapOutputValueClass(IntWritable.class);

        //设置最终输出的kv数据类型
        wordCountJob.setOutputKeyClass(Text.class);
        wordCountJob.setOutputValueClass(IntWritable.class);

        //设置要处理的文本数据所存放的路径
        FileInputFormat.setInputPaths(wordCountJob, new Path("/wordcount/srcdata/"));
        FileOutputFormat.setOutputPath(wordCountJob, new Path("/wordcount/output/"));

        //提交job给hadoop集群
        wordCountJob.waitForCompletion(true);
    }

}
