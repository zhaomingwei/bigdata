package hoau.com.cn.entity;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.io.Writable;
import org.apache.hadoop.mapreduce.lib.db.DBWritable;

import java.io.DataInput;
import java.io.DataOutput;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @Description: 件信息（标签信息）
 * @Author: zhaowei
 * @Date: 2020/10/19
 * @Time: 15:46
 */
public class Label implements Writable, DBWritable {

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
     * 运单号
     */
    private String CSGNID;

    @Override
    public void write(DataOutput out) throws IOException {
        Text.writeString(out, this.LBLID);
        Text.writeString(out, this.RCVMSGDATE);
        Text.writeString(out, this.SCANDATE);
        Text.writeString(out, this.PLANID);
        Text.writeString(out, this.CSGNID);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.LBLID = Text.readString(in);
        this.RCVMSGDATE = Text.readString(in);
        this.SCANDATE = Text.readString(in);
        this.PLANID = Text.readString(in);
        this.CSGNID = Text.readString(in);
    }

    @Override
    public void write(PreparedStatement statement) throws SQLException {
        statement.setString(1, LBLID);
        statement.setString(2, RCVMSGDATE);
        statement.setString(3, SCANDATE);
        statement.setString(4, PLANID);
        statement.setString(5, CSGNID);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.LBLID = resultSet.getString(1);
        this.RCVMSGDATE = resultSet.getString(2);
        this.SCANDATE = resultSet.getString(3);
        this.PLANID = resultSet.getString(4);
        this.CSGNID = resultSet.getString(5);
    }

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

    public String getCSGNID() {
        return CSGNID;
    }

    public void setCSGNID(String CSGNID) {
        this.CSGNID = CSGNID;
    }

    @Override
    public String toString() {
        return "Label{" +
                "LBLID='" + LBLID + '\'' +
                ", RCVMSGDATE='" + RCVMSGDATE + '\'' +
                ", SCANDATE='" + SCANDATE + '\'' +
                ", PLANID='" + PLANID + '\'' +
                ", CSGNID='" + CSGNID + '\'' +
                '}';
    }
}
