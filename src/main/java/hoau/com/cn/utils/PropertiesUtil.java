package hoau.com.cn.utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hdfs.web.JsonUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Properties;


/**
 * @author zhaowei
 * @Desc 配置文件加载
 */
public class PropertiesUtil {

    private static final Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);

    private Properties props = null;
    private Map<String, String> map = new LinkedHashMap();
    private String fileName = null;

    public PropertiesUtil(String fileName) throws Exception {
        this.fileName = fileName;
        init();
    }

    private void init() throws Exception {
        InputStream in = null;
        try {
            props = new Properties();
            in = this.getClass().getClassLoader().getResourceAsStream(fileName);
            if (in == null) {
                throw new Exception("配置文件：" + fileName + ",加载错误！");
            }
            props.load(in);
            logger.info("配置文件:{},加载正常:{}", fileName, JsonUtil.toJsonString(getAllProperty()));
        } catch (IOException e) {
            logger.error("配置文件：" + fileName + ",加载错误,错误信息:{}", e.getMessage());
            throw e;
        } catch (Exception e) {
            logger.error("配置文件：" + fileName + ",加载错误,错误信息:{}", e.getMessage());
            throw e;
        } finally {
            if (in != null) {
                in.close();
            }
        }
    }

    /**
     * 获取参数
     *
     * @param key 参数名
     * @return 参数值, 读取失败则为null
     */
    public String getProperty(String key) {
        String value = null;
        try {
            value = props.getProperty(key);
            if(StringUtils.isBlank(value)){
                logger.warn("{}:{} not init!", fileName, key);
                return value;
            }
            value = value.trim();
            logger.debug("{}\t:\t{}", key, value);
        } catch (Exception e) {
            logger.error("{} init err!", key);
            logger.error(e.getMessage(), e);
        }
        return value;
    }

    /**
     * 获取所有属性，返回一个map
     */
    public Map<String, String> getAllProperty() {
        Enumeration<?> enu = props.propertyNames();
        while (enu.hasMoreElements()) {
            String key = (String) enu.nextElement();
            String value = props.getProperty(key);
            map.put(key, value);
        }
        return map;
    }

}