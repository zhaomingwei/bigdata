package com.hoau.bigdata.entity;

import com.hoau.bigdata.entity.Query;
import lombok.Data;

/**
 * @description： 发货统计查询
 * @version: $version
 */
@Data
public class FhtjQuery extends Query {

    /**
     * 上货公司（场站）
     */
    private String shgs;

}
