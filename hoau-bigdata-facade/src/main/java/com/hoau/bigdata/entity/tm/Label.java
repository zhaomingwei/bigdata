package com.hoau.bigdata.entity.tm;

import java.io.Serializable;

/**
 * @Description: 件信息（标签信息）
 * @Author: zhaowei
 * @Date: 2020/10/19
 * @Time: 15:46
 */
public class Label implements Serializable {

    /**
     * 件号(标签号，跟票件关系表有关联)
     */
    private String LBLID;

    /**
     * 接收时间
     */
    private String RCVMSGDATE;

    /**
     * 扫描时间
     */
    private String SCANDATE;

    /**
     * 装卸单号(跟卸车计划表有关联；无计划卸车单没有关联)
     */
    private String PLANID;

    /**
     * 装车单号
     */
    private String LOADPLANID;

    /**
     * 卸车单号
     */
    private String UNLDPLANID;

    /**
     * 送货计划单号/下转移卸车计划单号
     */
    private String DELIVERPLANID;

    /**
     * 运单号
     */
    private String CSGNID;

    public String getLBLID() {
        return LBLID;
    }

    public void setLBLID(String LBLID) {
        this.LBLID = LBLID;
    }

    public String getRCVMSGDATE() {
        return RCVMSGDATE;
    }

    public void setRCVMSGDATE(String RCVMSGDATE) {
        this.RCVMSGDATE = RCVMSGDATE;
    }

    public String getSCANDATE() {
        return SCANDATE;
    }

    public void setSCANDATE(String SCANDATE) {
        this.SCANDATE = SCANDATE;
    }

    public String getPLANID() {
        return PLANID;
    }

    public void setPLANID(String PLANID) {
        this.PLANID = PLANID;
    }

    public String getLOADPLANID() {
        return LOADPLANID;
    }

    public void setLOADPLANID(String LOADPLANID) {
        this.LOADPLANID = LOADPLANID;
    }

    public String getUNLDPLANID() {
        return UNLDPLANID;
    }

    public void setUNLDPLANID(String UNLDPLANID) {
        this.UNLDPLANID = UNLDPLANID;
    }

    public String getDELIVERPLANID() {
        return DELIVERPLANID;
    }

    public void setDELIVERPLANID(String DELIVERPLANID) {
        this.DELIVERPLANID = DELIVERPLANID;
    }

    public String getCSGNID() {
        return CSGNID;
    }

    public void setCSGNID(String CSGNID) {
        this.CSGNID = CSGNID;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Label{LBLID='").append(LBLID == null ? "" : LBLID).append("'")
                .append(", RCVMSGDATE='").append(RCVMSGDATE == null ? "" : RCVMSGDATE).append("'")
                .append(", SCANDATE='").append(SCANDATE == null ? "" : SCANDATE).append("'")
                .append(", PLANID='").append(PLANID == null ? "" : PLANID).append("'")
                .append(", LOADPLANID='").append(LOADPLANID == null ? "" : LOADPLANID).append("'")
                .append(", UNLDPLANID='").append(UNLDPLANID == null ? "" : UNLDPLANID).append("'")
                .append(", DELIVERPLANID='").append(DELIVERPLANID == null ? "" : DELIVERPLANID).append("'")
                .append(", CSGNID='").append(CSGNID == null ? "" : CSGNID).append("'")
                .append("}");
        return sb.toString();
    }
}
