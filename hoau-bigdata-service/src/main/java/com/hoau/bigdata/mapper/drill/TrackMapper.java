package com.hoau.bigdata.mapper.drill;

import com.hoau.bigdata.entity.drill.TrackRecordEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * @version: $version
 */
@Mapper
public interface TrackMapper {

    /**
     * @description: 查询运单轨迹信息
     */
    TrackRecordEntity queryWaybillNoInfo(@Param("waybillNo") String waybillNo);

}
