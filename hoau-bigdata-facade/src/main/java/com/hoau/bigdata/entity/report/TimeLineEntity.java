package com.hoau.bigdata.entity.report;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：Joel.Li
 * @date ：2020/11/26 9:22
 * @description： 时间线路实体
 * @version: $version
 */
@Data
public class TimeLineEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    /**
     * 线路名称
     */
    private String nameLine;
    /**
     * 总耗时(小时)
     */
    private Double timeWayAll;
    /**
     * 耗时（小时）
     */
    private Double timeWay;
//    /**
//     * 发车公司
//     */
//    private String departureCompanyNo;
//    /**
//     * 到车公司
//     */
//    private String arrivalCompanyNo;
    /**
     * 耗时（分钟）
     */
    private Double transitTime;
    /**
     * 时刻
     */
    private String departureTime;
    /**
     * 时刻
     */
    private String timeDepart;
    /**
     * xxx
     */
    private String timeInterrupt;
    /**
     * 隔日天数
     */
    private Integer todayOrTomorrow;

    @Override
    public String toString() {
        return "TimeLineEntity{" +
                "nameLine='" + nameLine + '\'' +
                ", timeWayAll=" + timeWayAll +
                ", timeWay=" + timeWay +
                ", transitTime=" + transitTime +
                ", departureTime='" + departureTime + '\'' +
                ", timeDepart='" + timeDepart + '\'' +
                ", timeInterrupt='" + timeInterrupt + '\'' +
                ", todayOrTomorrow=" + todayOrTomorrow +
                '}';
    }
}
