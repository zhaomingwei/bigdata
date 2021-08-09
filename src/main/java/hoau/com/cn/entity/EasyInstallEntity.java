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
 * @description: 安装平台
 * @author: zxy
 * @create: 2020/12/6 17:18
 */
public class EasyInstallEntity implements Writable, DBWritable {

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
}
