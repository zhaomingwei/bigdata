package com.hoau.bigdata.utils;

import org.apache.commons.lang3.StringUtils;

import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020/10/21
 * @Time: 16:25
 */
public class DateUtils {

    /**
     * 字符转换为时间，默认使用yyyy-MM-dd HH:mm:ss格式
     *
     * @param date   日期字符串
     * @param format 转换格式
     * @return LocalDateTime
     */
    public static Date formatStringToDate(String date, String format) {
        if (StringUtils.isNotBlank(date)) {
            SimpleDateFormat formatter = new SimpleDateFormat(format);
            ParsePosition pos = new ParsePosition(0);
            return formatter.parse(date, pos);
        } else {
            return null;
        }
    }

    /**
     * 时间换为字符转，默认使用yyyy-MM-dd HH:mm:ss格式
     *
     * @param date   日期字符串
     * @param format 转换格式
     * @return LocalDateTime
     */
    public static String formatDateToString(Date date, String format) {
        if (null == date) {
            return null;
        }
        if (StringUtils.isBlank(format)) {
            return null;
        }
        SimpleDateFormat formatter = new SimpleDateFormat(format);
        return formatter.format(date);
    }

    /**
     * 日期加天数（正-往后加，负-往前移）
     *
     * @param date
     * @param day
     * @return
     */
    public static Date add(Date date, int day) {
        if (null == date) {
            return null;
        }
        Calendar calendar = new GregorianCalendar();
        calendar.setTime(date);
        calendar.add(calendar.DATE, day); //把日期往后增加一天,整数  往后推,负数往前移动
        return calendar.getTime(); //这个时间就是日期往后推一天的结果
    }

    /**
     * 日期相差天数，date2-date1
     * @param date1
     * @param date2
     * @return
     */
    public static int diff(Date date1, Date date2) {
        if (null == date1) {
            return -1;
        }
        if (null == date2) {
            return -1;
        }
        return (int) ((date2.getTime() - date1.getTime()) / (24 * 60 * 60 * 1000));
    }

    /**
     * 比较两个时间的先后顺序，第一个在前返回true，否则返回false
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean compareTo(Date date1, Date date2) {
        if (date1.before(date2)) {
            return true;
        }
        return false;
    }

}
