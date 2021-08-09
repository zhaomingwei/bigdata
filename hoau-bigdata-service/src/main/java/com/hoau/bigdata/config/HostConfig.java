package com.hoau.bigdata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020/11/17
 * @Time: 14:18
 */
@Configuration
public class HostConfig {

    @Value("${host}")
    public String host;

    @Value("${name}")
    public String userName;

    @Value("${password}")
    public String password;

    @Value("${charset}")
    public String charset;

    @Value("${exeFhmxCmd}")
    public String exeFhmxCmd;

    @Value("${exeLxFhCmd}")
    public String exeLxFhCmd;

    @Value("${exeTrackRecordCmd}")
    public String exeTrackRecordCmd;

    @Value("${exeDhmxCmd}")
    public String exeDhmxCmd;

    @Value("${exeClxxDHCmd}")
    public String exeClxxDHCmd;

}
