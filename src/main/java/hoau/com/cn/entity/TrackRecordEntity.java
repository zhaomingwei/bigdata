package hoau.com.cn.entity;

import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @description: 货物跟踪记录信息
 * @author: zxy
 * @create: 2020/12/9 13:36
 */
public class TrackRecordEntity implements Writable, DBWritable {

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

    @Override
    public void write(DataOutput out) throws IOException {

    }

    @Override
    public void readFields(DataInput in) throws IOException {

    }

    @Override
    public void write(PreparedStatement statement) throws SQLException {

    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {

    }

    @Override
    public String toString() {
        return waybillNo +
                "," + stateType +
                "," + currentStateTime +
                "," + deliveryCompany +
                "," + destinationCompany +
                "," + transferType +
                "," + loadType ;
    }
}
