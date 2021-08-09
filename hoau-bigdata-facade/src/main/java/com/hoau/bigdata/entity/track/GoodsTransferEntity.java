package com.hoau.bigdata.entity.track;

import java.io.Serializable;

/**
 * @description: 转库记录
 * @author: zxy
 * @create: 2020/12/6 16:32
 */
public class GoodsTransferEntity implements Serializable {
    private static final long serialVersionUID = 8884447995010666848L;
    /*'运单编号'*/
    private String waybillNo;
    /*'记录时间'*/
    private String recordTime;
    /*'转库公司'*/
    private String transferCompany;

    /*'区分状态 1 外发单 2 回单登记 3 转库记录 4 易安装记录 5 轨迹记录'*/
    private String stateType;

    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
    }

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getRecordTime() {
        return recordTime;
    }

    public void setRecordTime(String recordTime) {
        this.recordTime = recordTime;
    }

    public String getTransferCompany() {
        return transferCompany;
    }

    public void setTransferCompany(String transferCompany) {
        this.transferCompany = transferCompany;
    }

    @Override
    public String toString() {
        return "GoodsTransferEntity{" +
                "waybillNo='" + waybillNo + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", transferCompany='" + transferCompany + '\'' +
                ", stateType='" + stateType + '\'' +
                '}';
    }
}
