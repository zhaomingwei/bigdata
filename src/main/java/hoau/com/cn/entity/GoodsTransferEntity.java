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
 * @description: 转库记录
 * @author: zxy
 * @create: 2020/12/6 16:32
 */
public class GoodsTransferEntity  implements Writable, DBWritable {
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
