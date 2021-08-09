package com.hoau.bigdata.entity;

import lombok.Data;

/**
 * @author ：Joel.Li
 * @date ：2020/11/9 16:15
 * @description： 到货统计查询
 * @version: $version
 */
@Data
public class DhtjQuery extends Query {

    /**
     * 下货公司（场站）
     */
    private String xhgs;

}
