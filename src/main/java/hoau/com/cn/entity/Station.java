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
 * @Description: 场站实体类
 * @Author: zhaowei
 * @Date: 2020/9/27
 * @Time: 16:48
 */
public class Station implements Writable, DBWritable {

    /**
     * id
     */
    private Integer id;

    /**
     * 场站简称
     */
    private String czjc;

    /**
     * 所属大区
     */
    private String ssdq;

    /**
     * 所属事业部
     */
    private String sssyb;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCzjc() {
        return czjc;
    }

    public void setCzjc(String czjc) {
        this.czjc = czjc;
    }

    public String getSsdq() {
        return ssdq;
    }

    public void setSsdq(String ssdq) {
        this.ssdq = ssdq;
    }

    public String getSssyb() {
        return sssyb;
    }

    public void setSssyb(String sssyb) {
        this.sssyb = sssyb;
    }

    @Override
    public String toString() {
        return "Station{" +
                "id=" + id +
                ", czjc='" + czjc + '\'' +
                ", ssdq='" + ssdq + '\'' +
                ", sssyb='" + sssyb + '\'' +
                '}';
    }

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(id);
        Text.writeString(dataOutput, this.czjc);
        Text.writeString(dataOutput, this.ssdq);
        Text.writeString(dataOutput, this.sssyb);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readInt();
        this.czjc = Text.readString(dataInput);
        this.ssdq = Text.readString(dataInput);
        this.sssyb = Text.readString(dataInput);
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, czjc);
        preparedStatement.setString(3, ssdq);
        preparedStatement.setString(4, sssyb);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt(1);
        this.czjc = resultSet.getString(2);
        this.ssdq = resultSet.getString(3);
        this.sssyb = resultSet.getString(4);
    }
}
