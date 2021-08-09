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
 * @Description: 效率监控看板-到货数据实体类
 * @Author: zhaowei
 * @Date: 2020/10/21
 * @Time: 15:12
 */
public class FhMxAndDhMxOut implements Writable, DBWritable {

    /**
     * 统计公司
     */
    private String TJGS;
    /**
     * 当前时间: 2020-01-04 10:24:09
     */
    private String CURRENTTIME;
    /**
     * 发车编号
     */
    private String FCBH;
    /**
     * 车牌号
     */
    private String CPH;
    /**
     * 上货地公司
     */
    private String SHGS;
    /**
     * 装卸单号
     */
    private String ZXDH;
    /**
     * 状态
     */
    private String ZT;
    /**
     * 发车公司
     */
    private String FCGS;
    /**
     * 到车公司
     */
    private String DCGS;
    /**
     * 计划出库件数
     */
    private String JHCKJS;
    /**
     * 实际出库件数
     */
    private String SJCKJS;
    /**
     * 运单编号
     */
    private String YDBH;
    /**
     * 重量
     */
    private String ZL;
    /**
     * 件数
     */
    private String JS;
    /**
     * 体积
     */
    private String TJ;
    /**
     * 该发车编号下所有运单总重量
     */
    private String TZL;
    /**
     * 该发车编号下所有运单总件数
     */
    private String TJS;
    /**
     * 该发车编号下所有运单总体积
     */
    private String TTJ;
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
     * 计划发车时间
     */
    private String JHFCSJ;
    /**
     * 下货地公司
     */
    private String XHGS;
    /**
     * 统计时间
     */
    private String TJSJ;
    /**
     * 线路名称
     */
    private String NAMELINE;
    /**
     * 0-长途发货,1-中转发货,2-下转移发货,3-派送
     */
    private String BUSSESCODE;
    /**
     * 生成时间
     */
    private String SCSJ;
    /**
     * 发车时间
     */
    private String FCSJ;

    @Override
    public void write(DataOutput out) throws IOException {
        Text.writeString(out, this.TJGS);
        Text.writeString(out, this.CURRENTTIME);
        Text.writeString(out, this.FCBH);
        Text.writeString(out, this.CPH);
        Text.writeString(out, this.SHGS);
        Text.writeString(out, this.ZXDH);
        Text.writeString(out, this.ZT);
        Text.writeString(out, this.FCGS);
        Text.writeString(out, this.DCGS);
        Text.writeString(out, this.JHCKJS);
        Text.writeString(out, this.SJCKJS);
        Text.writeString(out, this.YDBH);
        Text.writeString(out, this.ZL);
        Text.writeString(out, this.JS);
        Text.writeString(out, this.TJ);
        Text.writeString(out, this.TZL);
        Text.writeString(out, this.TJS);
        Text.writeString(out, this.TTJ);
        Text.writeString(out, this.LBLID);
        Text.writeString(out, this.RCVMSGDATE);
        Text.writeString(out, this.SCANDATE);
        Text.writeString(out, this.JHFCSJ);
        Text.writeString(out, this.XHGS);
        Text.writeString(out, this.TJSJ);
        Text.writeString(out, this.NAMELINE);
        Text.writeString(out, this.BUSSESCODE);
        Text.writeString(out, this.SCSJ);
        Text.writeString(out, this.FCSJ);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.TJGS = Text.readString(in);
        this.CURRENTTIME = Text.readString(in);
        this.FCBH = Text.readString(in);
        this.CPH = Text.readString(in);
        this.SHGS = Text.readString(in);
        this.ZXDH = Text.readString(in);
        this.ZT = Text.readString(in);
        this.FCGS = Text.readString(in);
        this.DCGS = Text.readString(in);
        this.JHCKJS = Text.readString(in);
        this.SJCKJS = Text.readString(in);
        this.YDBH = Text.readString(in);
        this.ZL = Text.readString(in);
        this.JS = Text.readString(in);
        this.TJ = Text.readString(in);
        this.TZL = Text.readString(in);
        this.TJS = Text.readString(in);
        this.TTJ = Text.readString(in);
        this.LBLID = Text.readString(in);
        this.RCVMSGDATE = Text.readString(in);
        this.SCANDATE = Text.readString(in);
        this.JHFCSJ = Text.readString(in);
        this.XHGS = Text.readString(in);
        this.TJSJ = Text.readString(in);
        this.NAMELINE = Text.readString(in);
        this.BUSSESCODE = Text.readString(in);
        this.SCSJ = Text.readString(in);
        this.FCSJ = Text.readString(in);
    }

