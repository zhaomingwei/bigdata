package com.hoau.bigdata.test;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

/**
 * @author ：Joel.Li
 * @date ：2020/11/5 18:57
 * @description： 连接Drill测试
 * @version: $version
 */
public class ConnectDrillTest {

    public static void ContentTest() {
        try {
            Class.forName("org.apache.drill.jdbc.Driver");
            Connection connection = DriverManager.getConnection("jdbc:drill:zk=master:2181/drill/drill-master");
            Statement st = connection.createStatement();
            ResultSet rs = st.executeQuery("select TJGS,ZT,FCBH,CPH,ZL,TJ,JHCKJS,SJCKJS,SJSMJS,SJSMKSSJ,SMZXSJ,PDASCANRATE,XCWCL,max(TJSJ) as TJSJ from myhive.report.dhtjtable where CZ = '8850' and datediff(from_unixtime(unix_timestamp(),'yyyy-MM-dd HH:mm:ss'), TJSJ)<3 group by FCBH,TJGS,ZT,CPH,ZL,TJ,JHCKJS,SJCKJS,SJSMJS,SJSMKSSJ,SMZXSJ,PDASCANRATE,XCWCL limit 100");
            while (rs.next()) {
                System.out.println(rs.getString(1));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void main(String args[]) {
        String str = "TimeLineEntity{nameLine='02565850', timeWayAll=null, timeWay=null, transitTime=180.0, departureTime='20:00', timeDepart='null', timeInterrupt='null', todayOrTomorrow=null}";
        String[] timeLineEntities = str.split("TimeLineEntity");
        System.out.println(timeLineEntities[1]);
    }
}
