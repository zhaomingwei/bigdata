package hoau.com.cn.service.job;

import hoau.com.cn.entity.StationDetail;
import hoau.com.cn.service.format.FilterOutputFormat;
import hoau.com.cn.service.mapper.StationDetailMapper;
import hoau.com.cn.service.reducer.StationDetailReducer;
import hoau.com.cn.utils.Config;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.conf.Configured;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.Tool;
import org.apache.hadoop.util.ToolRunner;
import org.apache.http.client.utils.DateUtils;

import java.util.Date;

/**
 * @Description: 统计标签数
 * @Author: zhaowei
 * @Date: 2020/10/16
 * @Time: 13:09
 */
public class CalLabelNumAndVoteNumJob {

    public static void main(String[] args) throws Exception {
        Configuration configuration = new Configuration();
        int res = ToolRunner.run(configuration, new CalLabelNumTool(), args);
        System.exit(res);
    }

    static class CalLabelNumTool extends Configured implements Tool {

        @Override
        public int run(String[] args) throws Exception {
            JobConf conf = new JobConf(CalLabelNumTool.class);

            String baseDir = Config.getProperty("baseDir") + Config.getProperty("label.num.dir");

            String date = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
            String output_date_dir = date;
            conf.set("output_date_dir", output_date_dir);
            conf.set("output_name",  Config.getProperty("label.num.outputName"));
            System.out.println("模式：" + conf.get("mapreduce.framework.name"));

            //注意这行代码放在最前面，进行初始化，否则会报
            DBConfiguration.configureDB(conf, Config.getProperty("driver.class"), Config.getProperty("driver.url"),
                    Config.getProperty("driver.username"), Config.getProperty("driver.password"));

            /**设置数据库SQL语句,SQL结尾不能问添加; ，否则不能成功执行**/
            String sql = "  SELECT GS.GSJC GSJC," +
                    "       Q.FCBH FCBH," +
                    "       MX.YDBH YDBH," +
                    "       NVL(YD.ZL, 10) WEIGHT," +
                    "       NVL(YD.TJ, 10) VOLUME," +
                    "       NVL(YD.JS, 10) NUM_GOODS," +
                    "       MX.JHCKJS NUM_GOODS_PLAN" +
                    "  FROM HYDATA.LD_FHD FHD" +
                    " INNER JOIN HYDATA.LD_FHCLQK Q" +
                    "    ON FHD.FCBH = Q.FCBH" +
                    "  LEFT JOIN HYDATA.LD_FHDMX MX" +
                    "    ON FHD.FHDBH = MX.FHDBH" +
                    "  LEFT JOIN HYDATA.LD_YD YD" +
                    "    ON MX.YDBH = YD.YDBH" +
                    "  LEFT JOIN HYDATA.LD_GS GS" +
                    "    ON GS.GSBH = FHD.SCGSBH" +
                    " WHERE Q.FCSJ >= to_date('2016-06-01', 'yyyy-MM-dd')" +
                    "   AND Q.FCSJ < to_date('2016-06-10', 'yyyy-MM-dd')";
//                "   AND GS.GSJC = 'N上海'";
            String count = "SELECT COUNT(1)" +
                    "  FROM HYDATA.LD_FHD FHD" +
                    " INNER JOIN HYDATA.LD_FHCLQK Q" +
                    "    ON FHD.FCBH = Q.FCBH" +
                    "  LEFT JOIN HYDATA.LD_FHDMX MX" +
                    "    ON FHD.FHDBH = MX.FHDBH" +
                    "  LEFT JOIN HYDATA.LD_YD YD" +
                    "    ON MX.YDBH = YD.YDBH" +
                    "  LEFT JOIN HYDATA.LD_GS GS" +
                    "    ON GS.GSBH = FHD.SCGSBH" +
                    " WHERE Q.FCSJ >= to_date('2016-06-01', 'yyyy-MM-dd')" +
                    "   AND Q.FCSJ < to_date('2016-06-10', 'yyyy-MM-dd')";
//                "   AND GS.GSJC = 'N上海'";

            /**自定义的mapper输出类型的key、value类型要和自定义的reduce的输入的key、value类型对应上，这里都是Text, StationDetail**/
            /**reduce最后要输出到自定义的文件里，类型也必须要和自定义的FilterRecordWrite类继承的RecordWriter<Text, Text>类型一致且重写的write方法类型也要保持一致,否则不能输出到文件**/
            /**Job任务**/
            Job job = new Job(conf, "GET_STATIONS_DETAIL");
            /**设置输入格式为:从数据库获取数据**/
            job.setInputFormatClass(DBInputFormat.class);
            /**设置map输出key、value的类型,即：自定义的StationDetailMapper中的定义的泛型Mapper<LongWritable, StationDetail, Text, StationDetail>后两个类型**/
            job.setMapOutputKeyClass(Text.class);
            job.setMapOutputValueClass(StationDetail.class);
            /**设置mapper和reduce的处理类**/
            job.setMapperClass(StationDetailMapper.class);
            job.setReducerClass(StationDetailReducer.class);
            /**设置reducer输出key的类型,即：自定义的StationDetailReducer中的定义的泛型Reducer<Text, StationDetail, Text, Text>后两个类型**/
            job.setOutputKeyClass(Text.class);
            /**设置输出文件格式，实现自定义文件名称**/
            job.setOutputFormatClass(FilterOutputFormat.class);

            DBInputFormat.setInput(job, StationDetail.class, sql, count);

            String dir = baseDir + conf.get("output_date_dir");

            Path path = new Path( dir + "/" + output_date_dir + "/" + Config.getProperty("label.num.outputName"));
            FileSystem fs = path.getFileSystem(conf);
            if (!fs.exists(path)) {
                System.out.println("开始生成标签数和票数信息...");
                FileOutputFormat.setOutputPath(job, new Path(baseDir));
            }else {
                System.out.println("标签数和票数输出路径已存在,无需再次输出!");
            }

            return job.waitForCompletion(true) ? 0 : 1;
        }
    }

}
