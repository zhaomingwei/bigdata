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
 * @Date: 2020/9/27
 * @Time: 15:06
 */
public class FcInfo implements Writable, DBWritable {

    public String FCBH;

    public Integer FHDBH;


    @Override
    public void write(DataOutput dataOutput) throws IOException {
        Text.writeString(dataOutput, this.FCBH);
        dataOutput.writeInt(FHDBH);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.FCBH = Text.readString(dataInput);
        this.FHDBH = dataInput.readInt();
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setString(1, FCBH);
        preparedStatement.setInt(2, FHDBH);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.FCBH = resultSet.getString(1);
        this.FHDBH = resultSet.getInt(2);
    }

    @Override
    public String toString() {
        return "FcInfo{" +
                "FCBH='" + FCBH + '\'' +
                ", FHDBH='" + FHDBH + '\'' +
                '}';
    }
}
