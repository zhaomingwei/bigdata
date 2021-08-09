package hoau.com.cn.service.job;

import hoau.com.cn.service.format.FilterOutputFormat;
import hoau.com.cn.service.mapper.ClxxDHMapper;
import hoau.com.cn.service.reducer.ClxxDHReduce;
import hoau.com.cn.utils.DateUtils;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat;

import java.util.Date;

/**
 * @author ：Joel.Li
 * @date ：2020/12/1 14:32
 * @description： description
 * @version: $version
 */
public class ClxxDHJob {

    public static void main(String args[]) throws Exception {
        System.out.println("ClxxDHJob记录Start-one:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        if (args.length < 4) {
            System.out.println("参数不为空,入参不能少于4个");
            return;
        }

        JobConf jobConf = new JobConf(ClxxDHJob.class);
        /**设置此目录是为了拦截时输出到自定义文件里**/
        jobConf.set("baseDir", args[0]);
        /**设置此目录是为了拦截时输出到自定义文件里**/
        jobConf.set("outName", args[1]);

        Job job = new Job(jobConf, "CLXX_DH_JOB");

        //装载driver
        job.setJarByClass(ClxxDHJob.class);

        //设置MR对应的类
        job.setMapperClass(ClxxDHMapper.class);
        job.setReducerClass(ClxxDHReduce.class);
        //设置输出k-v类型
        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        LazyOutputFormat.setOutputFormatClass(job, FilterOutputFormat.class);

        /**可针对不同输入路径指定不同的Mapper，故可以指定不同Mapper处理不同类型的文件：**/
        String targetDir = "";
        for (int i = 0; i < args.length; i++) {
            if (i == 0 || i == 1) {
                continue;
            }
            if (i == 2) {
                targetDir = args[i];
                FileOutputFormat.setOutputPath(job, new Path(targetDir));
                continue;
            }
            FileInputFormat.addInputPath(job, new Path(args[i]));
        }
        System.out.println("ClxxDHJob记录Start-two:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        boolean flag = job.waitForCompletion(true);
        System.out.println("ClxxDHJob记录Start-three:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        if (flag) {
            /**删除mr生成的_SUCCESS及其路径、删除三个输入文件**/
            System.out.println("ClxxDHJob记录Start-four:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            Path path = new Path(targetDir);
            FileSystem fs = path.getFileSystem(jobConf);
            fs.delete(path, true);
        }
        System.out.println("ClxxDHJob记录Start-five:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        return;
    }

}
