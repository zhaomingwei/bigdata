package hoau.com.cn.service.job;

import hoau.com.cn.entity.StationDetail;
import hoau.com.cn.service.AllJobSubmitter;
import hoau.com.cn.service.format.FilterOutputFormat;
import hoau.com.cn.service.mapper.StationDetailMapper;
import hoau.com.cn.service.reducer.StationDetailReducer;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
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
 * @Author zw
 * Hello world!
 */
public class StationDetailJob {
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

        String date = DateUtils.formatDate(new Date(), "yyyyMMddHHmmss");
        String output_dir = date;
        conf.set("output_dir", output_dir);
        conf.set("output_name", output_name);
        System.out.println("模式：" + conf.get("mapreduce.framework.name"));

        //注意这行代码放在最前面，进行初始化，否则会报
        DBConfiguration.configureDB(conf, diverClass, url, userName, passWord);

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
                "   AND Q.FCSJ < to_date('2016-06-10', 'yyyy-MM-dd')" +
                "   AND GS.GSJC = 'N上海'";
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
                "   AND Q.FCSJ < to_date('2016-06-10', 'yyyy-MM-dd')" +
                "   AND GS.GSJC = 'N上海'";

        /**Job任务**/
        Job job = new Job(conf, "GET_STATIONS_DETAIL");
        /**设置输入格式为数据**/
        job.setInputFormatClass(DBInputFormat.class);
        /**设置map输出key、value的类型**/
        job.setMapOutputKeyClass(Text.class);
        job.setMapOutputValueClass(StationDetail.class);
        /**设置mapper和reduce的处理类**/
        job.setMapperClass(StationDetailMapper.class);
        job.setReducerClass(StationDetailReducer.class);
        /**设置reducer输出key的类型**/
        job.setOutputKeyClass(Text.class);
        /**设置输出文件格式，实现自定义文件名称**/
        job.setOutputFormatClass(FilterOutputFormat.class);

        DBInputFormat.setInput(job, StationDetail.class, sql, count);

        String baseDir = stationBaseDir + conf.get("output_dir");

        /**获取当天时间,场站信息每天就跑一次**/
        Path path = new Path( baseDir + "/" + output_dir + "/" + output_name);
        FileSystem fs = path.getFileSystem(conf);
        if (!fs.exists(path)) {
            System.out.println("当天第一次输出场站信息...");
            FileOutputFormat.setOutputPath(job, new Path(baseDir));
        }else {
            System.out.println("当天已输出场站信息,无需再次输出!");
        }

        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
