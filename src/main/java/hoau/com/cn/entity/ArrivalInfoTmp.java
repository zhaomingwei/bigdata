package hoau.com.cn.entity;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author ：Joel.Li
 * @date ：2020/11/25 10:52
 * @description： 车辆信息看板--到货
 * @version: $version
 */
public class ArrivalInfoTmp implements Serializable {
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
     * 最后到达时间
     */
    private String timeTrue;

    /**
     * 计划到达时间
     */
    private String timePlan;

    /**
     * 生成时间
     */
    private String scsj;

    /**
     * 发车时间
     */
    private String fcsj;

    /**
     * 卸车到货
     */
    private String xcdh;

    /**
     * 发车公司编码
     */
    private String gsbhGoodsLoad;

    /**
     * 到货公司编码
     */
    private String gsbhGoodsUnload;

    /**
     * 公里数
     */
    private Double gls;

    /**
     * 线路名称
     */
    private String nameLine;

    /**
     * 说明
     */
    private String remark;

    /**
     * 表来源
     */
    private String tableFrom;

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

    public String getTimeTrue() {
        return timeTrue;
    }

    public void setTimeTrue(String timeTrue) {
        this.timeTrue = timeTrue;
    }

    public String getTimePlan() {
        return timePlan;
    }

    public void setTimePlan(String timePlan) {
        this.timePlan = timePlan;
    }

    public String getScsj() {
        return scsj;
    }

    public void setScsj(String scsj) {
        this.scsj = scsj;
    }

    public String getFcsj() {
        return fcsj;
    }

    public void setFcsj(String fcsj) {
        this.fcsj = fcsj;
    }

    public String getXcdh() {
        return xcdh;
    }

    public void setXcdh(String xcdh) {
        this.xcdh = xcdh;
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

    public String getNameLine() {
        return nameLine;
    }

    public void setNameLine(String nameLine) {
        this.nameLine = nameLine;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getTableFrom() {
        return tableFrom;
    }

    public void setTableFrom(String tableFrom) {
        this.tableFrom = tableFrom;
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


    @Override
    public String toString() {
        String str = "ArrivalInfoTmp{" +
                "fcbh='" + fcbh + '\'' +
                ", zt=" + zt +
                ", typeStatistics='" + typeStatistics + '\'' +
                ", cph='" + cph + '\'' +
                ", cxms='" + cxms + '\'' +
                ", driver='" + driver + '\'' +
                ", addressGoodsLoad='" + addressGoodsLoad + '\'' +
                ", addressGoodsUnload='" + addressGoodsUnload + '\'' +
                ", pools=" + pools +
                ", numGoods=" + numGoods +
                ", weight=" + weight +
                ", volume=" + volume +
                ", timeTrue=" + timeTrue +
                ", timePlan=" + timePlan +
                ", scsj=" + scsj +
                ", fcsj=" + fcsj +
                ", xcdh='" + xcdh + '\'' +
                ", gsbhGoodsLoad='" + gsbhGoodsLoad + '\'' +
                ", gsbhGoodsUnload='" + gsbhGoodsUnload + '\'' +
                ", gls=" + gls +
                ", nameLine='" + nameLine + '\'' +
                ", remark='" + remark + '\'' +
                ", tableFrom='" + tableFrom + '\'' +
                '}';
        return str.replaceAll(" ", "");
//        return str;
    }


//    @Override
//    public String toString() {
//
//        StringBuilder sb = new StringBuilder();
//
//        sb.append(FCBH).append(",").append(ZT).append(",")
//                .append(TYPE_STATISTICS).append(",").append(CPH).append(",")
//                .append(CXMS).append(",").append(DRIVER).append(",")
//                .append(ADDRESS_GOODS_LOAD).append(",").append(ADDRESS_GOODS_UNLOAD).append(",")
//                .append(POOLS).append(",").append(NUM_GOODS).append(",")
//                .append(WEIGHT).append(",").append(VOLUME).append(",")
//                .append(TIME_TRUE).append(",").append(TIME_PLAN).append(",")
//                .append(SCSJ).append(",").append(FCSJ).append(",")
//                .append(XCDH).append(",").append(GSBH_GOODS_LOAD).append(",")
//                .append(GSBH_GOODS_UNLOAD).append(",").append(GLS).append(",")
//                .append(NAME_LINE).append(",").append(REMARK).append(",")
//                .append(TABLE_FROM);
//
//        return sb.toString().replaceAll(" ", "").replaceAll("null", "");
//    }
}
