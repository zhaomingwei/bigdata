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
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020/10/16
 * @Time: 14:06
 */
public class LabelNumAndVoteNum implements Writable, DBWritable {
    /**
     * 发车编号
     */
    private String FCBH;
    /**
     * 车牌号
     */
    private String CPH;
    private String SHGS;
    private String XHGS;
    private String ZXDH;
    private String FCGS;
    private String DCGS;
    private String JHCKJS;
    private String SJCKJS;
    private String YDBH;
    private String ZL;
    private String JS;
    private String TJ;
    private String LBLID;
    private String COUNT;
    private String SCANDATE;
    private String UNLDPLANID;
    private String USRNAME;

    @Override
    public void write(DataOutput out) throws IOException {
        Text.writeString(out, this.FCBH);
        Text.writeString(out, this.CPH);
        Text.writeString(out, this.SHGS);
        Text.writeString(out, this.XHGS);
        Text.writeString(out, this.ZXDH);
        Text.writeString(out, this.FCGS);
        Text.writeString(out, this.DCGS);
        Text.writeString(out, this.JHCKJS);
        Text.writeString(out, this.SJCKJS);
        Text.writeString(out, this.YDBH);
        Text.writeString(out, this.ZL);
        Text.writeString(out, this.JS);
        Text.writeString(out, this.TJ);
        Text.writeString(out, this.LBLID);
        Text.writeString(out, this.COUNT);
        Text.writeString(out, this.SCANDATE);
        Text.writeString(out, this.UNLDPLANID);
        Text.writeString(out, this.USRNAME);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.FCBH = Text.readString(in);
        this.CPH = Text.readString(in);
        this.SHGS = Text.readString(in);
        this.XHGS = Text.readString(in);
        this.ZXDH = Text.readString(in);
        this.FCGS = Text.readString(in);
        this.DCGS = Text.readString(in);
        this.JHCKJS = Text.readString(in);
        this.SJCKJS = Text.readString(in);
        this.YDBH = Text.readString(in);
        this.ZL = Text.readString(in);
        this.JS = Text.readString(in);
        this.TJ = Text.readString(in);
        this.LBLID = Text.readString(in);
        this.COUNT = Text.readString(in);
        this.SCANDATE = Text.readString(in);
        this.UNLDPLANID = Text.readString(in);
        this.USRNAME = Text.readString(in);
    }

    @Override
    public void write(PreparedStatement statement) throws SQLException {
        statement.setString(1, FCBH);
        statement.setString(2, CPH);
        statement.setString(3, SHGS);
        statement.setString(4, XHGS);
        statement.setString(5, ZXDH);
        statement.setString(6, FCGS);
        statement.setString(7, DCGS);
        statement.setString(8, JHCKJS);
        statement.setString(9, SJCKJS);
        statement.setString(10, YDBH);
        statement.setString(11, ZL);
        statement.setString(12, JS);
        statement.setString(13, TJ);
        statement.setString(14, LBLID);
        statement.setString(15, COUNT);
        statement.setString(16, SCANDATE);
        statement.setString(17, UNLDPLANID);
        statement.setString(18, USRNAME);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.FCBH = resultSet.getString(1);
        this.CPH = resultSet.getString(2);
        this.SHGS = resultSet.getString(3);
        this.XHGS = resultSet.getString(4);
        this.ZXDH = resultSet.getString(5);
        this.FCGS = resultSet.getString(6);
        this.DCGS = resultSet.getString(7);
        this.JHCKJS = resultSet.getString(8);
        this.SJCKJS = resultSet.getString(9);
        this.YDBH = resultSet.getString(10);
        this.ZL = resultSet.getString(11);
        this.JS = resultSet.getString(12);
        this.TJ = resultSet.getString(13);
        this.LBLID = resultSet.getString(14);
        this.COUNT = resultSet.getString(15);
        this.SCANDATE = resultSet.getString(16);
        this.UNLDPLANID = resultSet.getString(17);
        this.USRNAME = resultSet.getString(18);
    }

    public String getFCBH() {
        return FCBH;
    }

    public void setFCBH(String FCBH) {
        this.FCBH = FCBH;
    }

    public String getCPH() {
        return CPH;
    }

    public void setCPH(String CPH) {
        this.CPH = CPH;
    }

    public String getSHGS() {
        return SHGS;
    }

    public void setSHGS(String SHGS) {
        this.SHGS = SHGS;
    }

    public String getXHGS() {
        return XHGS;
    }

    public void setXHGS(String XHGS) {
        this.XHGS = XHGS;
    }

    public String getZXDH() {
        return ZXDH;
    }

    public void setZXDH(String ZXDH) {
        this.ZXDH = ZXDH;
    }

    public String getFCGS() {
        return FCGS;
    }

    public void setFCGS(String FCGS) {
        this.FCGS = FCGS;
    }

    public String getDCGS() {
        return DCGS;
    }

    public void setDCGS(String DCGS) {
        this.DCGS = DCGS;
    }

    public String getJHCKJS() {
        return JHCKJS;
    }

    public void setJHCKJS(String JHCKJS) {
        this.JHCKJS = JHCKJS;
    }

    public String getSJCKJS() {
        return SJCKJS;
    }

    public void setSJCKJS(String SJCKJS) {
        this.SJCKJS = SJCKJS;
    }

    public String getYDBH() {
        return YDBH;
    }

    public void setYDBH(String YDBH) {
        this.YDBH = YDBH;
    }

    public String getZL() {
        return ZL;
    }

    public void setZL(String ZL) {
        this.ZL = ZL;
    }

    public String getJS() {
        return JS;
    }

    public void setJS(String JS) {
        this.JS = JS;
    }

    public String getTJ() {
        return TJ;
    }

    public void setTJ(String TJ) {
        this.TJ = TJ;
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

    public String getUSRNAME() {
        return USRNAME;
    }

    public void setUSRNAME(String USRNAME) {
        this.USRNAME = USRNAME;
    }

    @Override
    public String toString() {
        return "LabelNumAndVoteNum{" +
                "FCBH='" + FCBH + '\'' +
                ", CPH='" + CPH + '\'' +
                ", SHGS='" + SHGS + '\'' +
                ", XHGS='" + XHGS + '\'' +
                ", ZXDH='" + ZXDH + '\'' +
                ", FCGS='" + FCGS + '\'' +
                ", DCGS='" + DCGS + '\'' +
                ", JHCKJS='" + JHCKJS + '\'' +
                ", SJCKJS='" + SJCKJS + '\'' +
                ", YDBH='" + YDBH + '\'' +
                ", ZL='" + ZL + '\'' +
                ", JS='" + JS + '\'' +
                ", TJ='" + TJ + '\'' +
                ", LBLID='" + LBLID + '\'' +
                ", COUNT='" + COUNT + '\'' +
                ", SCANDATE='" + SCANDATE + '\'' +
                ", UNLDPLANID='" + UNLDPLANID + '\'' +
                ", USRNAME='" + USRNAME + '\'' +
                '}';
    }
}
