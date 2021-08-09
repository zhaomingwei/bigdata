package com.hoau.bigdata.entity.drill;

import lombok.Data;

import java.io.Serializable;

/**
 * @author ：Joel.Li
 * @date ：2020/11/6 14:14
 * @description： 到货明细
 * @version: $version
 */
@Data
public class FhdmxEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    /**
     * 统计公司
     */
    private String tjgs;

    /**
     * 发车编号
     */
    private String fcbh;

    /**
     * 车牌号
     */
    private String cph;

    /**
     * 发车公司
     */
    private String fcgs;

    /**
     * 到车公司
     */
    private String dcgs;

    /**
     * 状态
     */
    private int zt;

    /**
     * 件数
     */
    private int js;

    /**
     * 重量
     */
    private double zl;

    /**
     * 体积
     */
    private double tj;

    /**
     * 扫描率
     */
    private String sml;

    /**
     * 卸车完成率
     */
    private String xcwcl;

    /**
     * 开始扫描时间
     */
    private String kssmsj;

    /**
     * 结束扫描时间
     */
    private String jssmsj;

    /**
     * 统计时间
     */
    private String tjsj;

    /**
     * 计划发车时间
     */
    private String jhfcsj;
}
