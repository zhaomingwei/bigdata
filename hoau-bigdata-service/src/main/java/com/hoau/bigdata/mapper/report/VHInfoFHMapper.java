package com.hoau.bigdata.mapper.report;

import com.hoau.bigdata.entity.report.ArrivalInfoTmp;
import com.hoau.bigdata.entity.report.TimeLineEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @author ：Joel.Li
 * @date ：2020/11/25 11:34
 * @description： description
 * @version: $version
 */
@Mapper
public interface VHInfoFHMapper {

    /**
     * @author: joel
     * @description: 获取1分钟内信息
     * @params: @param null:
     * @return:
     * @time: 2020/11/25 11:34
     */
    List<ArrivalInfoTmp> getSZYDH();

    /**
     * @author: joel
     * @description: 长途到货
     * @params: @param null:
     * @return:
     * @time: 2020/11/27 15:50
     */
    List<ArrivalInfoTmp> getCTDH();

    /**
     * @author: joel
     * @description: 中转到货
     * @params: @param null:
     * @return:
     * @time: 2020/11/27 16:52
     */
    List<ArrivalInfoTmp> getZZDH();

    /**
     * @author: joel
     * @description: 上转移线路时间拉取
     * @params: @param null:
     * @return:
     * @time: 2020/11/26 9:43
     */
    List<TimeLineEntity> szyLineTime();

    /**
     * @author: joel
     * @description: 长途线路时间拉取
     * @params: @param null:
     * @return:
     * @time: 2020/11/26 9:43
     */
    List<TimeLineEntity> ctLineTime();

    // TODO: 2021/1/4 备用 根据时间范围抽取数据 start

    /**
     * @author: joel
     * @description: 获取1分钟内信息
     * @params: @param null:
     * @return:
     * @time: 2020/11/25 11:34
     */
    List<ArrivalInfoTmp> getSZYDHBack(@Param("start") String start, @Param("end") String end);

    /**
     * @author: joel
     * @description: 长途到货
     * @params: @param null:
     * @return:
     * @time: 2020/11/27 15:50
     */
    List<ArrivalInfoTmp> getCTDHBack(@Param("start") String start, @Param("end") String end);

    /**
     * @author: joel
     * @description: 中转到货
     * @params: @param null:
     * @return:
     * @time: 2020/11/27 16:52
     */
    List<ArrivalInfoTmp> getZZDHBack(@Param("start") String start, @Param("end") String end);
    // TODO: 2021/1/4 备用 根据时间范围抽取数据 end

}
