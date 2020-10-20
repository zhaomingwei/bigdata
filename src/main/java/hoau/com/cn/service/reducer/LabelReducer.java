package hoau.com.cn.service.reducer;

import hoau.com.cn.entity.Label;
import org.apache.hadoop.io.NullWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020/10/19
 * @Time: 16:48
 */
public class LabelReducer extends Reducer<Text, Label, NullWritable, Text> {

    @Override
    protected void reduce(Text key, Iterable<Label> value, Context context)
            throws IOException, InterruptedException {
        for (Label item : value) {
//            System.out.println("REDUCE->key:"+key+",value:"+item.toString());
            context.write(NullWritable.get(), new Text(item.toString()));
        }
    }

}
