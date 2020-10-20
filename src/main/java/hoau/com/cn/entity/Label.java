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
 * @Description: 件信息
 * @Author: zhaowei
 * @Date: 2020/10/19
 * @Time: 15:46
 */
public class Label implements Writable, DBWritable {

    /**
     * 件号(跟票件关系表有关联)
     */
    private String LBLID;

    /**
     * 大票货件数(装卸车)
     */
    private String COUNT;

    /**
     * 扫描时间
     */
    private String SCANDATE;

    /**
     * 卸车单号(跟卸车计划表有关联；无计划卸车单没有关联)
     */
    private String UNLDPLANID;

    /**
     * 运单号
     */
    private String CSGNID;

    /**
     * 用户姓名
     */
    private String USRNAME;

    @Override
    public void write(DataOutput out) throws IOException {
        Text.writeString(out, this.LBLID);
        Text.writeString(out, this.COUNT);
        Text.writeString(out, this.SCANDATE);
        Text.writeString(out, this.UNLDPLANID);
        Text.writeString(out, this.CSGNID);
        Text.writeString(out, this.USRNAME);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.LBLID = Text.readString(in);
        this.COUNT = Text.readString(in);
        this.SCANDATE = Text.readString(in);
        this.UNLDPLANID = Text.readString(in);
        this.CSGNID = Text.readString(in);
        this.USRNAME = Text.readString(in);
    }

    @Override
    public void write(PreparedStatement statement) throws SQLException {
        statement.setString(1, LBLID);
        statement.setString(2, COUNT);
        statement.setString(3, SCANDATE);
        statement.setString(4, UNLDPLANID);
        statement.setString(5, CSGNID);
        statement.setString(6, USRNAME);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.LBLID = resultSet.getString(1);
        this.COUNT = resultSet.getString(2);
        this.SCANDATE = resultSet.getString(3);
        this.UNLDPLANID = resultSet.getString(4);
        this.CSGNID = resultSet.getString(5);
        this.USRNAME = resultSet.getString(6);
    }

    public String getLBLID() {
        return LBLID;
    }

    public void setLBLID(String LBLID) {
        this.LBLID = LBLID;
    }

    public String getCOUNT() {
        return COUNT;
    }

    public void setCOUNT(String COUNT) {
        this.COUNT = COUNT;
    }

    public String getSCANDATE() {
        return SCANDATE;
    }

    public void setSCANDATE(String SCANDATE) {
        this.SCANDATE = SCANDATE;
    }

    public String getUNLDPLANID() {
        return UNLDPLANID;
    }

    public void setUNLDPLANID(String UNLDPLANID) {
        this.UNLDPLANID = UNLDPLANID;
    }

    public String getCSGNID() {
        return CSGNID;
    }

    public void setCSGNID(String CSGNID) {
        this.CSGNID = CSGNID;
    }

    public String getUSRNAME() {
        return USRNAME;
    }

    public void setUSRNAME(String USRNAME) {
        this.USRNAME = USRNAME;
    }

    @Override
    public String toString() {
        return "Label{" +
                "LBLID='" + LBLID + '\'' +
                ", COUNT='" + COUNT + '\'' +
                ", SCANDATE='" + SCANDATE + '\'' +
                ", UNLDPLANID='" + UNLDPLANID + '\'' +
                ", CSGNID='" + CSGNID + '\'' +
                ", USRNAME='" + USRNAME + '\'' +
                '}';
    }
}
