package hoau.com.cn.service.reducer;

import com.google.gson.Gson;
import hoau.com.cn.common.Constant;
import hoau.com.cn.entity.FhMxAndDhMxOut;
import hoau.com.cn.entity.TimeLineEntity;
import hoau.com.cn.utils.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Description: 发货单明细看板数据reducer处理
 * @Author: zhaowei
 * @Date: 2020/10/19
 * @Time: 16:48
 */
public class FhMxReducer extends Reducer<Text, Text, Text, Text> {

    Gson gson = new Gson();
    FhMxAndDhMxOut fhMxAndDhMxOut = new FhMxAndDhMxOut();
    TimeLineEntity timeLineEntity = new TimeLineEntity();

    @Override
    protected void reduce(Text key, Iterable<Text> value, Context context) {
        try {
            List<FhMxAndDhMxOut> fhmxList = new ArrayList();
            //理论上一个nameLine只会有一个
            List<TimeLineEntity> timeLineList = new ArrayList();
            for (Text item : value) {
                String val = item.toString().trim();
                fhMxAndDhMxOut = new FhMxAndDhMxOut();
                if (StringUtils.isNotBlank(val) && val.startsWith(Constant.FHMXANDDHMXOUT)) {
                    fhMxAndDhMxOut = gson.fromJson(val.replaceAll(Constant.FHMXANDDHMXOUT, ""), FhMxAndDhMxOut.class);
                    fhmxList.add(fhMxAndDhMxOut);
                } else if (StringUtils.isNotBlank(val) && val.startsWith(Constant.TIMELINEENTITY)) {
                    timeLineEntity = gson.fromJson(val.replaceAll(Constant.TIMELINEENTITY, ""), TimeLineEntity.class);
                    timeLineList.add(timeLineEntity);
                }
            }
            if (!fhmxList.isEmpty() && !timeLineList.isEmpty()) {
                TimeLineEntity timeLineEntity = timeLineList.get(0);
                for (FhMxAndDhMxOut f : fhmxList) {
                    String bussesCode = f.getBUSSESCODE();
                    Date scsjTime = DateUtils.formatStringToDate(f.getSCSJ(), "yyyy-MM-dd HH:mm:ss");
                    Date scsjDay = DateUtils.formatStringToDate(StringUtils.substring(f.getSCSJ(), 0, 11), "yyyy-MM-dd");
                    //0-长途发货,毫秒
                    if (StringUtils.equals("0", bussesCode)) {
                        if (timeLineEntity.getTodayOrTomorrow() == 1) {
                            if (null != scsjTime && null != scsjDay && scsjTime.getTime() > (scsjDay.getTime() + 6.5 * 60 * 60 * 1000)) {
                                f.setJHFCSJ(DateUtils.formatDateToString(DateUtils.add(scsjDay, 1), "yyyy-MM-dd") + " " + timeLineEntity.getTimeDepart());
                            } else if (StringUtils.isNotBlank(timeLineEntity.getTimeDepart()) && !StringUtils.equals("null", timeLineEntity.getTimeDepart())) {
                                if(timeLineEntity.getTimeDepart().length() == 5){
                                    timeLineEntity.setTimeDepart(timeLineEntity.getTimeDepart() + ":00");
                                }
                                Date fcsj = null;
                                if (StringUtils.isNotBlank(f.getFCSJ()) && !StringUtils.equals("null", f.getFCSJ())) {
                                    fcsj = DateUtils.formatStringToDate(f.getFCSJ(), "yyyy-MM-dd HH:mm:ss");
                                } else {
                                    fcsj = DateUtils.formatStringToDate(DateUtils.formatDateToString(
                                            DateUtils.add(scsjDay, timeLineEntity.getTodayOrTomorrow()),
                                            "yyyy-MM-dd") + " " + timeLineEntity.getTimeDepart(), "yyyy-MM-dd HH:mm:ss");
                                }
                                if (null != fcsj && DateUtils.diff(scsjTime, fcsj) > 3) {
                                    f.setJHFCSJ(DateUtils.formatDateToString(DateUtils.add(scsjDay, timeLineEntity.getTodayOrTomorrow()), "yyyy-MM-dd") + " " + timeLineEntity.getTimeDepart());
                                }
                            } else if (StringUtils.isBlank(timeLineEntity.getTimeDepart()) || StringUtils.equals("null", timeLineEntity.getTimeDepart())) {
                                f.setJHFCSJ(DateUtils.formatDateToString(scsjDay, "yyyy-MM-dd"));
                            }
                        } else {
                            f.setJHFCSJ(DateUtils.formatDateToString(scsjDay, "yyyy-MM-dd") + " " + timeLineEntity.getTimeDepart());
                        }
                    } else if (StringUtils.equals("1", bussesCode)) {//1-中转发货
                        if(timeLineEntity.getTodayOrTomorrow() == 1){
                            if(timeLineEntity.getTimeDepart().length() == 5){
                                timeLineEntity.setTimeDepart(timeLineEntity.getTimeDepart() + ":00");
                            }
                            Date zfcsj = null;
                            if (StringUtils.isNotBlank(f.getFCSJ()) && !StringUtils.equals("null", f.getFCSJ())) {
                                zfcsj = DateUtils.formatStringToDate(f.getFCSJ(), "yyyy-MM-dd HH:mm:ss");
                            } else {
                                zfcsj = DateUtils.formatStringToDate(DateUtils.formatDateToString(scsjDay,"yyyy-MM-dd") + " " + timeLineEntity.getTimeDepart(), "yyyy-MM-dd HH:mm:ss");
                            }
                            if((new Date().getTime() > (scsjDay.getTime() + 6.5 * 60 * 60 * 1000)) || (DateUtils.diff(new Date(), zfcsj) > 3)){
                                int timeInterrupt = timeLineEntity.getTimeInterrupt() == null || StringUtils.equals("null", timeLineEntity.getTimeInterrupt()) ?
                                        0 : Integer.parseInt(timeLineEntity.getTimeInterrupt());
                                int timeWay = timeLineEntity.getTimeWay() == null ? 0 : timeLineEntity.getTimeWay().intValue();
                                Date fcsj = DateUtils.add(scsjDay, (timeInterrupt + timeWay) / 24 + timeLineEntity.getTodayOrTomorrow());
                                int diffDay = DateUtils.diff(scsjDay, DateUtils.formatStringToDate(DateUtils.formatDateToString(new Date(), "yyyy-MM-dd")
                                        + " " + timeLineEntity.getTimeDepart(), "yyyy-MM-dd HH:mm:ss"));
                                f.setJHFCSJ(DateUtils.formatDateToString(DateUtils.add(fcsj, diffDay), "yyyy-MM-dd HH:mm"));
                            } else {
                                int timeInterrupt = timeLineEntity.getTimeInterrupt() == null || StringUtils.equals("null", timeLineEntity.getTimeInterrupt()) ?
                                        0 : Integer.parseInt(timeLineEntity.getTimeInterrupt());
                                int timeWay = timeLineEntity.getTimeWay() == null ? 0 : timeLineEntity.getTimeWay().intValue();
                                Date fcsj = DateUtils.add(scsjDay, (timeInterrupt + timeWay) / 24);
                                int diffDay = DateUtils.diff(scsjDay, DateUtils.formatStringToDate(DateUtils.formatDateToString(new Date(), "yyyy-MM-dd")
                                        + " " + timeLineEntity.getTimeDepart(), "yyyy-MM-dd HH:mm:ss"));
                                f.setJHFCSJ(DateUtils.formatDateToString(DateUtils.add(fcsj, diffDay), "yyyy-MM-dd HH:mm"));
                            }
                        }
                    } else if (StringUtils.equals("2", bussesCode)) {//2-下转移发货
                        String departureTime = timeLineEntity.getDepartureTime();
                        if(StringUtils.isNotBlank(departureTime) && departureTime.length() == 5){
                            departureTime = departureTime + ":00";
                        }
                        Date date = DateUtils.formatStringToDate(DateUtils.formatDateToString(new Date(), "yyyy-MM-dd")
                                + " " + departureTime, "yyyy-MM-dd HH:mm:ss");
                        if (scsjTime.getTime() - date.getTime() > 0) {
                            f.setJHFCSJ(DateUtils.formatDateToString(DateUtils.add(scsjDay, 1), "yyyy-MM-dd") + " " + timeLineEntity.getDepartureTime());
                        } else {
                            f.setJHFCSJ(DateUtils.formatDateToString(scsjDay, "yyyy-MM-dd") + " " + timeLineEntity.getDepartureTime());
                        }
                    }
//                            System.out.println("reduce->:key:" + key + ", value:" + f1.toString());
                    context.write(key, new Text(f.getStringContent()));
                }
            }
        } catch (Exception e) {
            System.out.println("FhMxReducer->exception:" + e);
        }
    }

}
