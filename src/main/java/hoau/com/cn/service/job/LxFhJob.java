package hoau.com.cn.service.job;

import hoau.com.cn.service.format.FilterOutputFormat;
import hoau.com.cn.service.mapper.LxFhMapper;
import hoau.com.cn.service.reducer.LxFhReducer;
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
public class LxFhJob {

    public static void main(String[] args) throws Exception {
        long startTime = System.currentTimeMillis();
        System.out.println("效率监控看板-发货MR聚合开始时间:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        Configuration configuration = new Configuration();
        int res = ToolRunner.run(configuration, new LxFhTool(), args);
        System.out.println("效率监控看板-发货MR聚合结束时间:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        long endTime = System.currentTimeMillis();
        System.out.println("效率监控看板-发货MR聚合用时:<" + (endTime - startTime) / 1000 + ">秒");
        System.exit(res);
    }

    static class LxFhTool extends Configured implements Tool {

        @Override
        public int run(String[] args) throws Exception {
            JobConf conf = new JobConf(LxFhTool.class);
            System.out.println("模式：" + conf.get("mapreduce.framework.name"));

            String dateStr = DateUtils.formatDateToString(new Date(), "yyyyMMdd");
            System.out.println("run:" + Arrays.toString(args));
            /**
             * @Desc 第一个参数：输出目标文件HDFS路径 如：hdfs://cluster/report/statistics/dhmxTable
             *       第二个参数：输出目标文件类型 如：.json
             *       第三个参数：输出目标文件中产生的临时文件存放路径 如：hdfs://cluster/report/dhmx
             *       第四个参数：计划发车时间需要用到的时间数据文件存放路径 如：hdfs://cluster/report/vehicle/arrival/tmp/timeline.josn
             *       第五个参数：发货信息文件路径 如：hdfs://cluster/report/visualization/lxfh/lxfh.json
             * 参数不能小于5个
             */
            if (args.length < 5) {
                System.out.println("参数不为空,入参不能少于5个");
                return 1;
            }
            /**最终目标路径：前缀+日期+日期.json**/
            String targetDir = args[0] + "/" + dateStr + "/" + dateStr;
            /**文件格式**/
            String outName = args[1];
            /**临时目录**/
            String tmpTargetDir = args[2] + "/" + dateStr;
            /**设置此目录是为了拦截时输出到自定义文件里**/
            conf.set("baseDir", targetDir);
            /**设置此目录是为了拦截时输出到自定义文件里**/
            conf.set("outName", outName);

            Job job = new Job(conf, "LxFh_Job");
            /**设置map输出key、value的类型,即：自定义的FhMxAndDhMxMapper中的定义的泛型Mapper<LongWritable, Text, Text, Text>后两个类型**/
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            /**设置mapper和reduce的处理类**/
            job.setMapperClass(LxFhMapper.class);
            job.setReducerClass(LxFhReducer.class);
            /**设置reducer输出key的类型,即：自定义的FhMxAndDhMxReducer中的定义的泛型Reducer<Text, Text, Text, Text>后两个类型**/
            /**reducer的前两个类型要与mapper的后两个类型一致**/
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            job.setOutputFormatClass(FilterOutputFormat.class);
            FileOutputFormat.setOutputPath(job, new Path(tmpTargetDir));

            /**可针对不同输入路径指定不同的Mapper，故可以指定不同Mapper处理不同类型的文件：**/
            for (int i = 0; i < args.length; i++) {
                if (i < 3) {
                    continue;
                }
                FileInputFormat.addInputPath(job, new Path(args[i]));
            }

            boolean flag = job.waitForCompletion(true);
            if (flag) {
                /**删除mr生成的_SUCCESS及其路径、删除三个输入文件**/
                Path path = new Path(tmpTargetDir);
                FileSystem fs = path.getFileSystem(conf);
                fs.delete(path, true);
            }
            return flag ? 0 : 1;
        }
    }

}
