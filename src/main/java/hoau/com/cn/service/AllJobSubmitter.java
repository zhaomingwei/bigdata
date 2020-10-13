package hoau.com.cn.service;

import hoau.com.cn.entity.Station;
import hoau.com.cn.service.format.FilterOutputFormat;
import hoau.com.cn.service.mapper.StationMapper;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.jobcontrol.ControlledJob;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.http.client.utils.DateUtils;

import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020/9/28
 * @Time: 14:37
 */
public class AllJobSubmitter {

    public static void main(String[] args) throws Exception {
        JobConf conf = new JobConf(AllJobSubmitter.class);

        InputStream in = AllJobSubmitter.class.getResourceAsStream("/conf.properties");
        Properties properties = new Properties();
        properties.load(in);
        String diverClass = properties.getProperty("driver.class");
        String url = properties.getProperty("driver.url");
        String userName = properties.getProperty("driver.username");
        String passWord = properties.getProperty("driver.password");
        String stationBaseDir = properties.getProperty("station.baseDir");
        String output_name = properties.getProperty("station.outputName");

        String date = DateUtils.formatDate(new Date(), "yyyyMMdd");
        String output_dir = date;
        conf.set("output_dir", output_dir);
        conf.set("output_name", output_name);
        System.out.println("模式：" + conf.get("mapreduce.framework.name"));

        //注意这行代码放在最前面，进行初始化，否则会报
        DBConfiguration.configureDB(conf, diverClass, url, userName, passWord);

        /**设置数据库SQL语句,SQL结尾不能问添加; ，否则不能成功执行**/
        String sql = "  SELECT ROWNUM                  id," +
                "         ST.STATIONS_SHORTNAME      czjc," +
                "         ST.THE_AREA                ssdq," +
                "         ST.THE_BUSINESS_DEPARTMENT sssyb" +
                "    FROM REPORT_STATIONS ST" +
                "   WHERE ST.ACTIVE = 'Y'";
        String count = "SELECT count(1) FROM REPORT_STATIONS ST WHERE ST.ACTIVE = 'Y'";

        /**Job任务**/
        /**获取场站信息**/
        Job job1 = new Job(conf, "GET_STATIONS");
        /**设置输入格式**/
        job1.setInputFormatClass(DBInputFormat.class);
        job1.setOutputKeyClass(LongWritable.class);
        job1.setOutputValueClass(Text.class);
        job1.setMapperClass(StationMapper.class);
        job1.setOutputFormatClass(FilterOutputFormat.class);

        DBInputFormat.setInput(job1, Station.class, sql, count);

        String baseDir = stationBaseDir + conf.get("output_dir");

        /**获取当天时间,场站信息每天就跑一次**/
        Path path = new Path( baseDir + "/" + output_dir + "/" + output_name);
        FileSystem fs = path.getFileSystem(conf);
        if (!fs.exists(path)) {
            System.out.println("当天第一次输出场站信息...");
            FileOutputFormat.setOutputPath(job1, new Path(baseDir));
        }else {
            System.out.println("当天已输出场站信息,无需再次输出!");
        }

        //job1加入控制器
        ControlledJob ctrlJob1 = new ControlledJob(conf);
        ctrlJob1.setJob(job1);

        /**Job任务**/
        /****/
        Job job2 = new Job(conf, "GET_STATIONS_MX");
        /**设置输入格式**/
        job2.setInputFormatClass(DBInputFormat.class);
        job2.setOutputKeyClass(LongWritable.class);
        job2.setOutputValueClass(Text.class);
        job2.setMapperClass(StationMapper.class);
        job2.setOutputFormatClass(FilterOutputFormat.class);

        System.exit(job2.waitForCompletion(true) ? 0 : 1);
    }

}
