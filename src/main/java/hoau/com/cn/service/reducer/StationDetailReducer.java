package hoau.com.cn.service.reducer;

import hoau.com.cn.entity.StationDetail;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.math.BigDecimal;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020/10/9
 * @Time: 15:37
 *
 * 四个泛型类型分别代表：
 *      KeyIn        Reducer的输入数据的Key，这里是每行记录的发车编号
 *      ValueIn      Reducer的输入数据的Value，这里是每行记录的实体类
 *      KeyOut       Reducer的输出数据的Key，这里是每行记录的发车编号
 *      ValueOut     Reducer的输出数据的Value，这里是处理后的实体类
 */
public class StationDetailReducer extends Reducer<Text, StationDetail, Text, Text> {

    @Override
    protected void reduce(Text key, Iterable<StationDetail> value, Context context)
            throws IOException, InterruptedException {
//        System.out.println("REDUCE->key:"+key+",value:"+value);
        BigDecimal totalWeight = new BigDecimal("0");
        BigDecimal totalVolume = new BigDecimal("0");
        BigDecimal totalNumGoods = new BigDecimal("0");
        BigDecimal totalNumGoodsPlan = new BigDecimal("0");
        StationDetail stationDetail = new StationDetail();
        for (StationDetail item : value) {
            stationDetail.setGSJC(item.getGSJC());
            stationDetail.setFCBH(item.getFCBH());
            totalWeight = totalWeight.add(new BigDecimal(item.getWEIGHT()));
            totalVolume = totalVolume.add(new BigDecimal(item.getVOLUME()));
            totalNumGoods = totalNumGoods.add(new BigDecimal(item.getNUM_GOODS()));
            totalNumGoodsPlan = totalNumGoodsPlan.add(new BigDecimal(item.getNUM_GOODS_PLAN()));
            item.setWEIGHT(totalWeight.toString());
            item.setVOLUME(totalVolume.toString());
            item.setNUM_GOODS(totalNumGoods.toString());
            item.setNUM_GOODS_PLAN(totalNumGoodsPlan.toString());
        }
        stationDetail.setWEIGHT(totalWeight.toString());
        stationDetail.setVOLUME(totalVolume.toString());
        stationDetail.setNUM_GOODS(totalNumGoods.toString());
        stationDetail.setNUM_GOODS_PLAN(totalNumGoodsPlan.toString());
        context.write(key, new Text(stationDetail.toString()));
    }

}
