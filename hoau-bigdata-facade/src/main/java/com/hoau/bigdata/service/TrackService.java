package com.hoau.bigdata.service;


import com.hoau.bigdata.entity.drill.TrackRecordEntity;

/**
 * zxy
 * @version: $version
 */
public interface TrackService {

    /**
     * 查询运单轨迹信息
     * @param waybillNo
     * @return
     */
    TrackRecordEntity queryWaybillNoInfo(String waybillNo);
}
