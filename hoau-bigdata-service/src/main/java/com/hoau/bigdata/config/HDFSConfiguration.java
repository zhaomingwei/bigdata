package com.hoau.bigdata.config;

import org.apache.hadoop.fs.FileSystem;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.IOException;

/**
 * @author ：Joel.Li
 * @date ：2021/1/16 10:20
 * @description： hdfs配置
 * @version: $version
 */
//@Slf4j
@Configuration
public class HDFSConfiguration {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${name}")
    public String userName;

    @Bean
    public org.apache.hadoop.conf.Configuration getConfiguration() {
        org.apache.hadoop.conf.Configuration conf = new org.apache.hadoop.conf.Configuration();
        conf.set("hadoop.job.ugi", userName);
        conf.set("dfs.client.failover.proxy.provider.ns1", "org.apache.hadoop.hdfs.server.namenode.ha.ConfiguredFailoverProxyProvider");

        //文件写入配置
        conf.set("fs.hdfs.impl.disable.cache", "true");
        conf.setBoolean("dfs.support.append", true);
        conf.setBoolean("dfs.client.block.write.replace-datanode-on-failure.enabled", true);
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");

        return conf;
    }

    @Bean
    public FileSystem getFileSystem() {
        FileSystem fileSystem = null;
        try {
            fileSystem = FileSystem.get(getConfiguration());
        } catch (IOException e) {
            // TODO Auto-generated catch block
            logger.error(e.getMessage());
        }
        return fileSystem;
    }

}