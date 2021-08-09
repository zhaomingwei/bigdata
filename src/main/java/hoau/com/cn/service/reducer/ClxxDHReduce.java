package hoau.com.cn.service.reducer;

import com.google.gson.Gson;
import hoau.com.cn.entity.ArrivalInfo;
import hoau.com.cn.entity.ArrivalInfoTmp;
import hoau.com.cn.entity.TimeLineEntity;
import hoau.com.cn.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

/**
 * @author ：Joel.Li
 * @date ：2020/12/1 13:30
 * @description： 车辆信息---到货--Reduce
 * @version: $version
 */
public class ClxxDHReduce extends Reducer<Text, Text, Text, Text> {

    //    ArrivalInfo arrivalInfo = new ArrivalInfo();
    Gson gson = new Gson();

    @Override
    protected void reduce(Text key, Iterable<Text> values, Context context) throws IOException, InterruptedException {
        List<ArrivalInfoTmp> arrivalInfoTmps = new ArrayList<>();
        List<TimeLineEntity> timeLineEntitys = new ArrayList<>();
        for (Text test : values) {
            String value = test.toString();
            if (value.startsWith("ArrivalInfoTmp")) {
                ArrivalInfoTmp arrivalInfoTmp = gson.fromJson(value.replaceAll("ArrivalInfoTmp", ""), ArrivalInfoTmp.class);
                arrivalInfoTmps.add(arrivalInfoTmp);
            } else if (value.startsWith("TimeLineEntity")) {
                TimeLineEntity timeLineEntity = gson.fromJson(value.replaceAll("TimeLineEntity", ""), TimeLineEntity.class);
                timeLineEntitys.add(timeLineEntity);
            }
        }
        if (!arrivalInfoTmps.isEmpty()) {
            if (!timeLineEntitys.isEmpty()) {
                String s = "";
                for (ArrivalInfoTmp tmp : arrivalInfoTmps) {
                    for (TimeLineEntity t : timeLineEntitys) {
                        String fcsj = tmp.getFcsj();
                        if (null != fcsj && !StringUtils.equals("null", fcsj)) {
                            tmp.setTimePlan(calcTimePlan(tmp, t));
                        }
                        ArrivalInfo arrivalInfo = ArrInfoTransform(tmp);
                        s = arrivalInfo.toString();
                        context.write(key, new Text(s));
                    }
                }
            } else {
                for (ArrivalInfoTmp tmp : arrivalInfoTmps) {
                    ArrivalInfo arrivalInfo = ArrInfoTransform(tmp);
                    String s = arrivalInfo.toString();
                    context.write(key, new Text(s));
                }
            }
        }
    }

    /**
     * @author: joel
     * @description: 计算计划到达时间
     * @params: @param null:
     * @return:
     * @time: 2020/12/1 14:11
     */
    private String calcTimePlan(ArrivalInfoTmp arrivalInfoTmp, TimeLineEntity timeLineEntity) {

        //表来源
        String table_from = arrivalInfoTmp.getTableFrom();
        //发车时间
        String fcsjStr = arrivalInfoTmp.getFcsj();
        // 获取Calendar对象并以传进来的时间为准
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(DateUtils.formatStringToDate(fcsjStr, "yyyy-MM-dd HH:mm:ss"));

        /**
         * 计划到达时间=发车时间+耗时时长
         */
        //上转移
        if (StringUtils.equals(table_from, "LD_SZYFH")) {
            //单位--分钟
            Double transit_time = timeLineEntity.getTransitTime();
            if (null == transit_time) {
                return null;
            }
            int tr_time = new Double(transit_time).intValue();
            calendar.add(Calendar.MINUTE, tr_time);
        }
        //长途
        else if (StringUtils.equals(table_from, "LD_FHCLQK")) {
            Double timeWayAll = timeLineEntity.getTimeWayAll();
            if (null == timeWayAll) {
                return null;
            }
            Double time_way_all = timeWayAll * 60;
            int tw_all = new Double(time_way_all).intValue();
            calendar.add(Calendar.MINUTE, tw_all);
        }
        //中转
        else if (StringUtils.equals(table_from, "LD_FHCLQK_ZZDH")) {
            Double timeWay = timeLineEntity.getTimeWay();
            if (null == timeWay) {
                return null;
            }
            Double time_way = timeWay * 60;
            int tw = new Double(time_way).intValue();
            calendar.add(Calendar.MINUTE, tw);
        }
        return DateUtils.formatDateToString(calendar.getTime(), "yyyy-MM-dd HH:mm:ss");
    }

    //实体数据转换
    private ArrivalInfo ArrInfoTransform(ArrivalInfoTmp arrivalInfoTmp) {
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sf.format(new Date());

        ArrivalInfo arrivalInfo = new ArrivalInfo();

        arrivalInfo.setAddressGoodsLoad(arrivalInfoTmp.getAddressGoodsLoad());
        arrivalInfo.setAddressGoodsUnload(arrivalInfoTmp.getAddressGoodsUnload());
        arrivalInfo.setCph(arrivalInfoTmp.getCph());
        //道口
//        arrivalInfo.setCrossingStop(arrivalInfoTmp.getc);
        arrivalInfo.setCxms(arrivalInfoTmp.getCxms());
        arrivalInfo.setDriver(arrivalInfoTmp.getDriver());
        arrivalInfo.setFcbh(arrivalInfoTmp.getFcbh());
        arrivalInfo.setNumGoods(arrivalInfoTmp.getNumGoods());
        arrivalInfo.setPools(arrivalInfoTmp.getPools());
//        arrivalInfo.setRemark(arrivalInfoTmp.getRemark());
        arrivalInfo.setGsbhGoodsLoad(arrivalInfoTmp.getGsbhGoodsLoad());
        arrivalInfo.setGsbhGoodsUnload(arrivalInfoTmp.getGsbhGoodsUnload());
        //路程监控
//        arrivalInfo.setRoadMonitor(arrivalInfoTmp.get);
        arrivalInfo.setGls(arrivalInfoTmp.getGls());
        arrivalInfo.setFcsj(arrivalInfoTmp.getFcsj());

        //延误时间
        String s = calcTimeDelay(arrivalInfoTmp);
        arrivalInfo.setTimeDelay(s == null ? "00:00" : s);
        arrivalInfo.setTimePlan(arrivalInfoTmp.getTimePlan());
        arrivalInfo.setTimeTrue(arrivalInfoTmp.getTimeTrue());
        arrivalInfo.setTypeStatistics(arrivalInfoTmp.getTypeStatistics());
        arrivalInfo.setVolume(arrivalInfoTmp.getVolume());
        arrivalInfo.setWeight(arrivalInfoTmp.getWeight());
        arrivalInfo.setZt(arrivalInfoTmp.getZt());
        arrivalInfo.setTimeInsert(dateStr);
        return arrivalInfo;
    }

    private String calcTimeDelay(ArrivalInfoTmp arrivalInfoTmp) {
        String timeTrue = arrivalInfoTmp.getTimeTrue();
        String timePlan = arrivalInfoTmp.getTimePlan();
        try {
            if (null != timeTrue && !StringUtils.equals(timeTrue, "") && null != timePlan && !StringUtils.equals(timePlan, "")) {
                long diffTime = DateUtils.getDiffTime(timePlan, timeTrue);
                if (diffTime <= 0) {
                    return "00:00";
                }
                return DateUtils.getGapTime(diffTime);
            }
        } catch (Exception e) {
            //转换异常，不处理
        }
        return null;
    }
}
