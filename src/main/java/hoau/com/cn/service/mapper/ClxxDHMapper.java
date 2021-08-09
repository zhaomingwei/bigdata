package hoau.com.cn.service.mapper;

import com.google.gson.Gson;
import hoau.com.cn.entity.ArrivalInfoTmp;
import hoau.com.cn.entity.TimeLineEntity;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;

/**
 * @author ：Joel.Li
 * @date ：2020/12/1 8:37
 * @description： 车辆信息---到货Mapper
 * @version: $version
 */
public class ClxxDHMapper extends Mapper<LongWritable, Text, Text, Text> {

    Text k = new Text();
    ArrivalInfoTmp arrivalInfoTmp = new ArrivalInfoTmp();
    TimeLineEntity timeLineEntity = new TimeLineEntity();
    Gson gson = new Gson();

    @Override
    protected void map(LongWritable key, Text value, Context context) throws IOException, InterruptedException {
//        super.map(key, value, context);
        String var = value.toString();
        String nameLine = null;
        if (var.startsWith("ArrivalInfoTmp")) {
            arrivalInfoTmp = gson.fromJson(var.replaceAll("ArrivalInfoTmp", ""), ArrivalInfoTmp.class);
            String tableFrom = arrivalInfoTmp.getTableFrom();
            //上转移
            if (StringUtils.equals(tableFrom, "LD_SZYFH")) {
                nameLine = arrivalInfoTmp.getGsbhGoodsLoad() + arrivalInfoTmp.getGsbhGoodsUnload();
            }
            //长途/中转
            else if (StringUtils.equals(tableFrom, "LD_FHCLQK") || StringUtils.equals(tableFrom, "LD_FHCLQK_ZZDH")) {
                nameLine = arrivalInfoTmp.getNameLine();
            }
        } else if (var.startsWith("TimeLineEntity")) {
            timeLineEntity = gson.fromJson(var.replaceAll("TimeLineEntity", ""), TimeLineEntity.class);
            nameLine = timeLineEntity.getNameLine();

        }
        k.set(nameLine);
        context.write(k, value);
    }
}
