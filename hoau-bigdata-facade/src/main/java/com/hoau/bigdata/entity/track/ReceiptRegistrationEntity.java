package com.hoau.bigdata.entity.track;

import java.io.Serializable;

/**
 * @description: 回单登记
 * @author: zxy
 * @create: 2020/12/6 11:13
 */
public class ReceiptRegistrationEntity implements Serializable {
    private static final long serialVersionUID = -4404105664227189710L;
    /*'运单编号'*/
    private String waybillNo;
    /*'记录时间'*/
    private String recordTime;
    /*'生成公司'*/
    private String generationCompany;
    /*'区分状态 1 外发单 2 回单登记 3 转库记录 4 易安装记录 5 轨迹记录'*/
    private String stateType;
    /**运输类型*/
    private String transferType;

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

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

    public String getGenerationCompany() {
        return generationCompany;
    }

    public void setGenerationCompany(String generationCompany) {
        this.generationCompany = generationCompany;
    }

    @Override
    public String toString() {
        return "ReceiptRegistrationEntity{" +
                "waybillNo='" + waybillNo + '\'' +
                ", recordTime='" + recordTime + '\'' +
                ", generationCompany='" + generationCompany + '\'' +
                ", stateType='" + stateType + '\'' +
                ", transferType='" + transferType + '\'' +
                '}';
    }
}
