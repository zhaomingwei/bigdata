package com.hoau.bigdata.utils;

import java.util.List;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020/11/17
 * @Time: 14:58
 */
public class ContentStringUtils {

    public static <T> String getStringContent(List<T> list) {
        StringBuilder sb = new StringBuilder();
        for (T t : list) {
            sb.append(t.toString()).append("\n");
        }
        return sb.toString();
    }

}
