package com.hoau.bigdata.mapper.report;

import com.hoau.bigdata.entity.report.FhMxAndDhMx;
import com.hoau.bigdata.entity.report.FhMxAndDhMxOut;
import com.hoau.bigdata.entity.tm.Label;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface FhMxDao {

    /**
     * 功能描述: <br>
     *     长途发货：
     * 〈查询1分钟内（包括一分钟）准备发货、发货完成状态发货明细列表〉
     * @param sec 距离最近秒
     * @Return: com.hoau.bigdata.entity.report.FhMxAndDhMx
     */
    List<FhMxAndDhMx> queryInTimeLDFhMxList(@Param("sec") int sec);

    /**
     * 功能描述: <br>
     *     长途发货：
     * 〈查询1分钟之前2天之内准备发货、发货完成状态发货明细列表〉
     * @param day 距离最近几天
     * @param sec 超过秒数
     * @Return: com.hoau.bigdata.entity.report.FhMxAndDhMx
     */
    List<FhMxAndDhMx> queryOutTimeLDFhMxList(@Param("day") int day, @Param("sec") int sec);

    /**
     * 功能描述: <br>
     *     中转发货：
     * 〈查询1分钟内（包括一分钟）准备发货、发货完成状态发货明细列表〉
     * @param sec 距离最近秒
     * @Return: com.hoau.bigdata.entity.report.FhMxAndDhMx
     */
    List<FhMxAndDhMx> queryInTimeTSFhMxList(@Param("sec") int sec);

    /**
     * 功能描述: <br>
     *     中转发货：
     * 〈查询1分钟之前2天之内准备发货、发货完成状态发货明细列表〉
     * @param day 距离最近几天
     * @param sec 超过秒数
     * @Return: com.hoau.bigdata.entity.report.FhMxAndDhMx
     */
    List<FhMxAndDhMx> queryOutTimeTSFhMxList(@Param("day") int day, @Param("sec") int sec);

    /**
     * 功能描述: <br>
     *     下转移发货：
     * 〈查询1分钟内（包括一分钟）准备发货、发货完成状态发货明细列表〉
     * @param sec 距离最近秒
     * @Return: com.hoau.bigdata.entity.report.FhMxAndDhMx
     */
    List<FhMxAndDhMx> queryInTimeDTFhMxList(@Param("sec") int sec);

    /**
     * 功能描述: <br>
     *     下转移发货：
     * 〈查询1分钟之前2天之内准备发货、发货完成状态发货明细列表〉
     * @param day 距离最近几天
     * @param sec 超过秒数
     * @Return: com.hoau.bigdata.entity.report.FhMxAndDhMx
     */
    List<FhMxAndDhMx> queryOutTimeDTFhMxList(@Param("day") int day, @Param("sec") int sec);

    /**
     * 功能描述: <br>
     *     派送发货：
     * 〈查询1分钟内（包括一分钟）准备发货、发货完成状态发货明细列表〉
     * @param sec 距离最近秒
     * @Return: com.hoau.bigdata.entity.report.FhMxAndDhMx
     */
    List<FhMxAndDhMx> queryInTimeDLFhMxList(@Param("sec") int sec);

    /**
     * 功能描述: <br>
     *     派送发货：
     * 〈查询1分钟之前2天之内准备发货、发货完成状态发货明细列表〉
     * @param day 距离最近几天
     * @param sec 超过秒数
     * @Return: com.hoau.bigdata.entity.report.FhMxAndDhMx
     */
    List<FhMxAndDhMx> queryOutTimeDLFhMxList(@Param("day") int day, @Param("sec") int sec);

    /**
     * 通过标签信息查询长途发货明细
     * @param list
     * @return
     */
    List<FhMxAndDhMxOut> queryLxFhCtList(List<Label> list);

    /**
     * 通过标签信息查询中转发货明细
     * @param list
     * @return
     */
    List<FhMxAndDhMxOut> queryLxFhZzList(List<Label> list);

    /**
     * 通过标签信息查询下转移明细
     * @param list
     * @return
     */
    List<FhMxAndDhMxOut> queryLxFhXzyList(List<Label> list);

    /**
     * 通过标签信息查询派送明细
     * @param list
     * @return
     */
    List<FhMxAndDhMxOut> queryLxFhPsList(List<Label> list);

    /**
     * 派送按发车编号分组统计该发车编号下所有运单的总重量、总体积、总件数
     * @param reqList
     * @return
     */
    List<FhMxAndDhMxOut> queryPsFhTotalDataByFcbh(List<FhMxAndDhMxOut> reqList);

    /**
     * 下转移发货按发车编号分组统计该发车编号下所有运单的总重量、总体积、总件数
     * @param reqList
     * @return
     */
    List<FhMxAndDhMxOut> queryXzyFhTotalDataByFcbh(List<FhMxAndDhMxOut> reqList);

    /**
     * 长途发货按发车编号分组统计该发车编号下所有运单的总重量、总体积、总件数
     * @param reqList
     * @return
     */
    List<FhMxAndDhMxOut> queryCtFhTotalDataByFcbh(List<FhMxAndDhMxOut> reqList);

    /**
     * 中转发货按发车编号分组统计该发车编号下所有运单的总重量、总体积、总件数
     * @param reqList
     * @return
     */
    List<FhMxAndDhMxOut> queryZzFhTotalDataByFcbh(List<FhMxAndDhMxOut> reqList);
}
