package hoau.com.cn;

import hoau.com.cn.utils.DateUtils;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Hello world!
 *
 */
public class App
{
    public static void main( String[] args )
    {
        System.out.println( "Hello World!" );
        System.out.println(DateUtils.formatStringToDate("2020-10-21 14:26:14.0", "yyyy-MM-dd HH:mm:ss"));
        System.out.println(DateUtils.formatDateToString(new Date(), "yyyy-MM-dd HH:mm:ss"));
        System.out.println(new BigDecimal(".23"));
    }
}
