package hoau.com.cn.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020/10/16
 * @Time: 13:11
 */
public class Config {

    private static final Logger logger = LoggerFactory.getLogger(Config.class);

    public static PropertiesUtil propertiesUtil;

    static {
        initConfig();
    }

    /**
     * 配置信息初始化
     */
    private static void initConfig() {
        if (propertiesUtil == null) {
            try {
                propertiesUtil = new PropertiesUtil("conf.properties");
            } catch (Exception e) {
                logger.error("Config init fail:{}", e);
            }
        }
    }

    /**
     * 获取参数
     *
     * @param key 参数名
     * @return 参数值, 读取失败则为null
     */
    public static String getProperty(String key){
        return propertiesUtil.getProperty(key);
    }

}
