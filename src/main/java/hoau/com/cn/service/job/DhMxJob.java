package hoau.com.cn.service.job;

import hoau.com.cn.service.format.FilterOutputFormatWithOther;
import hoau.com.cn.service.mapper.DhMxMapper;
import hoau.com.cn.service.reducer.DhMxReducer;
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
 * @Description: 到货明细Job
 * @Author: zhaowei
 * @Date: 2020/10/21
 * @Time: 13:46
 */
public class DhMxJob {

    public static void main(String[] args) throws Exception {
        System.out.println("处理到货明细看板数据开始时间:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        Configuration configuration = new Configuration();
        int res = ToolRunner.run(configuration, new DhMxTool(), args);
        System.out.println("处理到货明细看板数据结束时间:" + DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        System.exit(res);
    }

    static class DhMxTool extends Configured implements Tool {

        @Override
        public int run(String[] args) throws Exception {
            JobConf conf = new JobConf(DhMxTool.class);
            System.out.println("模式：" + conf.get("mapreduce.framework.name"));

            String dateStr = DateUtils.formatDateToString(new Date(), "yyyyMMddHHmmss");
            System.out.println("run:" + Arrays.toString(args));
            /**
             * @Desc 第一个参数：输出目标文件HDFS路径 如：hdfs://master:9000/report/statistics/dhmxTable
             *       第二个参数：输出目标文件类型 如：.txt
             *       第三个参数：输出目标文件中产生的临时文件存放路径 如：hdfs://master:9000/report/dhmx/tmp
             *       第四个参数：件扫描信息文件路径 如：hdfs://master:9000/report/visualization/dhLabel/dhLabel.txt
             *       第五个参数：输入文件路径
             *       ... : 多个输入文件路径
             * 参数不能小于5个
             */
            if (args.length < 5) {
                System.out.println("参数不为空,入参不能少于5个");
                return 1;
            }
            /**设置此目录是为了拦截时输出到自定义文件里**/
            conf.set("baseDir", args[0] + "/" + dateStr);
            /**设置此目录是为了拦截时输出到自定义文件里**/
            conf.set("outName", args[1]);

            Job job = new Job(conf, "FHMX_AND_DHMX_JOB");
            /**设置map输出key、value的类型,即：自定义的FhMxAndDhMxMapper中的定义的泛型Mapper<LongWritable, Text, Text, Text>后两个类型**/
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(Text.class);
            /**设置mapper和reduce的处理类**/
            job.setMapperClass(DhMxMapper.class);
            job.setReducerClass(DhMxReducer.class);
            /**设置reducer输出key的类型,即：自定义的FhMxAndDhMxReducer中的定义的泛型Reducer<Text, Text, Text, Text>后两个类型**/
            /**reducer的前两个类型要与mapper的后两个类型一致**/
            job.setOutputKeyClass(Text.class);
            job.setOutputValueClass(Text.class);
            /**LazyOutputFormat-避免输出空文件, FilterOutputFormatWithOther-设置输出文件格式，实现自定义文件名称**/
            LazyOutputFormat.setOutputFormatClass(job, FilterOutputFormatWithOther.class);

            /**可针对不同输入路径指定不同的Mapper，故可以指定不同Mapper处理不同类型的文件：**/
            String targetDir = "";
            for(int i=0;i<args.length;i++){
                if(i == 0 || i == 1){
                    continue;
                }
                if (i == 2) {
                    targetDir = args[i]+ "/" + dateStr;
                    FileOutputFormat.setOutputPath(job, new Path(targetDir));
                    continue;
                }
                FileInputFormat.addInputPath(job, new Path(args[i]));
            }

            boolean flag = job.waitForCompletion(true);

            if (flag) {
                /**删除mr生成的_SUCCESS及其路径、删除三个输入文件**/
                Path path = new Path(targetDir);
                FileSystem fs = path.getFileSystem(conf);
                fs.delete(path, true);
            }
            return flag ? 0 : 1;
        }
    }

}
