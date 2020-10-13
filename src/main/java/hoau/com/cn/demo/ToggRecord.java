package hoau.com.cn.demo;

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
 * @Description: 封装数据库实体信息的记录
 * @Author: zhaowei
 * @Date: 2020/9/27
 * @Time: 10:59
 */
public class ToggRecord implements Writable, DBWritable {

    /**
     * 对应数据库中id字段
     */
    public Integer id;

    /**
     * 对应数据库中text_name字段
     */
    public String text_name;

    @Override
    public void write(DataOutput dataOutput) throws IOException {
        dataOutput.writeInt(id);
        Text.writeString(dataOutput, this.text_name);
    }

    @Override
    public void readFields(DataInput dataInput) throws IOException {
        this.id = dataInput.readInt();
        this.text_name = Text.readString(dataInput);
    }

    @Override
    public void write(PreparedStatement preparedStatement) throws SQLException {
        preparedStatement.setInt(1, id);
        preparedStatement.setString(2, text_name);
    }

    @Override
    public void readFields(ResultSet resultSet) throws SQLException {
        this.id = resultSet.getInt(1);
        this.text_name = resultSet.getString(2);
    }

    @Override
    public String toString() {
        return "ToggRecord{" +
                "id=" + id +
                ", text_name='" + text_name + '\'' +
                '}';
    }
}
