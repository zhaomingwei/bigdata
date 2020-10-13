package hoau.com.cn.demo;

import hoau.com.cn.entity.FcInfo;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapred.JobConf;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.db.DBConfiguration;
import org.apache.hadoop.mapreduce.lib.db.DBInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020/9/27
 * @Time: 11:10
 */
public class ReadMapDb {

    /**
     * Map作业读取数据记录数
     **/
    private static class DbMap extends Mapper<LongWritable, FcInfo, LongWritable, Text> {
        @Override
        protected void map(LongWritable key, FcInfo value, Context context)
                throws IOException, InterruptedException {
            context.write(new LongWritable(value.FHDBH), new Text(value.toString()));
        }
    }

    public static void main(String[] args) throws Exception {
        JobConf conf = new JobConf(ReadMapDb.class);
        InputStream in = ReadMapDb.class.getResourceAsStream("/db.properties");
        Properties properties = new Properties();
        properties.load(in);
        String diverClass = properties.getProperty("driver.class");
        String url = properties.getProperty("driver.url");
        String userName = properties.getProperty("driver.username");
        String passWord = properties.getProperty("driver.password");

        //注意这行代码放在最前面，进行初始化，否则会报
        DBConfiguration.configureDB(conf, diverClass, url, userName, passWord);

        /**要读取的字段信息**/
//        String fileds[] = new String[]{"id", "text_name"};
        /**Job任务**/
        Job job = new Job(conf, "readDB");
        System.out.println("模式：  " + conf.get("mapreduce.framework.name"));

        /**设置数据库输入格式的一些信息**/
        String sql = "SELECT FD.FCBH, FD.FHDBH, FD.SCGSBH FROM HYDATA.LD_FHD FD LEFT JOIN HYDATA.LD_FHDMX MX ON MX.FHDBH = FD.FHDBH WHERE FD.FCBH = 'N汕头N杭州N上海-16010119'";
        String count = "SELECT count(1) FROM HYDATA.LD_FHD FD LEFT JOIN HYDATA.LD_FHDMX MX ON MX.FHDBH = FD.FHDBH WHERE FD.FCBH = 'N汕头N杭州N上海-16010119'";

        DBInputFormat.setInput(job, FcInfo.class, sql, count);
        /***设置输入格式*/
        job.setInputFormatClass(DBInputFormat.class);
        job.setOutputKeyClass(LongWritable.class);
        job.setOutputValueClass(Text.class);
        job.setMapperClass(DbMap.class);
//        String path = "hdfs://10.39.251.159:9000/root/outputdb";
//        Path p = new Path(path);
//        FileSystem fs = p.getFileSystem(conf);
//        if (fs.exists(p)) {
//            fs.delete(p, true);
//            System.out.println("输出路径存在，已删除！");
//        }
        FileOutputFormat.setOutputPath(job, new Path("hdfs://10.39.251.159:9000/root/outputdb3"));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }

}
