package com.hoau.bigdata.service;

/**
 * @author ：Joel.Li
 * @date ：2021/1/4 15:14
 * @description： description
 * @version: $version
 */
public interface TaskService {

    /**
     * @author: joel
     * @description: 根据开始时间和结束时间抽取数据
     * @params: start:开始时间
     * @params: end:结束时间
     * @return:
     * @time: 2021/1/4 15:17
     */
    public boolean clxxdhExtract(String start,String end);
}
