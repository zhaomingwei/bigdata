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
 * @Description: 发货单明细
 * @Author: zhaowei
 * @Date: 2020/10/20
 * @Time: 9:07
 */
public class FhMxAndDhMx implements Writable, DBWritable {
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
     * 下货地公司
     */
    private String XHGS;
    /**
     * 统计公司
     */
    private String TJGS;
    /**
     * 装卸单号
     */
    private String ZXDH;
    /**
     * 状态
     */
    private String ZT;
    /**
     * 统计时间
     */
    private String TJSJ;
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
     * 计划发车时间
     */
    private String JHFCSJ;
    /**
     * 数据类型：1为1分钟内，2为超过1分钟
     */
    private String TAG;
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
        Text.writeString(out, this.FCBH);
        Text.writeString(out, this.CPH);
        Text.writeString(out, this.SHGS);
        Text.writeString(out, this.XHGS);
        Text.writeString(out, this.TJGS);
        Text.writeString(out, this.ZXDH);
        Text.writeString(out, this.ZT);
        Text.writeString(out, this.TJSJ);
        Text.writeString(out, this.FCGS);
        Text.writeString(out, this.DCGS);
        Text.writeString(out, this.JHCKJS);
        Text.writeString(out, this.SJCKJS);
        Text.writeString(out, this.YDBH);
        Text.writeString(out, this.ZL);
        Text.writeString(out, this.JS);
        Text.writeString(out, this.TJ);
        Text.writeString(out, this.JHFCSJ);
        Text.writeString(out, this.TAG);
        Text.writeString(out, this.NAMELINE);
        Text.writeString(out, this.BUSSESCODE);
        Text.writeString(out, this.SCSJ);
        Text.writeString(out, this.FCSJ);
    }

    @Override
    public void readFields(DataInput in) throws IOException {
        this.FCBH = Text.readString(in);
        this.CPH = Text.readString(in);
        this.SHGS = Text.readString(in);
        this.XHGS = Text.readString(in);
        this.TJGS = Text.readString(in);
        this.ZXDH = Text.readString(in);
        this.ZT = Text.readString(in);
        this.TJSJ = Text.readString(in);
        this.FCGS = Text.readString(in);
        this.DCGS = Text.readString(in);
        this.JHCKJS = Text.readString(in);
        this.SJCKJS = Text.readString(in);
        this.YDBH = Text.readString(in);
        this.ZL = Text.readString(in);
        this.JS = Text.readString(in);
        this.TJ = Text.readString(in);
        this.JHFCSJ = Text.readString(in);
        this.TAG = Text.readString(in);
        this.NAMELINE = Text.readString(in);
        this.BUSSESCODE = Text.readString(in);
        this.SCSJ = Text.readString(in);
        this.FCSJ = Text.readString(in);
    }

    @Override
    public void write(PreparedStatement statement) throws SQLException {
        statement.setString(1, FCBH);
        statement.setString(2, CPH);
        statement.setString(3, SHGS);
        statement.setString(4, XHGS);
        statement.setString(5, TJGS);
        statement.setString(6, ZXDH);
        statement.setString(7, ZT);
        statement.setString(8, TJSJ);
        statement.setString(9, FCGS);
        statement.setString(10, DCGS);
        statement.setString(11, JHCKJS);
        statement.setString(12, SJCKJS);
        statement.setString(13, YDBH);
        statement.setString(14, ZL);
        statement.setString(15, JS);
        statement.setString(16, TJ);
        statement.setString(17, JHFCSJ);
        statement.setString(18, TAG);
        statement.setString(19, NAMELINE);
        statement.setString(20, BUSSESCODE);
        statement.setString(21, SCSJ);
        statement.setString(22, FCSJ);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.FCBH = resultSet.getString(1);
        this.CPH = resultSet.getString(2);
        this.SHGS = resultSet.getString(3);
        this.XHGS = resultSet.getString(4);
        this.TJGS = resultSet.getString(5);
        this.ZXDH = resultSet.getString(6);
        this.ZT = resultSet.getString(7);
        this.TJSJ = resultSet.getString(8);
        this.FCGS = resultSet.getString(9);
        this.DCGS = resultSet.getString(10);
        this.JHCKJS = resultSet.getString(11);
        this.SJCKJS = resultSet.getString(12);
        this.YDBH = resultSet.getString(13);
        this.ZL = resultSet.getString(14);
        this.JS = resultSet.getString(15);
        this.TJ = resultSet.getString(16);
        this.JHFCSJ = resultSet.getString(17);
        this.TAG = resultSet.getString(18);
        this.NAMELINE = resultSet.getString(19);
        this.BUSSESCODE = resultSet.getString(20);
        this.SCSJ = resultSet.getString(21);
        this.FCSJ = resultSet.getString(22);
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

    public String getTJGS() {
        return TJGS;
    }

    public void setTJGS(String TJGS) {
        this.TJGS = TJGS;
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

    public String getTJSJ() {
        return TJSJ;
    }

    public void setTJSJ(String TJSJ) {
        this.TJSJ = TJSJ;
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

    public String getJHFCSJ() {
        return JHFCSJ;
    }

    public void setJHFCSJ(String JHFCSJ) {
        this.JHFCSJ = JHFCSJ;
    }

    public String getTAG() {
        return TAG;
    }

    public void setTAG(String TAG) {
        this.TAG = TAG;
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

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("FhMxAndDhMx{FCBH='").append(FCBH == null ? "" : FCBH).append("'")
                .append(", CPH='").append(CPH == null ? "" : CPH).append("'")
                .append(", SHGS='").append(SHGS == null ? "" : SHGS).append("'")
                .append(", XHGS='").append(XHGS == null ? "" : XHGS).append("'")
                .append(", TJGS='").append(TJGS == null ? "" : TJGS).append("'")
                .append(", ZXDH='").append(ZXDH == null ? "" : ZXDH).append("'")
                .append(", ZT='").append(ZT == null ? "" : ZT).append("'")
                .append(", TJSJ='").append(TJSJ == null ? "" : TJSJ).append("'")
                .append(", FCGS='").append(FCGS == null ? "" : FCGS).append("'")
                .append(", DCGS='").append(DCGS == null ? "" : DCGS).append("'")
                .append(", JHCKJS='").append(JHCKJS == null ? "" : JHCKJS).append("'")
                .append(", SJCKJS='").append(SJCKJS == null ? "" : SJCKJS).append("'")
                .append(", YDBH='").append(YDBH == null ? "" : YDBH).append("'")
                .append(", ZL='").append(ZL == null ? "" : ZL).append("'")
                .append(", JS='").append(JS == null ? "" : JS).append("'")
                .append(", TJ='").append(TJ == null ? "" : TJ).append("'")
                .append(", JHFCSJ='").append(JHFCSJ == null ? "" : JHFCSJ).append("'")
                .append(", TAG='").append(TAG == null ? "" : TAG).append("'")
                .append(", NAMELINE='").append(NAMELINE == null ? "" : NAMELINE).append("'")
                .append(", BUSSESCODE='").append(BUSSESCODE == null ? "" : BUSSESCODE).append("'")
                .append(", SCSJ='").append(SCSJ == null ? "" : SCSJ).append("'")
                .append(", FCSJ='").append(FCSJ == null ? "" : FCSJ).append("'")
                .append("}");
        return sb.toString();
    }
}
