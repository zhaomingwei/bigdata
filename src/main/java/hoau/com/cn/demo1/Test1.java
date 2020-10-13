package hoau.com.cn.demo1;

import hoau.com.cn.entity.Station;
import hoau.com.cn.service.AllJobSubmitter;
import hoau.com.cn.service.format.FilterOutputFormat;
import hoau.com.cn.service.mapper.StationMapper;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.http.client.utils.DateUtils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Properties;

/**
 * Hello world!
 */
public class Test1 {
    public static void main(String[] args) throws IOException, ClassNotFoundException, InterruptedException {
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
        Job job = new Job(conf, "GET_STATIONS");
        /**设置输入格式**/
        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(Text.class);
        job.setMapperClass(StationMapper.class);
        job.setOutputFormatClass(FilterOutputFormat.class);

        DBInputFormat.setInput(job, Station.class, sql, count);

        String baseDir = stationBaseDir + conf.get("output_dir");
        FileOutputFormat.setOutputPath(job, new Path(baseDir));

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
