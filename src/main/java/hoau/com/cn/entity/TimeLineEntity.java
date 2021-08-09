package hoau.com.cn.entity;

import java.io.Serializable;

/**
 * @author ：Joel.Li
 * @date ：2020/11/26 9:22
 * @description： 时间线路实体
 * @version: $version
 */
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

    public String getNameLine() {
        return nameLine;
    }

    public void setNameLine(String nameLine) {
        this.nameLine = nameLine;
    }

    public Double getTimeWayAll() {
        return timeWayAll;
    }

    public void setTimeWayAll(Double timeWayAll) {
        this.timeWayAll = timeWayAll;
    }

    public Double getTimeWay() {
        return timeWay;
    }

    public void setTimeWay(Double timeWay) {
        this.timeWay = timeWay;
    }

    public Double getTransitTime() {
        return transitTime;
    }

    public void setTransitTime(Double transitTime) {
        this.transitTime = transitTime;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public String getTimeDepart() {
        return timeDepart;
    }

    public void setTimeDepart(String timeDepart) {
        this.timeDepart = timeDepart;
    }

    public String getTimeInterrupt() {
        return timeInterrupt;
    }

    public void setTimeInterrupt(String timeInterrupt) {
        this.timeInterrupt = timeInterrupt;
    }

    public Integer getTodayOrTomorrow() {
        return todayOrTomorrow;
    }

    public void setTodayOrTomorrow(Integer todayOrTomorrow) {
        this.todayOrTomorrow = todayOrTomorrow;
    }

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
