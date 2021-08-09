package com.hoau.bigdata.entity.track;

import java.io.Serializable;

/**
 * @description: 安装平台
 * @author: zxy
 * @create: 2020/12/6 17:18
 */
public class EasyInstallEntity implements Serializable {

    private static final long serialVersionUID = 8803769112687107421L;
    /*'运单编号'*/
    private String waybillNo;
    /*'记录时间'*/
    private String recordTime;
    /*'安装状态'*/
    private String installationStatus;
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

    public String getInstallationStatus() {
        return installationStatus;
    }

    public void setInstallationStatus(String installationStatus) {
        this.installationStatus = installationStatus;
    }

    @Override
    public String toString() {
        return "EasyInstallEntity{" +
                "waybillNo='" + waybillNo + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", installationStatus='" + installationStatus + '\'' +
                ", stateType='" + stateType + '\'' +
                '}';
    }
}
