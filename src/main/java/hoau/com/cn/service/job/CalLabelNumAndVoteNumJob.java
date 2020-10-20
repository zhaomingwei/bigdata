package hoau.com.cn.service.job;

import hoau.com.cn.entity.Label;
import hoau.com.cn.entity.Vote;
import hoau.com.cn.service.format.FilterOutputFormatWithNull;
import hoau.com.cn.service.mapper.LabelAndVoteMapper;
import hoau.com.cn.service.mapper.LabelMapper;
import hoau.com.cn.service.mapper.VoteMapper;
import hoau.com.cn.service.reducer.LabelAndVoteReducer;
import hoau.com.cn.service.reducer.LabelReducer;
import hoau.com.cn.service.reducer.VoteReducer;
import hoau.com.cn.utils.Config;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.http.client.utils.DateUtils;

import java.util.Date;

/**
 * @Description: 统计标签数
 * @Author: zhaowei
 * @Date: 2020/10/16
 * @Time: 14：00
 */
public class CalLabelNumAndVoteNumJob {

    public static void main(String[] args) throws Exception {
        System.out.println("开始时间:" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        Configuration configuration = new Configuration();
        int res = ToolRunner.run(configuration, new CalLabelNumAndVoteNumTool(), args);
        System.out.println("结束时间:" + DateUtils.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
        System.exit(res);
    }

    static class CalLabelNumAndVoteNumTool extends Configured implements Tool {

        @Override
        public int run(String[] args) throws Exception {
            JobConf conf1 = new JobConf(CalLabelNumAndVoteNumTool.class);
            System.out.println("模式：" + conf1.get("mapreduce.framework.name"));

            String output_date_dir = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            conf1.set("output_date_dir", output_date_dir);
            conf1.set("output_name", Config.getProperty("label.outputName"));
            String baseDir = Config.getProperty("baseDir") + Config.getProperty("label.dir") + "/" + output_date_dir;

            //注意这行代码放在最前面，进行初始化，否则会报
            DBConfiguration.configureDB(conf1, Config.getProperty("driver.class"), Config.getProperty("tm.driver.url"),
                    Config.getProperty("tm.driver.username"), Config.getProperty("tm.driver.password"));

            /**自定义的mapper输出类型的key、value类型要和自定义的reduce的输入的key、value类型对应上，这里都是Text, StationDetail**/
            /**reduce最后要输出到自定义的文件里，类型也必须要和自定义的FilterRecordWrite类继承的RecordWriter<Text, Text>类型一致且重写的write方法类型也要保持一致,否则不能输出到文件**/
            /**Job任务**/
            Job job1 = new Job(conf1, "GET_LABEL");
            /**设置输入格式为:从数据库获取数据**/
            job1.setInputFormatClass(DBInputFormat.class);
            /**设置map输出key、value的类型,即：自定义的StationDetailMapper中的定义的泛型Mapper<LongWritable, StationDetail, Text, StationDetail>后两个类型**/
            job1.setMapOutputKeyClass(Text.class);
            job1.setMapOutputValueClass(Label.class);
            /**设置mapper和reduce的处理类**/
            job1.setMapperClass(LabelMapper.class);
            job1.setReducerClass(LabelReducer.class);
            /**设置reducer输出key的类型,即：自定义的StationDetailReducer中的定义的泛型Reducer<Text, StationDetail, Text, Text>后两个类型**/
            job1.setOutputKeyClass(Text.class);
            /**设置输出文件格式，实现自定义文件名称**/
            job1.setOutputFormatClass(FilterOutputFormatWithNull.class);

            /**设置数据库SQL语句,SQL结尾不能问添加; ，否则不能成功执行**/
            String sql = "SELECT PDA.LBLID," +
                    "       PDA.COUNT," +
                    "       PDA.SCANDATE," +
                    "       PDA.UNLDPLANID," +
                    "       PDA.CSGNID," +
                    "       PDA.USRNAME" +
                    "  FROM TBLSCANMSG PDA" +
                    " WHERE PDA.SCANTYPE = '2'" +
                    "   AND PDA.SCANDATE > (SYSDATE - 5 / 1440)";
            String count = "select count(1) from (" +
                    "SELECT PDA.LBLID," +
                    "       PDA.COUNT," +
                    "       PDA.SCANDATE," +
                    "       PDA.UNLDPLANID," +
                    "       PDA.CSGNID," +
                    "       PDA.USRNAME" +
                    "  FROM TBLSCANMSG PDA" +
                    " WHERE PDA.SCANTYPE = '2'" +
                    "   AND PDA.SCANDATE > (SYSDATE + 10000))";

            DBInputFormat.setInput(job1, Label.class, sql, count);
            FileOutputFormat.setOutputPath(job1, new Path(baseDir));

            //job1加入控制器
            ControlledJob ctrlJob1 = new ControlledJob(conf1);
            ctrlJob1.setJob(job1);

            JobConf conf2 = new JobConf(CalLabelNumAndVoteNumTool.class);
            conf2.set("output_date_dir", output_date_dir);
            conf2.set("output_name", Config.getProperty("vote.outputName"));
            String vBaseDir = Config.getProperty("baseDir") + Config.getProperty("vote.dir") + "/" + output_date_dir;

            //注意这行代码放在最前面，进行初始化，否则会报
            DBConfiguration.configureDB(conf2, Config.getProperty("driver.class"), Config.getProperty("driver.url"),
                    Config.getProperty("driver.username"), Config.getProperty("driver.password"));

            /**自定义的mapper输出类型的key、value类型要和自定义的reduce的输入的key、value类型对应上，这里都是Text, StationDetail**/
            /**reduce最后要输出到自定义的文件里，类型也必须要和自定义的FilterRecordWrite类继承的RecordWriter<Text, Text>类型一致且重写的write方法类型也要保持一致,否则不能输出到文件**/
            /**Job任务**/
            Job job2 = new Job(conf2, "GET_VOTE");
            /**设置输入格式为:从数据库获取数据**/
            job2.setInputFormatClass(DBInputFormat.class);
            /**设置map输出key、value的类型,即：自定义的StationDetailMapper中的定义的泛型Mapper<LongWritable, StationDetail, Text, StationDetail>后两个类型**/
            job2.setMapOutputKeyClass(Text.class);
            job2.setMapOutputValueClass(Vote.class);
            /**设置mapper和reduce的处理类**/
            job2.setMapperClass(VoteMapper.class);
            job2.setReducerClass(VoteReducer.class);
            /**设置reducer输出key的类型,即：自定义的StationDetailReducer中的定义的泛型Reducer<Text, StationDetail, Text, Text>后两个类型**/
            job2.setOutputKeyClass(Text.class);
            /**设置输出文件格式，实现自定义文件名称**/
            job2.setOutputFormatClass(FilterOutputFormatWithNull.class);

            /**设置数据库SQL语句,SQL结尾不能问添加; ，否则不能成功执行**/
            String sql2 = "SELECT FH.FCBH," +
                    "       TRIM(QK.CPH) CPH," +
                    "       FH.SCGSBH SHGS," +
                    "       FH.GSBH XHGS," +
                    "       FH.XCDH ZXDH," +
                    "       QK.GSBH FCGS," +
                    "       QK.MDDGS DCGS," +
                    "       MX.JHCKJS," +
                    "       MX.SJCKJS," +
                    "       MX.YDBH," +
                    "       YD.ZL," +
                    "       YD.JS," +
                    "       YD.TJ" +
                    "  FROM HYDATA.LD_FHD FH" +
                    "  LEFT JOIN HYDATA.LD_FHCLQK QK" +
                    "    ON QK.FCBH = FH.FCBH" +
                    "  LEFT JOIN HYDATA.LD_FHDMX MX" +
                    "    ON MX.FHDBH = FH.FHDBH" +
                    "  LEFT JOIN HYDATA.LD_YD YD" +
                    "    ON YD.YDBH = MX.YDBH" +
                    " WHERE FH.DDSJ >= TRUNC(SYSDATE) - 1" +
                    "   AND FH.ZT = 2" +
                    "   AND QK.ZCLX NOT IN (1, 2, 6, 12, 14)";
            String count2 = "select count(1) from (" +
                    "SELECT FH.FCBH," +
                    "       TRIM(QK.CPH) CPH," +
                    "       FH.SCGSBH SHGS," +
                    "       FH.GSBH XHGS," +
                    "       FH.XCDH ZXDH," +
                    "       QK.GSBH FCGS," +
                    "       QK.MDDGS DCGS," +
                    "       MX.JHCKJS," +
                    "       MX.SJCKJS," +
                    "       MX.YDBH," +
                    "       YD.ZL," +
                    "       YD.JS," +
                    "       YD.TJ" +
                    "  FROM HYDATA.LD_FHD FH" +
                    "  LEFT JOIN HYDATA.LD_FHCLQK QK" +
                    "    ON QK.FCBH = FH.FCBH" +
                    "  LEFT JOIN HYDATA.LD_FHDMX MX" +
                    "    ON MX.FHDBH = FH.FHDBH" +
                    "  LEFT JOIN HYDATA.LD_YD YD" +
                    "    ON YD.YDBH = MX.YDBH" +
                    " WHERE FH.DDSJ >= TRUNC(SYSDATE) - 1" +
                    "   AND FH.ZT = 2" +
                    "   AND QK.ZCLX NOT IN (1, 2, 6, 12, 14))";

            DBInputFormat.setInput(job2, Vote.class, sql2, count2);
            FileOutputFormat.setOutputPath(job2, new Path(vBaseDir));

            //job1加入控制器
            ControlledJob ctrlJob2 = new ControlledJob(conf2);
            ctrlJob2.setJob(job2);

            boolean flag1 = job1.waitForCompletion(true);
            boolean flag2 = job2.waitForCompletion(true);

            if(flag1 && flag2){
                JobConf conf3 = new JobConf(CalLabelNumAndVoteNumTool.class);
                System.out.println("111111:" + baseDir + "/" + conf1.get("output_date_dir") + "/" + conf1.get("output_name"));
                System.out.println("222222:" + vBaseDir + "/" + conf2.get("output_date_dir") + "/" + conf2.get("output_name"));
                Job job3 = new Job(conf3, "all");
                job3.setInputFormatClass(FileInputFormat.class);
                /**可针对不同输入路径指定不同的Mapper，故可以指定不同Mapper处理不同类型的文件：**/
                FileInputFormat.addInputPath(job3, new Path(baseDir + "/" + conf1.get("output_date_dir") + "/" + conf1.get("output_name")));
                FileInputFormat.addInputPath(job3, new Path(vBaseDir + "/" + conf2.get("output_date_dir") + "/" + conf2.get("output_name")));
                FileOutputFormat.setOutputPath(job3, new Path(vBaseDir + "/" + conf2.get("output_date_dir") + "/" + "out.log"));
                /**设置map输出key、value的类型,即：自定义的StationDetailMapper中的定义的泛型Mapper<LongWritable, StationDetail, Text, StationDetail>后两个类型**/
                job3.setMapOutputKeyClass(Text.class);
                job3.setMapOutputValueClass(Text.class);
                /**设置mapper和reduce的处理类**/
                job3.setMapperClass(LabelAndVoteMapper.class);
                job3.setReducerClass(LabelAndVoteReducer.class);
                /**设置reducer输出key的类型,即：自定义的StationDetailReducer中的定义的泛型Reducer<Text, StationDetail, Text, Text>后两个类型**/
                job3.setOutputKeyClass(Text.class);
                /**设置输出文件格式，实现自定义文件名称**/
                job3.setOutputFormatClass(FilterOutputFormatWithNull.class);
//            return 0;
            }
            return 0;
        }
    }

}
