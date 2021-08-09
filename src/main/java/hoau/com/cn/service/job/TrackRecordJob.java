package hoau.com.cn.service.job;

import hoau.com.cn.service.format.FilterOutputFormat;
import hoau.com.cn.service.mapper.TrackRecordOneMapper;
import hoau.com.cn.service.reducer.TrackRecordOneReducer;
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
 * @Description: 货物跟踪记录Job
 * @Date: 2020/11/27
 * @Time: 13:29
 */
public class TrackRecordJob {

    public static void main(String[] args) throws Exception {
        System.out.println("货物跟踪记录one开始时间:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        Configuration configuration = new Configuration();
        int res = ToolRunner.run(configuration, new TrackRecordTool(), args);
        System.out.println("货物跟踪记录结束时间:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        System.exit(res);
    }

    static class TrackRecordTool extends Configured implements Tool {

        @Override
        public int run(String[] args) throws Exception {
            JobConf conf = new JobConf(TrackRecordTool.class);
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
            /**文件格式**/
            String outName = args[1];
            /**车辆时刻文件**/
            String timeLine = args[3];
            /**设置此目录是为了拦截时输出到自定义文件里**/
            conf.set("baseDir", targetDir + "/" + "trackTable");
            /**设置此目录是为了拦截时输出到自定义文件里**/
            conf.set("outName", outName);

            Job job = new Job(conf, "TrackRecord_Tmp_Job");
            /**设置map输出key、value的类型,即：自定义的TrackRecordAndDhMxMapper中的定义的泛型Mapper<LongWritable, Text, Text, Text>后两个类型**/
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            /**设置mapper和reduce的处理类**/
            job.setMapperClass(TrackRecordOneMapper.class);
            job.setReducerClass(TrackRecordOneReducer.class);
            /**设置reducer输出key的类型,即：自定义的TrackRecordAndDhMxReducer中的定义的泛型Reducer<Text, Text, Text, Text>后两个类型**/
            /**reducer的前两个类型要与mapper的后两个类型一致**/
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            /**LazyOutputFormat-避免输出空文件, FilterOutputFormatWithOther-设置输出文件格式，实现自定义文件名称**/
            LazyOutputFormat.setOutputFormatClass(job, FilterOutputFormat.class);
            job.setOutputFormatClass(FilterOutputFormat.class);
            FileOutputFormat.setOutputPath(job, new Path(tmp1TargetDir));

            /**可针对不同输入路径指定不同的Mapper，故可以指定不同Mapper处理不同类型的文件：**/
            for(int i=0;i<args.length;i++){
                if(i < 3){
                    continue;
                }
                FileInputFormat.addInputPath(job, new Path(args[i]));
            }

            System.out.println("货物跟踪记录Start-four:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            System.out.println("删除指定目录"+tmp1TargetDir);
            boolean flag = job.waitForCompletion(true);
            System.out.println("货物跟踪记录Start-five:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            if (flag) {
                System.out.println("货物跟踪记录Start-six:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
                //**删除mr生成的_SUCCESS及其路径、删除三个输入文件**//*
                System.out.println("删除指定目录"+tmp1TargetDir);
                Path path1 = new Path(tmp1TargetDir);
                FileSystem fs1 = path1.getFileSystem(conf);
                fs1.delete(path1, true);
            }
            System.out.println("货物跟踪记录end时间:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
            return flag ? 0 : 1;
        }
    }

}
