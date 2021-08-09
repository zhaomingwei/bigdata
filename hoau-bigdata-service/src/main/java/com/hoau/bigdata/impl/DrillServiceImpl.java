package com.hoau.bigdata.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hoau.bigdata.entity.DhtjQuery;
import com.hoau.bigdata.entity.FhtjQuery;
import com.hoau.bigdata.entity.drill.ArrivalInfo;
import com.hoau.bigdata.entity.drill.FhdmxEntity;
import com.hoau.bigdata.mapper.drill.DrillMapper;
import com.hoau.bigdata.service.DrillService;
import com.hoau.bigdata.utils.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author ：Joel.Li
 * @date ：2020/11/6 14:12
 * @description： description
 * @version: $version
 */
@Service
public class DrillServiceImpl implements DrillService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    DrillMapper drillMapper;

//    @Reference(timeout = 10000, retries = -1)
//    FmsService fmsService;


    @Override
    public List<FhdmxEntity> dhtjQuery(DhtjQuery dhtjQuery) {
//        logger.info("效率监控看板-到货==service==入参==>" + JsonUtils.toJson(dhtjQuery));
        Map paramMap = new HashMap();
        paramMap.put("xhgs", dhtjQuery.getXhgs());
        paramMap.put("toDay", DateUtils.formatDateToString(new Date(), "yyyyMMdd"));
        paramMap.put("yesterday", DateUtils.formatDateToString(DateUtils.add(new Date(), -1), "yyyyMMdd"));
        paramMap.put("beforeYesterday", DateUtils.formatDateToString(DateUtils.add(new Date(), -2), "yyyyMMdd"));
        List<FhdmxEntity> maps = drillMapper.dhtjQuery(paramMap);
        if (null != maps && !maps.isEmpty()) {
            List<FhdmxEntity> list = maps.stream().filter(u -> Float.parseFloat(u.getSml()) < 1 ||
                    (Float.parseFloat(u.getSml()) >= 1 &&
                            DateUtils.diff(DateUtils.formatStringToDate(u.getTjsj(), "yyyy-MM-dd"), new Date()) < 1)).collect(Collectors.toList());
            return list.stream().sorted(Comparator.comparing(FhdmxEntity::getSml)).collect(Collectors.toList());
        }
        return maps;
    }

    @Override
    public List<FhdmxEntity> fhtjQuery(FhtjQuery fhtjQuery) {
//        logger.info("效率监控看板-发货==service==入参==>" + JsonUtils.toJson(fhtjQuery));
        Map paramMap = new HashMap();
        paramMap.put("shgs", fhtjQuery.getShgs());
        paramMap.put("toDay", DateUtils.formatDateToString(new Date(), "yyyyMMdd"));
        paramMap.put("yesterday", DateUtils.formatDateToString(DateUtils.add(new Date(), -1), "yyyyMMdd"));
        paramMap.put("beforeYesterday", DateUtils.formatDateToString(DateUtils.add(new Date(), -2), "yyyyMMdd"));
        List<FhdmxEntity> maps = drillMapper.fhtjQuery(paramMap);
        if (null != maps && !maps.isEmpty()) {
            List<FhdmxEntity> list = maps.stream().filter(u -> Float.parseFloat(u.getSml()) < 1 ||
                    (Float.parseFloat(u.getSml()) >= 1 &&
                            DateUtils.diff(DateUtils.formatStringToDate(u.getTjsj(), "yyyy-MM-dd"), new Date()) < 1)).collect(Collectors.toList());
            return list.stream().sorted(Comparator.comparing(FhdmxEntity::getXcwcl)).collect(Collectors.toList());
        }
        return Collections.emptyList();
    }

    @Override
    public List<ArrivalInfo> clxxdhQuery(DhtjQuery dhtjQuery) {
//        logger.info("车辆信息--到货--service--入参--》》" + JsonUtils.toJson(dhtjQuery));
//        List<CalMileageReq> calMileageReqs = new ArrayList<CalMileageReq>();
        List<ArrivalInfo> ls = new ArrayList<ArrivalInfo>();
        try {

            List<String> fcbhs = new ArrayList<>();
            List<ArrivalInfo> arrivalInfos = drillMapper.clxxdhQuery(dhtjQuery);
            Iterator<ArrivalInfo> iterator = arrivalInfos.iterator();
            while (iterator.hasNext()) {
                ArrivalInfo arrivalInfo = iterator.next();
                String fcbh = arrivalInfo.getFcbh();
                String cph = arrivalInfo.getCph();
                String fcsj = arrivalInfo.getFcsj();
//                Date actual = DateTimeUtil.getDateFromStringFm(fcsj);
//                fcbhs.add(fcbh);
//
//                CalMileageReq calMileageReq = new CalMileageReq();
//                calMileageReq.setCarNumber(cph);
//                calMileageReq.setDepartureNo(fcbh);
//                calMileageReq.setActualDepartureTime(actual);
//                calMileageReq.setCurrentTime(new Date());
//                calMileageReqs.add(calMileageReq);
            }
            if (fcbhs.size() > 0) {
//                return checkScan(arrivalInfos, fcbhs, calMileageReqs);
            }
        } catch (Exception e) {
            e.printStackTrace();
            logger.info("车辆信息--到货--service--异常--》》" + e);
        }
        return ls;
    }

