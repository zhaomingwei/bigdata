package com.hoau.bigdata.mapper.drill;

import com.hoau.bigdata.entity.DhtjQuery;
import com.hoau.bigdata.entity.drill.ArrivalInfo;
import com.hoau.bigdata.entity.drill.FhdmxEntity;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

/**
 * @author ：Joel.Li
 * @date ：2020/7/29 16:18
 * @description： description
 * @version: $version
 */
@Mapper
public interface DrillMapper {

    /**
     * @author: joel
     * @description: 到货统计查询
     * @params: @param null:
     * @return:
     * @time: 2020/11/9 16:21
     */
    List<FhdmxEntity> dhtjQuery(Map map);

    /**
     * @author: zhaowei
     * @description: 发货统计查询
     * @params: @param shgs: 上货公司编号
     * @return:
     */
    List<FhdmxEntity> fhtjQuery(Map map);

    /**
     * @author: joel
     * @description: 车辆信息--到货查询
     * @params: @param null:
     * @return:
     * @time: 2020/12/3 16:16
     */
    List<ArrivalInfo> clxxdhQuery(DhtjQuery dhtjQuery);

    /**
     * @author: joel
     * @description: 根据发车编号请求判断扫描记录
     * @params: @param null:
     * @return:
     * @time: 2020/12/4 11:06
     */
    List<String> dhtjQueryByFcbh(List<String> fcbhs);
}
