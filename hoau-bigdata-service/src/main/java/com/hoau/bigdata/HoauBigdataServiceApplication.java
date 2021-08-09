package com.hoau.bigdata;


import com.alibaba.dubbo.config.spring.context.annotation.DubboComponentScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = {"com.hoau.bigdata"})
@DubboComponentScan(basePackages = "com.hoau.bigdata.impl")
public class HoauBigdataServiceApplication {
    public static void main(String[] args) {
        try {

            SpringApplication.run(HoauBigdataServiceApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
