package com.hoau.bigdata.entity.drill;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：Joel.Li
 * @date ：2020/11/6 14:14
 * @description： description
 * @version: $version
 */
@Data
public class DhtjEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 场站
     */
    private String CZ;

    /**
     * 统计公司
     */
    private String TJGS;

    /**
     * 状态
     */
    private String ZT;

    /**
     * 统计时间
     */
    private String TJSJ;

    /**
     * 发车编号
     */
    private String FCBH;

    /**
     * 车牌号
     */
    private String CPH;

    /**
     * 重量
     */
    private String ZL;

    /**
     * 体积
     */
    private String TJ;

    /**
     * 计划出库件数
     */
    private String JHCKJS;

    /**
     * 实际出库件数
     */
    private String SJCKJS;

    /**
     * 实际扫描件数(一个发车编号下的标签扫描件数)
     */
    private String SJSMJS;

    /**
     * 实际扫描开始时间
     */
    private String SJSMKSSJ;

    /**
     * 扫描最新时间
     */
    private String SMZXSJ;

    /**
     * PDA扫描率 : 实际扫描件数（即Label的总数） / JHCKJS（计划出库件数）
     */
    private String PDASCANRATE;

    /**
     * 卸车完成率 : 实际扫描件数 / 计划件数
     */
    private String XCWCL;


}
