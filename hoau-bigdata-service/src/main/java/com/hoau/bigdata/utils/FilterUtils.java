package com.hoau.bigdata.utils;

import com.hoau.bigdata.entity.report.FhMxAndDhMxOut;
import com.hoau.bigdata.entity.tm.Label;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020.12.25
 * @Time: 10:19
 */
public class FilterUtils {

    private static final Log log = LogFactory.getLog(FilterUtils.class);

    /**
     * 过滤重复的数据(运单号和装卸单号一样的算重复数据)
     *
     * @param labelList
     * @return
     */
    public static List<Label> filterLabelList(List<Label> labelList, String message) {
        try {
            List<Label> reqList = CopyUtils.deepCopy(labelList);
            log.info(message + "去重复前条数:<" + reqList.size() + ">");
            List<Label> list = reqList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                    () -> new TreeSet<>(Comparator.comparing(o -> o.getPLANID() + "#" + o.getCSGNID()))),
                    ArrayList::new));
            log.info(message + "去重复后条数:<" + list.size() + ">");
            return list;
        } catch (Exception e) {
            log.error(message + "filterList异常:" + e);
        }
        return Collections.emptyList();
    }

    /**
     * 到货：按发车编号与装卸货公司去重
     *
     * @param dhList
     * @return
     */
    public static List<FhMxAndDhMxOut> filterFhAndDhList(List<FhMxAndDhMxOut> dhList, String message) {
        try {
            List<FhMxAndDhMxOut> reqList = CopyUtils.deepCopy(dhList);
            log.info(message + "去重复前条数:<" + reqList.size() + ">");
            List<FhMxAndDhMxOut> list = reqList.stream().collect(Collectors.collectingAndThen(Collectors.toCollection(
                    () -> new TreeSet<>(Comparator.comparing(o -> o.getFCBH() + "#" + o.getTJGSCODE()))),
                    ArrayList::new));
            log.info(message + "去重复后条数:<" + list.size() + ">");
            return list;
        } catch (Exception e) {
            log.error(message + "filterList异常:" + e);
        }
        return Collections.emptyList();
    }

}
