package com.hoau.bigdata;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;


@SpringBootApplication(scanBasePackages = {"com.hoau.bigdata"})
@ServletComponentScan
public class HoauBigdataControllerApplication {
    public static void main(String[] args) {
        try {

            SpringApplication.run(HoauBigdataControllerApplication.class, args);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
