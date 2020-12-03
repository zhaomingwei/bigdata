package hoau.com.cn.service.job;

import hoau.com.cn.service.format.FilterOutputFormatWithOther;
import hoau.com.cn.service.mapper.FhMxMapper;
import hoau.com.cn.service.mapper.FhMxOneMapper;
import hoau.com.cn.service.reducer.FhMxOneReducer;
import hoau.com.cn.service.reducer.FhMxReducer;
import hoau.com.cn.utils.DateUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.mapreduce.lib.output.LazyOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;

import java.util.Arrays;
import java.util.Date;

/**
 * @Description: 效率监控看板-发货Job
 * @Author: zhaowei
 * @Date: 2020/11/27
 * @Time: 13:29
 */
public class FhMxJob {

    public static void main(String[] args) throws Exception {
        System.out.println("效率监控看板-发货开始时间:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        Configuration configuration = new Configuration();
        int res = ToolRunner.run(configuration, new FhMxTool(), args);
        System.out.println("效率监控看板-发货结束时间:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        System.exit(res);
    }

    static class FhMxTool extends Configured implements Tool {

        @Override
        public int run(String[] args) throws Exception {
            JobConf conf = new JobConf(FhMxTool.class);
            System.out.println("模式：" + conf.get("mapreduce.framework.name"));

            String dateStr = DateUtils.formatDateToString(new Date(), "yyyyMMddHHmmss");
            System.out.println("run:" + Arrays.toString(args));
            /**
             * @Desc 第一个参数：输出目标文件HDFS路径 如：hdfs://master:9000/report/statistics/dhmxTable
             *       第二个参数：输出目标文件类型 如：.txt
             *       第三个参数：输出目标文件中产生的临时文件存放路径 如：hdfs://master:9000/report/dhmx/tmp
             *       第四个参数：计划发车时间需要用到的时间数据文件存放路径 如：hdfs://master:9000/report/vehicle/arrival/tmp/timeline.josn
             *       第五个参数：件扫描信息文件路径 如：hdfs://master:9000/report/visualization/dhLabel/dhLabel.txt
             *       第六个参数：输入文件路径
             *       ... : 多个输入文件路径
             * 参数不能小于6个
             */
            if (args.length < 6) {
                System.out.println("参数不为空,入参不能少于6个");
                return 1;
            }
            /**最终目标路径**/
            String targetDir = args[0];
            /**临时目录**/
            String tmpTargetDir = args[2];
            /**第一个job输出地址**/
            String tmp1TargetDir = tmpTargetDir + "/tmp1/" + dateStr;
            /**第二个job输出地址**/
            String tmp2TargetDir = tmpTargetDir + "/tmp2/" + dateStr;
            /**文件格式**/
            String outName = args[1];
            /**车辆时刻文件**/
            String timeLine = args[3];
            /**设置此目录是为了拦截时输出到自定义文件里**/
            conf.set("baseDir", tmp1TargetDir);
            /**设置此目录是为了拦截时输出到自定义文件里**/
            conf.set("outName", outName);

            Job job = new Job(conf, "FhMx_Tmp_Job");
            /**设置map输出key、value的类型,即：自定义的FhMxAndDhMxMapper中的定义的泛型Mapper<LongWritable, Text, Text, Text>后两个类型**/
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            /**设置mapper和reduce的处理类**/
            job.setMapperClass(FhMxOneMapper.class);
            job.setReducerClass(FhMxOneReducer.class);
            /**设置reducer输出key的类型,即：自定义的FhMxAndDhMxReducer中的定义的泛型Reducer<Text, Text, Text, Text>后两个类型**/
            /**reducer的前两个类型要与mapper的后两个类型一致**/
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            job.setOutputFormatClass(FilterOutputFormatWithOther.class);
            FileOutputFormat.setOutputPath(job, new Path(tmp1TargetDir));

            /**可针对不同输入路径指定不同的Mapper，故可以指定不同Mapper处理不同类型的文件：**/
            for(int i=0;i<args.length;i++){
                if(i < 3){
                    continue;
                }
                FileInputFormat.addInputPath(job, new Path(args[i]));
            }

            boolean flag = job.waitForCompletion(true);
            boolean flag2 = false;

            if (flag) {
                JobConf conf2 = new JobConf(FhMxTool.class);
                /**设置此目录是为了拦截时输出到自定义文件里**/
                conf2.set("baseDir", targetDir + "/" + dateStr);
                /**设置此目录是为了拦截时输出到自定义文件里**/
                conf2.set("outName", outName);
                Job job2 = new Job(conf2, "FhMx_Job");
                job2.setMapOutputKeyClass(Text.class);
                job2.setMapOutputValueClass(Text.class);
                /**设置mapper和reduce的处理类**/
                job2.setMapperClass(FhMxMapper.class);
                job2.setReducerClass(FhMxReducer.class);
                job2.setOutputKeyClass(Text.class);
                /**LazyOutputFormat-避免输出空文件, FilterOutputFormatWithOther-设置输出文件格式，实现自定义文件名称**/
                LazyOutputFormat.setOutputFormatClass(job2, FilterOutputFormatWithOther.class);
                /**可针对不同输入路径指定不同的Mapper，故可以指定不同Mapper处理不同类型的文件：**/
                FileInputFormat.addInputPath(job2, new Path(tmp1TargetDir + outName));
                FileInputFormat.addInputPath(job2, new Path(timeLine));
                /**LazyOutputFormat-避免输出空文件, FilterOutputFormatWithOther-设置输出文件格式，实现自定义文件名称**/
                LazyOutputFormat.setOutputFormatClass(job2, FilterOutputFormatWithOther.class);
                FileOutputFormat.setOutputPath(job2, new Path(tmp2TargetDir));
                flag2 = job2.waitForCompletion(true);
                if(flag2){
                    /**删除mr生成的_SUCCESS及其路径、删除三个输入文件**/
                    Path path1 = new Path(tmp1TargetDir);
                    FileSystem fs1 = path1.getFileSystem(conf);
                    fs1.delete(path1, true);

                    Path path2 = new Path(tmp2TargetDir);
                    FileSystem fs2 = path2.getFileSystem(conf2);
                    fs2.delete(path2, true);
                }
            }
            return flag2 ? 0 : 1;
        }
    }

}
