package com.hoau.bigdata.entity.drill;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ：Joel.Li
 * @date ：2020/11/25 10:52
 * @description： 车辆信息看板--到货
 * @version: $version
 */
public class ArrivalInfo implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 发车编号
     */
    private String fcbh;

    /**
     * 状态
     */
    private Integer zt;

    /**
     * 当前状态（根据zt+是否有扫描记录判断生成，逻辑在实现类中）
     */
    private String statusNow;

    /**
     * 在途：0；到货：1；卸车中：2；卸车完成：3
     */
    private int statusNowCode;

    /**
     * 类型
     */
    private String typeStatistics;

    /**
     * 车牌号
     */
    private String cph;

    /**
     * 车型描述
     */
    private String cxms;

    /**
     * 司机
     */
    private String driver;

    /**
     * 发车地
     */
    private String addressGoodsLoad;

    /**
     * 到车地
     */
    private String addressGoodsUnload;

    /**
     * 票数
     */
    private Integer pools;

    /**
     * 件数
     */
    private Integer numGoods;

    /**
     * 重量
     */
    private BigDecimal weight;

    /**
     * 体积
     */
    private BigDecimal volume;

    /**
     * 计划到达时间
     */
    private String timePlan;

    /**
     * 发车时间
     */
    private String fcsj;

    /**
     * 核算当天时间
     */
    private String gfcsj;

    /**
     * 发车公司编码
     */
    private String gsbhGoodsLoad;

    /**
     * 到货公司编码
     */
    private String gsbhGoodsUnload;

    /**
     * 路程监控
     */
    private BigDecimal roadMonitor;

    /**
     * 总公里数
     */
    private Double gls;

    /**
     * 最后到达时间
     */
    private String timeTrue;

    /**
     * 延误时间 （实际到达时间-计划到达时间））
     */
    private String timeDelay;

    /**
     * 说明
     */
    private String remark;

    /**
     * 停靠道口
     */
    private String crossingStop;

    public String getStatusNow() {
        return statusNow;
    }

    public void setStatusNow(String statusNow) {
        this.statusNow = statusNow;
    }

    public String getFcbh() {
        return fcbh;
    }

    public void setFcbh(String fcbh) {
        this.fcbh = fcbh;
    }

    public Integer getZt() {
        return zt;
    }

    public void setZt(Integer zt) {
        this.zt = zt;
    }

    public String getTypeStatistics() {
        return typeStatistics;
    }

    public void setTypeStatistics(String typeStatistics) {
        this.typeStatistics = typeStatistics;
    }

    public String getCph() {
        return cph;
    }

    public void setCph(String cph) {
        this.cph = cph;
    }

    public String getCxms() {
        return cxms;
    }

    public void setCxms(String cxms) {
        this.cxms = cxms;
    }

    public String getDriver() {
        return driver;
    }

    public void setDriver(String driver) {
        this.driver = driver;
    }

    public String getAddressGoodsLoad() {
        return addressGoodsLoad;
    }

    public void setAddressGoodsLoad(String addressGoodsLoad) {
        this.addressGoodsLoad = addressGoodsLoad;
    }

    public String getAddressGoodsUnload() {
        return addressGoodsUnload;
    }

    public void setAddressGoodsUnload(String addressGoodsUnload) {
        this.addressGoodsUnload = addressGoodsUnload;
    }

    public Integer getPools() {
        return pools;
    }

    public void setPools(Integer pools) {
        this.pools = pools;
    }

    public Integer getNumGoods() {
        return numGoods;
    }

    public void setNumGoods(Integer numGoods) {
        this.numGoods = numGoods;
    }

    public BigDecimal getWeight() {
        return weight;
    }

    public BigDecimal getVolume() {
        return volume;
    }

    public String getTimePlan() {
        return timePlan;
    }

    public void setTimePlan(String timePlan) {
        this.timePlan = timePlan;
    }

    public BigDecimal getRoadMonitor() {
        return roadMonitor;
    }

    public void setRoadMonitor(BigDecimal roadMonitor) {
        if (null != roadMonitor) {
            this.roadMonitor = roadMonitor.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    public String getTimeTrue() {
        return timeTrue;
    }

    public void setTimeTrue(String timeTrue) {
        this.timeTrue = timeTrue;
    }

    public String getTimeDelay() {
        return timeDelay;
    }

    public void setTimeDelay(String timeDelay) {
        this.timeDelay = timeDelay == null ? "00:00" : timeDelay;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getCrossingStop() {
        return crossingStop;
    }

    public void setCrossingStop(String crossingStop) {
        this.crossingStop = crossingStop;
    }

    public String getGsbhGoodsLoad() {
        return gsbhGoodsLoad;
    }

    public void setGsbhGoodsLoad(String gsbhGoodsLoad) {
        this.gsbhGoodsLoad = gsbhGoodsLoad;
    }

    public String getGsbhGoodsUnload() {
        return gsbhGoodsUnload;
    }

    public void setGsbhGoodsUnload(String gsbhGoodsUnload) {
        this.gsbhGoodsUnload = gsbhGoodsUnload;
    }

    public Double getGls() {
        return gls;
    }

    public void setGls(Double gls) {
        this.gls = gls;
    }

    public String getFcsj() {
        return fcsj;
    }

    public void setFcsj(String fcsj) {
        this.fcsj = fcsj;
    }

    public String getGfcsj() {
        return gfcsj;
    }

    public void setGfcsj(String gfcsj) {
        this.gfcsj = gfcsj;
    }

    public void setWeight(BigDecimal weight) {
        if (null != weight) {
            this.weight = weight.setScale(4, BigDecimal.ROUND_HALF_UP);
        }
    }


    public void setVolume(BigDecimal volume) {
        if (null != volume) {
            this.volume = volume.setScale(4, BigDecimal.ROUND_HALF_UP);
        }
    }

    public int getStatusNowCode() {
        return statusNowCode;
    }

    public void setStatusNowCode(int statusNowCode) {
        this.statusNowCode = statusNowCode;
    }

}