    @Override
    public void write(PreparedStatement statement) throws SQLException {
        statement.setString(1, TJGS);
        statement.setString(2, CURRENTTIME);
        statement.setString(3, FCBH);
        statement.setString(4, CPH);
        statement.setString(5, SHGS);
        statement.setString(6, ZXDH);
        statement.setString(7, ZT);
        statement.setString(8, FCGS);
        statement.setString(9, DCGS);
        statement.setString(10, JHCKJS);
        statement.setString(11, SJCKJS);
        statement.setString(12, YDBH);
        statement.setString(13, ZL);
        statement.setString(14, JS);
        statement.setString(15, TJ);
        statement.setString(16, TZL);
        statement.setString(17, TJS);
        statement.setString(18, TTJ);
        statement.setString(19, LBLID);
        statement.setString(20, RCVMSGDATE);
        statement.setString(21, SCANDATE);
        statement.setString(22, JHFCSJ);
        statement.setString(23, XHGS);
        statement.setString(24, TJSJ);
        statement.setString(25, NAMELINE);
        statement.setString(26, BUSSESCODE);
        statement.setString(27, SCSJ);
        statement.setString(28, FCSJ);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.TJGS = resultSet.getString(1);
        this.CURRENTTIME = resultSet.getString(2);
        this.FCBH = resultSet.getString(3);
        this.CPH = resultSet.getString(4);
        this.SHGS = resultSet.getString(5);
        this.ZXDH = resultSet.getString(6);
        this.ZT = resultSet.getString(7);
        this.FCGS = resultSet.getString(8);
        this.DCGS = resultSet.getString(9);
        this.JHCKJS = resultSet.getString(10);
        this.SJCKJS = resultSet.getString(11);
        this.YDBH = resultSet.getString(12);
        this.ZL = resultSet.getString(13);
        this.JS = resultSet.getString(14);
        this.TJ = resultSet.getString(15);
        this.TZL = resultSet.getString(16);
        this.TJS = resultSet.getString(17);
        this.TTJ = resultSet.getString(18);
        this.LBLID = resultSet.getString(19);
        this.RCVMSGDATE = resultSet.getString(20);
        this.SCANDATE = resultSet.getString(21);
        this.JHFCSJ = resultSet.getString(22);
        this.XHGS = resultSet.getString(23);
        this.TJSJ = resultSet.getString(24);
        this.NAMELINE = resultSet.getString(25);
        this.BUSSESCODE = resultSet.getString(26);
        this.SCSJ = resultSet.getString(27);
        this.FCSJ = resultSet.getString(28);
    }

    public String getTJGS() {
        return TJGS;
    }

    public void setTJGS(String TJGS) {
        this.TJGS = TJGS;
    }

    public String getCURRENTTIME() {
        return CURRENTTIME;
    }

