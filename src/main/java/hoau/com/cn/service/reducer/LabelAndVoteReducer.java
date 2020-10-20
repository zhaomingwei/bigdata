package hoau.com.cn.service.reducer;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;

/**
 * @Description: 标签和票数reducer处理
 * @Author: zhaowei
 * @Date: 2020/10/16
 * @Time: 14：20
 * <p>
 * 四个泛型类型分别代表：
 * KeyIn        Reducer的输入数据的Key，这里是每行记录的发车编号
 * ValueIn      Reducer的输入数据的Value，这里是每行记录的实体类
 * KeyOut       Reducer的输出数据的Key，这里是每行记录的发车编号
 * ValueOut     Reducer的输出数据的Value，这里是处理后的实体类
 */
public class LabelAndVoteReducer extends Reducer<Text, Text, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<Text> value, Context context)
            throws IOException, InterruptedException {
        for (Text item : value) {
            System.out.println("REDUCE->key:" + key + ",value:" + item.toString());
            context.write(key, item);
        }
    }

}
