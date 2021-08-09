package com.hoau.bigdata.service;


import com.hoau.bigdata.entity.DhtjQuery;
import com.hoau.bigdata.entity.drill.ArrivalInfo;
import com.hoau.bigdata.entity.drill.FhdmxEntity;
import com.hoau.bigdata.entity.FhtjQuery;

import java.util.List;

/**
 * @author ：Joel.Li
 * @date ：2020/11/6 14:12
 * @description： description
 * @version: $version
 */
public interface DrillService {

    List<FhdmxEntity> dhtjQuery(DhtjQuery dhtjQuery);

    List<FhdmxEntity> fhtjQuery(FhtjQuery fhtjQuery);

    List<ArrivalInfo> clxxdhQuery(DhtjQuery dhtjQuery);
}