    public void setCURRENTTIME(String CURRENTTIME) {
        this.CURRENTTIME = CURRENTTIME;
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

    public String getZXDH() {
        return ZXDH;
    }

    public void setZXDH(String ZXDH) {
        this.ZXDH = ZXDH;
    }

    public String getZT() {
        return ZT;
    }

    public void setZT(String ZT) {
        this.ZT = ZT;
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

    public String getTZL() {
        return TZL;
    }

    public void setTZL(String TZL) {
        this.TZL = TZL;
    }

    public String getTJS() {
        return TJS;
    }

    public void setTJS(String TJS) {
        this.TJS = TJS;
    }

    public String getTTJ() {
        return TTJ;
    }

    public void setTTJ(String TTJ) {
        this.TTJ = TTJ;
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

    public String getJHFCSJ() {
        return JHFCSJ;
    }

    public void setJHFCSJ(String JHFCSJ) {
        this.JHFCSJ = JHFCSJ;
    }

    public String getXHGS() {
        return XHGS;
    }

    public void setXHGS(String XHGS) {
        this.XHGS = XHGS;
    }

    public String getTJSJ() {
        return TJSJ;
    }

    public void setTJSJ(String TJSJ) {
        this.TJSJ = TJSJ;
    }

    public String getNAMELINE() {
        return NAMELINE;
    }

    public void setNAMELINE(String NAMELINE) {
        this.NAMELINE = NAMELINE;
    }

    public String getBUSSESCODE() {
        return BUSSESCODE;
    }

    public void setBUSSESCODE(String BUSSESCODE) {
        this.BUSSESCODE = BUSSESCODE;
    }

    public String getSCSJ() {
        return SCSJ;
    }

    public void setSCSJ(String SCSJ) {
        this.SCSJ = SCSJ;
    }

    public String getFCSJ() {
        return FCSJ;
    }

    public void setFCSJ(String FCSJ) {
        this.FCSJ = FCSJ;
    }

    public String getStringContent() {
        StringBuilder sb = new StringBuilder();
        sb.append(TJGS == null ? "" : TJGS).append(",")
                .append(CURRENTTIME == null ? "" : CURRENTTIME).append(",")
                .append(FCBH == null ? "" : FCBH).append(",")
                .append(CPH == null ? "" : CPH).append(",")
                .append(SHGS == null ? "" : SHGS).append(",")
                .append(ZXDH == null ? "" : ZXDH).append(",")
                .append(ZT == null ? "" : ZT).append(",")
                .append(FCGS == null ? "" : FCGS).append(",")
                .append(DCGS == null ? "" : DCGS).append(",")
                .append(JHCKJS == null ? "" : JHCKJS).append(",")
                .append(SJCKJS == null ? "" : SJCKJS).append(",")
                .append(YDBH == null ? "" : YDBH).append(",")
                .append(ZL == null ? "" : ZL).append(",")
                .append(JS == null ? "" : JS).append(",")
                .append(TJ == null ? "" : TJ).append(",")
                .append(TZL == null ? "" : TZL).append(",")
                .append(TJS == null ? "" : TJS).append(",")
                .append(TTJ == null ? "" : TTJ).append(",")
                .append(LBLID == null ? "" : LBLID).append(",")
                .append(RCVMSGDATE == null ? "" : RCVMSGDATE).append(",")
                .append(SCANDATE == null ? "" : SCANDATE).append(",")
                .append(JHFCSJ == null ? "" : JHFCSJ).append(",")
                .append(XHGS == null ? "" : XHGS).append(",")
                .append(TJSJ == null ? "" : TJSJ);
        return sb.toString();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FhMxAndDhMxOut{TJGS='").append(TJGS == null ? "" : TJGS).append("'")
                .append(", CURRENTTIME='").append(CURRENTTIME == null ? "" : CURRENTTIME).append("'")
                .append(", FCBH='").append(FCBH == null ? "" : FCBH).append("'")
                .append(", CPH='").append(CPH == null ? "" : CPH).append("'")
                .append(", SHGS='").append(SHGS == null ? "" : SHGS).append("'")
                .append(", ZXDH='").append(ZXDH == null ? "" : ZXDH).append("'")
                .append(", ZT='").append(ZT == null ? "" : ZT).append("'")
                .append(", FCGS='").append(FCGS == null ? "" : FCGS).append("'")
                .append(", DCGS='").append(DCGS == null ? "" : DCGS).append("'")
                .append(", JHCKJS='").append(JHCKJS == null ? "" : JHCKJS).append("'")
                .append(", SJCKJS='").append(SJCKJS == null ? "" : SJCKJS).append("'")
                .append(", YDBH='").append(YDBH == null ? "" : YDBH).append("'")
                .append(", ZL='").append(ZL == null ? "" : ZL).append("'")
                .append(", JS='").append(JS == null ? "" : JS).append("'")
                .append(", TJ='").append(TJ == null ? "" : TJ).append("'")
                .append(", TZL='").append(TZL == null ? "" : TZL).append("'")
                .append(", TJS='").append(TJS == null ? "" : TJS).append("'")
                .append(", TTJ='").append(TTJ == null ? "" : TTJ).append("'")
                .append(", LBLID='").append(LBLID == null ? "" : LBLID).append("'")
                .append(", RCVMSGDATE='").append(RCVMSGDATE == null ? "" : RCVMSGDATE).append("'")
                .append(", SCANDATE='").append(SCANDATE == null ? "" : SCANDATE).append("'")
                .append(", JHFCSJ='").append(JHFCSJ == null ? "" : JHFCSJ).append("'")
                .append(", XHGS='").append(SCSJ == null ? "" : XHGS).append("'")
                .append(", TJSJ='").append(TJSJ == null ? "" : TJSJ).append("'")
                .append(", NAMELINE='").append(NAMELINE == null ? "" : NAMELINE).append("'")
                .append(", BUSSESCODE='").append(BUSSESCODE == null ? "" : BUSSESCODE).append("'")
                .append(", SCSJ='").append(SCSJ == null ? "" : SCSJ).append("'")
                .append(", FCSJ='").append(FCSJ == null ? "" : FCSJ).append("'")
                .append("}");
        return sb.toString();
    }
}