//    public List<ArrivalInfo> checkScan(List<ArrivalInfo> arrivalInfos, List<String> fcbhs, List<CalMileageReq> calMileageReqs) {
//        logger.info("车辆信息--到货--service--checkScan--》》");
//        Map<String, String> fcbhMap = new HashMap();
//        List<ArrivalInfo> ls = new ArrayList<ArrivalInfo>();
//
//        //查询是否有扫描件
//        logger.info("车辆信息--到货--service--checkScan--查询是否有扫描件--》》");
//        List<String> alreadyScans = drillMapper.dhtjQueryByFcbh(fcbhs);
//        logger.info("车辆信息--到货--service--checkScan--查询是否有扫描件--返回扫描件数--》》" + alreadyScans.size());
//        Iterator<String> iterator = alreadyScans.iterator();
//        while (iterator.hasNext()) {
//            String fcbhStr = iterator.next();
//            fcbhMap.put(fcbhStr, fcbhStr);
//        }
////查询行驶公里数
//        Map<String, BigDecimal> calMileageRespMap = getCarAlreadyGoMileage(calMileageReqs);
//        Iterator<ArrivalInfo> arrivalInfoIterator = arrivalInfos.iterator();
//        while (arrivalInfoIterator.hasNext()) {
//            String statusNow = null;
//            int statusNowCode = 0;
//            ArrivalInfo arrivalInfo = arrivalInfoIterator.next();
//            String fcbh = arrivalInfo.getFcbh();
//            String cph = arrivalInfo.getCph();
//            Double gls = arrivalInfo.getGls();
//            String str = fcbh + cph;
//            int zt = arrivalInfo.getZt();
//            boolean b = fcbhMap.containsKey(fcbh);
//            switch (zt) {
//                case 1:
//                    statusNow = "在途";
//                    statusNowCode = 0;
//                    break;
//                case 2:
//                    if (b) {
//                        statusNow = "已到库";
//                        statusNowCode = 1;
//                    } else {
//                        statusNow = "卸车中";
//                        statusNowCode = 2;
//                    }
//                    break;
//                case 3:
//                    if (b) {
//                        statusNow = "已到库";
//                        statusNowCode = 1;
//                    } else {
//                        statusNow = "卸车完成";
//                        statusNowCode = 3;
//                    }
//                    break;
//            }
//            arrivalInfo.setStatusNow(statusNow);
//            arrivalInfo.setStatusNowCode(statusNowCode);
//
//            if (null != gls && null != calMileageRespMap && calMileageRespMap.containsKey(str)) {
//                BigDecimal str1 = calMileageRespMap.get(str);
//                BigDecimal alreadyGoMileage = null != str1 ? str1 : new BigDecimal("0.00");
//                BigDecimal glsB = new BigDecimal(gls);
//                BigDecimal bigDecimal = alreadyGoMileage.divide(glsB, 4, BigDecimal.ROUND_HALF_UP);
//                if (bigDecimal.compareTo(new BigDecimal("1")) == 1) {
//                    bigDecimal = new BigDecimal("1");
//                }
//                arrivalInfo.setRoadMonitor(bigDecimal);
//            } else {
//                arrivalInfo.setRoadMonitor(new BigDecimal("0.00"));
//            }
//            ls.add(arrivalInfo);
//        }
//        ls.sort(new Comparator<ArrivalInfo>() {
//            @Override
//            public int compare(ArrivalInfo info1, ArrivalInfo info2) {
//                return info1.getStatusNowCode() - info2.getStatusNowCode();
//            }
//        });
//        return ls;
//    }

    /**
     * @author: joel
     * @description: 查询行驶里程
     * @params: @param null:
     * @return:
     * @time: 2021/1/6 14:46
     */
