package com.hoau.bigdata.mapper.report;

import com.hoau.bigdata.entity.report.FhMxAndDhMx;
import com.hoau.bigdata.entity.report.FhMxAndDhMxOut;
import com.hoau.bigdata.entity.tm.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DhMxDao {

    /**
     * 功能描述: <br>
     * 〈查询1分钟内（包括一分钟）在途、卸车完成状态到货明细列表〉
     * @param sec 距离最近秒
     * @Return: com.hoau.bigdata.entity.report.FhMxAndDhMx
     */
    List<FhMxAndDhMx> queryInTimeDhMxList(@Param("sec") int sec);

    /**
     * 功能描述: <br>
     * 〈查询1分钟之前2天之内在途、卸车完成状态到货明细列表〉
     * @param day 距离最近几天
     * @param sec 超过秒数
     * @Return: com.hoau.bigdata.entity.report.FhMxAndDhMx
     */
    List<FhMxAndDhMx> queryOutTimeDhMxList(@Param("day") int day, @Param("sec") int sec);

    /**
     * 功能描述: <br>
     * 〈查询1分钟内（包括一分钟）在途、卸车完成状态上转移到货明细列表〉
     * @param sec 距离最近秒
     * @Return: com.hoau.bigdata.entity.report.FhMxAndDhMx
     */
    List<FhMxAndDhMx> queryInTimeSZYDhMxList(@Param("sec") int sec);

    /**
     * 功能描述: <br>
     * 〈查询1分钟之前2天之内在途、卸车完成状态上转移到货明细列表〉
     * @param day 距离最近几天
     * @param sec 超过秒数
     * @Return: com.hoau.bigdata.entity.report.FhMxAndDhMx
     */
    List<FhMxAndDhMx> queryOutTimeSZYDhMxList(@Param("day") int day, @Param("sec") int sec);

    /**
     * 通过标签信息查询长途发货明细
     * @param list
     * @return
     */
    List<FhMxAndDhMxOut> queryLxDhCtList(List<Label> list);

    /**
     * 通过标签信息查询上转移明细
     * @param list
     * @return
     */
    List<FhMxAndDhMxOut> queryLxDhSzyList(List<Label> list);

    /**
     * 长途到货按发车编号分组统计该发车编号下所有运单的总重量、总体积、总件数
     * @param reqList
     * @return
     */
    List<FhMxAndDhMxOut> queryCtDhTotalDataByFcbh(List<FhMxAndDhMxOut> reqList);

    /**
     * 上转移到货按发车编号分组统计该发车编号下所有运单的总重量、总体积、总件数
     * @param reqList
     * @return
     */
    List<FhMxAndDhMxOut> querySzyDhTotalDataByFcbh(List<FhMxAndDhMxOut> reqList);
}
