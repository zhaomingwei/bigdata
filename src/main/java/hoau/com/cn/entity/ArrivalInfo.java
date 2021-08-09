package hoau.com.cn.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;

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

    /**
     * 写入时间
     */
    private String timeInsert;

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
        this.roadMonitor = roadMonitor;
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
        this.timeDelay = timeDelay;
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

    public void setWeight(BigDecimal weight) {
        if (null != weight) {
            this.weight = weight.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }


    public void setVolume(BigDecimal volume) {
        if (null != volume) {
            this.volume = volume.setScale(2, BigDecimal.ROUND_HALF_UP);
        }
    }

    public String getTimeInsert() {
        return timeInsert;
    }

    public void setTimeInsert(String timeInsert) {
        this.timeInsert = timeInsert;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append(fcbh).append(",")
                .append(zt).append(",")
                .append(typeStatistics).append(",")
                .append(cph).append(",")
                .append(cxms).append(",")
                .append(driver).append(",")
                .append(addressGoodsLoad).append(",")
                .append(addressGoodsUnload).append(",")
                .append(pools).append(",")
                .append(numGoods).append(",")
                .append(weight).append(",")
                .append(volume).append(",")
                .append(timePlan).append(",")
                .append(fcsj).append(",")
                .append(gsbhGoodsLoad).append(",")
                .append(gsbhGoodsUnload).append(",")
                .append(roadMonitor).append(",")
                .append(gls).append(",")
                .append(timeTrue).append(",")
                .append(timeDelay).append(",")
                .append(remark).append(",")
                .append(crossingStop).append(",")
                .append(timeInsert).append(",");

        return sb.toString().replaceAll("null", "");
    }
}
