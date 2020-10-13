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
 * @Date: 2020/9/29
 * @Time: 17:01
 */
public class StationDetail implements Writable, DBWritable {

    /**
     * 公司简称
     */
    private String GSJC;

    /**
     * 发车编号
     */
    private String FCBH;

    /**
     * 运单编号
     */
    private String YDBH;

    /**
     * 重量
     */
    private String WEIGHT;

    /**
     * 体积
     */
    private String VOLUME;

    /**
     * 件数
     */
    private String NUM_GOODS;

    /**
     * 计划件数
     */
    private String NUM_GOODS_PLAN;

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        Text.writeString(dataOutput, this.GSJC);
        Text.writeString(dataOutput, this.FCBH);
        Text.writeString(dataOutput, this.YDBH);
        Text.writeString(dataOutput, this.WEIGHT);
        Text.writeString(dataOutput, this.VOLUME);
        Text.writeString(dataOutput, this.NUM_GOODS);
        Text.writeString(dataOutput, this.NUM_GOODS_PLAN);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.GSJC = Text.readString(dataInput);
        this.FCBH = Text.readString(dataInput);
        this.YDBH = Text.readString(dataInput);
        this.WEIGHT = Text.readString(dataInput);
        this.VOLUME = Text.readString(dataInput);
        this.NUM_GOODS = Text.readString(dataInput);
        this.NUM_GOODS_PLAN = Text.readString(dataInput);
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, GSJC);
        preparedStatement.setString(2, FCBH);
        preparedStatement.setString(3, YDBH);
        preparedStatement.setString(4, WEIGHT);
        preparedStatement.setString(5, VOLUME);
        preparedStatement.setString(6, NUM_GOODS);
        preparedStatement.setString(7, NUM_GOODS_PLAN);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.GSJC = resultSet.getString(1);
        this.FCBH = resultSet.getString(2);
        this.YDBH = resultSet.getString(3);
        this.WEIGHT = resultSet.getString(4);
        this.VOLUME = resultSet.getString(5);
        this.NUM_GOODS = resultSet.getString(6);
        this.NUM_GOODS_PLAN = resultSet.getString(7);
    }

    public String getGSJC() {
        return GSJC;
    }

    public void setGSJC(String GSJC) {
        this.GSJC = GSJC;
    }

    public String getFCBH() {
        return FCBH;
    }

    public void setFCBH(String FCBH) {
        this.FCBH = FCBH;
    }

    public String getYDBH() {
        return YDBH;
    }

    public void setYDBH(String YDBH) {
        this.YDBH = YDBH;
    }

    public String getWEIGHT() {
        return WEIGHT;
    }

    public void setWEIGHT(String WEIGHT) {
        this.WEIGHT = WEIGHT;
    }

    public String getVOLUME() {
        return VOLUME;
    }

    public void setVOLUME(String VOLUME) {
        this.VOLUME = VOLUME;
    }

    public String getNUM_GOODS() {
        return NUM_GOODS;
    }

    public void setNUM_GOODS(String NUM_GOODS) {
        this.NUM_GOODS = NUM_GOODS;
    }

    public String getNUM_GOODS_PLAN() {
        return NUM_GOODS_PLAN;
    }

    public void setNUM_GOODS_PLAN(String NUM_GOODS_PLAN) {
        this.NUM_GOODS_PLAN = NUM_GOODS_PLAN;
    }

    @Override
    public String toString() {
        return "{" +
                "GSJC='" + GSJC + '\'' +
                ", FCBH='" + FCBH + '\'' +
                ", YDBH='" + YDBH + '\'' +
                ", WEIGHT='" + WEIGHT + '\'' +
                ", VOLUME='" + VOLUME + '\'' +
                ", NUM_GOODS='" + NUM_GOODS + '\'' +
                ", NUM_GOODS_PLAN='" + NUM_GOODS_PLAN + '\'' +
                '}';
    }
}
