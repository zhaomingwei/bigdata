package com.hoau.bigdata.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.hoau.bigdata.entity.DhtjQuery;
import com.hoau.bigdata.entity.drill.ArrivalInfo;
import com.hoau.bigdata.entity.drill.FhdmxEntity;
import com.hoau.bigdata.entity.FhtjQuery;
import com.hoau.bigdata.service.DrillService;
import com.hoau.bigdata.service.TaskService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.thymeleaf.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * @Author: joel
 * @Date: 2020/11/05 16:30
 * @Description: 测试controller
 */

@RestController
public class BigdataController {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Reference(timeout = 200000, retries = -1)
    DrillService drillService;

    @Reference(timeout = 200000, retries = -1)
    TaskService taskService;


    @PostMapping("dhtj")
    public String dhtj(@RequestBody DhtjQuery dhtjQuery) {
//        logger.info("效率监控看板-到货查询==controller===入参===》》" + JsonUtils.toJson(dhtjQuery));
        String xhgs = dhtjQuery.getXhgs();
        if (null == xhgs || StringUtils.equals("", xhgs)) {
            return "效率监控看板-到货场站为空";
        }
        List<FhdmxEntity> maps = drillService.dhtjQuery(dhtjQuery);
        logger.info("效率监控看板-到货查询返回条数==》" + maps.size());
//        return JsonUtils.toJson(maps);
        return null;
    }

    @PostMapping("fhtj")
    public String fhtj(@RequestBody FhtjQuery fhtjQuery) {
//        logger.info("效率监控看板-发货查询==controller===入参===》》" + JsonUtils.toJson(fhtjQuery));
        if (null == fhtjQuery || StringUtils.isEmpty(fhtjQuery.getShgs())) {
            return "效率监控看板-发货场站为空";
        }
        List<FhdmxEntity> maps = drillService.fhtjQuery(fhtjQuery);
        logger.info("效率监控看板-发货查询返回条数==》" + maps.size());
//        return JsonUtils.toJson(maps);
        return null;
    }

    /**
     * @author: joel
     * @description: 车辆信息----到货
     * @params: @param null:
     * @return:
     * @time: 2020/12/3 16:09
     */
    @PostMapping("clxxdh")
    public String clxxdh(@RequestBody DhtjQuery dhtjQuery) {
//        logger.info("车辆信息--到货--controller--入参--》》" + JsonUtils.toJson(dhtjQuery));
        String xhgs = dhtjQuery.getXhgs();
        if (null == xhgs || StringUtils.equals("", xhgs)) {
            return "场站为空";
        }
        List<ArrivalInfo> maps = drillService.clxxdhQuery(dhtjQuery);
        logger.info("车辆信息--到货--controller--查询返回条数--》" + (null != maps ? maps.size() : 0));
//        return JsonUtils.toJson(maps);
        return null;
    }

    /**
     * @author: joel
     * @description: 根据开始时间和结束时间抽取数据
     * @params: @param null:
     * @return:
     * @time: 2021/1/4 15:30
     */
    @PostMapping("clxxdhExtract")
    public String clxxdhExtract(@RequestBody String str) {
//        logger.info("车辆信息--到货抽取--controller--clxxdhExtract--入参--》》" + JsonUtils.toJson(str));
//        Map<String, String> stringStringMap = JsonUtils.toMap(str, String.class, String.class);
        Map<String, String> stringStringMap = null;
        String start = stringStringMap.get("start");
        String end = stringStringMap.get("end");
        if (null == start || StringUtils.equals("", start)) {
            return "开始时间为空";
        }
        if (null == end || StringUtils.equals("", end)) {
            return "结束时间为空";
        }
        if (taskService.clxxdhExtract(start, end)) {
            return "true";
        } else {
            return "false";
        }
    }
}