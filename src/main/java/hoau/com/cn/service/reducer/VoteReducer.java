package hoau.com.cn.service.reducer;

import hoau.com.cn.entity.Vote;
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
public class VoteReducer extends Reducer<Text, Vote, NullWritable, Text> {

    @Override
    protected void reduce(Text key, Iterable<Vote> value, Context context)
            throws IOException, InterruptedException {
        for (Vote item : value) {
//            System.out.println("REDUCE->key:"+key+",value:"+item.toString());
            context.write(NullWritable.get(), new Text(item.toString()));
        }
    }

}