//    private Map<String, BigDecimal> getCarAlreadyGoMileage(List<CalMileageReq> calMileageReqs) {
//        Map<String, BigDecimal> calMileageRespMap = new HashMap();
//        //查询行驶公里数
//        logger.info("车辆信息--到货service--查询已行驶公里数--getCarAlreadyGoMileage--入参大小--》》" + calMileageReqs.size());
//        if (null == fmsService) {
//            logger.info("车辆信息--到货service--查询已行驶公里数--fmsService RPC异常");
//        }
//        BackObject backObject = null;
//
//        List<List<CalMileageReq>> lists = subList(calMileageReqs, 100);
//        for (List<CalMileageReq> ls : lists) {
//            try {
//                logger.info("车辆信息--到货service--查询已行驶公里数--大小--》" + ls.size());
//                backObject = fmsService.getCarAlreadyGoMileage(ls);
//            } catch (Exception e) {
//                logger.info("车辆信息--到货service--查询已行驶公里数--fmsService异常--》》" + e.getMessage());
//            }
//            if (null != backObject) {
//                if (StringUtils.equals("200", backObject.getBackStatusCode())) {
//                    logger.info("车辆信息--到货service--查询已行驶公里数--返参--》》StatusCode:" + backObject.getBackStatusCode() + ",backStatusStr:" + backObject.getBackStatusStr());
//                    List<CalMileageResp> calMileageResps = (List<CalMileageResp>) backObject.getBackObj();
//                    logger.info("车辆信息--到货service--查询已行驶公里数--返参大小--》》" + calMileageResps.size());
//                    Iterator<CalMileageResp> iterator1 = calMileageResps.iterator();
//                    while (iterator1.hasNext()) {
//                        CalMileageResp next = iterator1.next();
//                        String departureNo = next.getDepartureNo();//发车编号
//                        String carNumber = next.getCarNumber();//车牌号
//                        String alreadyGoMileageStr = next.getAlreadyGoMileage();
//                        BigDecimal alreadyGoMileage = new BigDecimal(alreadyGoMileageStr).setScale(4, BigDecimal.ROUND_HALF_UP);
//                        calMileageRespMap.put(departureNo + carNumber, alreadyGoMileage);
//                    }
//                }
//            } else {
//                logger.info("车辆信息--到货service--查询已行驶公里数--fmsService异常--》》StatusCode:" + backObject.getBackStatusCode() + ",backStatusStr:" + backObject.getBackStatusStr());
//            }
//        }
//        return calMileageRespMap;
//    }

    /**
     * 截取list集合，返回list集合
     *
     * @param tList  (需要截取的集合)
     * @param subNum (每次截取的数量)
     * @return
     */
    private static <T> List<List<T>> subList(List<T> tList, Integer subNum) {
        // 新的截取到的list集合
        List<List<T>> tNewList = new ArrayList<List<T>>();
        // 要截取的下标上限
        Integer priIndex = 0;
        // 要截取的下标下限
        Integer lastIndex = 0;
        // 每次插入list的数量
        // Integer subNum = 500;
        // 查询出来list的总数目
        Integer totalNum = tList.size();
        // 总共需要插入的次数
        Integer insertTimes = totalNum / subNum;
        List<T> subNewList = new ArrayList<T>();
        for (int i = 0; i <= insertTimes; i++) {
            // [0--20) [20 --40) [40---60) [60---80) [80---100)
            priIndex = subNum * i;
            lastIndex = priIndex + subNum;
            // 判断是否是最后一次
            if (i == insertTimes) {
                subNewList = tList.subList(priIndex, tList.size());
            } else {
                // 非最后一次
                subNewList = tList.subList(priIndex, lastIndex);

            }
            if (subNewList.size() > 0) {
                //logger.info("开始将截取的list放入新的list中");
                tNewList.add(subNewList);
            }
        }
        return tNewList;

    }
}
