package hoau.com.cn.demo1;

import java.io.IOException;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.lib.input.FileSplit;

public class TableMapper extends Mapper<LongWritable, Text, Text, TableBean> {
    String name;
    TableBean bean = new TableBean();
    Text k = new Text();

    @Override
    protected void setup(Context context) throws IOException, InterruptedException {
        // 1 获取输入文件切片
        FileSplit split = (FileSplit) context.getInputSplit();
        // 2 获取输入文件名称
        name = split.getPath().getName();
    }

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
        // 1 获取输入数据
        String line = value.toString();
        // 2 不同文件分别处理
        if (name.startsWith("order")) {// 订单表处理
            //id	pid	amount
            //1001	01	1
            // 2.1 切割
            String[] fields = line.split("\t");
            // 2.2 封装bean对象
            bean.setOrder_id(fields[0]);
            bean.setP_id(fields[1]);
            bean.setAmount(Integer.parseInt(fields[2]));
            bean.setPname("");//不能为空，不然序列化的时候会报异常
            bean.setFlag("order");
            k.set(fields[1]);
        } else {// 产品表处理
            //pid	pname
            //01	小米
            // 2.3 切割
            String[] fields = line.split("\t");
            // 2.4 封装bean对象
            bean.setP_id(fields[0]);
            bean.setPname(fields[1]);
            bean.setFlag("pd");
            bean.setAmount(0);
            bean.setOrder_id("");
            k.set(fields[0]);
        }
        // 3 写出
        context.write(k, bean);
    }
}
