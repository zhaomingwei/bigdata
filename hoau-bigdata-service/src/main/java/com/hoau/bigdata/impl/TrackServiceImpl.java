package com.hoau.bigdata.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hoau.bigdata.entity.drill.TrackRecordEntity;
import com.hoau.bigdata.mapper.drill.TrackMapper;
import com.hoau.bigdata.service.TrackService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author ：zxy
 * @description： description
 * @version: $version
 */
@Service
public class TrackServiceImpl implements TrackService {

    Logger logger = LoggerFactory.getLogger(getClass());


    @Autowired
    private TrackMapper trackMapper;

    @Override
    public TrackRecordEntity queryWaybillNoInfo(String waybillNo) {
        TrackRecordEntity recordEntities = new TrackRecordEntity();
        try {
            logger.info("开始查询运单轨迹信息--service--入参--》》" + waybillNo);
            recordEntities = trackMapper.queryWaybillNoInfo(waybillNo);
//            logger.info("结束查询运单轨迹信息--条数----》》" + JsonUtils.toJson(recordEntities));
            return recordEntities;
        } catch (Exception e){
            logger.warn("配置文件statusMapping.properties 读取失败！", e);
        }
        return recordEntities;
    }
}
