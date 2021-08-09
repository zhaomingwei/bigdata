package com.hoau.bigdata.entity.drill;

import java.io.Serializable;

/**
 * @description: 货物跟踪记录信息
 * @author: zxy
 * @create: 2020/12/9 13:36
 */
public class TrackRecordEntity implements Serializable {

    private static final long serialVersionUID = 3490149480443716976L;
    /*'运单编号'*/
    private String waybillNo;
    /*'区分状态 1 外发单 2 回单登记 3 转库记录 4 易安装记录 5 轨迹记录'*/
    private String stateType;
    /*'当前状态时间'*/
    private String currentStateTime;
    /*'物流方向发货地'*/
    private String deliveryCompany;
    /*'物流方向目的地'*/
    private String destinationCompany;
    /*'运输类型'*/
    private String transferType;
    /*'装车类型 2是配载，12为偏线'*/
    private String loadType;

    public String getWaybillNo() {
        return waybillNo;
    }

    public void setWaybillNo(String waybillNo) {
        this.waybillNo = waybillNo;
    }

    public String getStateType() {
        return stateType;
    }

    public void setStateType(String stateType) {
        this.stateType = stateType;
    }

    public String getCurrentStateTime() {
        return currentStateTime;
    }

    public void setCurrentStateTime(String currentStateTime) {
        this.currentStateTime = currentStateTime;
    }

    public String getDeliveryCompany() {
        return deliveryCompany;
    }

    public void setDeliveryCompany(String deliveryCompany) {
        this.deliveryCompany = deliveryCompany;
    }

    public String getDestinationCompany() {
        return destinationCompany;
    }

    public void setDestinationCompany(String destinationCompany) {
        this.destinationCompany = destinationCompany;
    }

    public String getTransferType() {
        return transferType;
    }

    public void setTransferType(String transferType) {
        this.transferType = transferType;
    }

    public String getLoadType() {
        return loadType;
    }

    public void setLoadType(String loadType) {
        this.loadType = loadType;
    }
}
