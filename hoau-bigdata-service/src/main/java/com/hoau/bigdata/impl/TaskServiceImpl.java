package com.hoau.bigdata.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.hoau.bigdata.config.HdfsConfig;
import com.hoau.bigdata.entity.report.ArrivalInfoTmp;
import com.hoau.bigdata.mapper.report.VHInfoFHMapper;
import com.hoau.bigdata.service.TaskService;
import com.hoau.bigdata.utils.ContentStringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：Joel.Li
 * @date ：2021/1/4 15:18
 * @description： description
 * @version: $version
 */
@Service
public class TaskServiceImpl implements TaskService {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HdfsConfig hdfsConfig;

    @Autowired
    private VHInfoFHMapper vhInfoFHMapper;

    @Autowired
    HDFSServiceImpl hdfsService;

    @Override
    public boolean clxxdhExtract(String start, String end) {
        logger.info("TaskServiceImpl--clxxdh--车辆信息看板--到货---开始");

        SimpleDateFormat sfm = new SimpleDateFormat("YYYYMMddHHmmss");
        String sfmStr = sfm.format(new Date());

        int size = 0;
        List<ArrivalInfoTmp> cldh = new ArrayList<ArrivalInfoTmp>();

        //上转移到货
        List<ArrivalInfoTmp> szydh = szydh(start, end);
        //长途到货
        List<ArrivalInfoTmp> ctdh = ctdh(start, end);
        //中转到货
        List<ArrivalInfoTmp> zzdh = zzdh(start, end);

        cldh.addAll(szydh);
        cldh.addAll(ctdh);
        cldh.addAll(zzdh);
        size = cldh.size();
        logger.info("TaskServiceImpl--clxxdh--车辆信息看板--到货---总数据量===》" + cldh.size());
        if (size > 0) {
            String stringContent = ContentStringUtils.getStringContent(cldh);
            try {
                logger.info("TaskServiceImpl--clxxdh--车辆信息看板--到货---写入名称==》" + sfmStr + ".json");
                logger.info("TaskServiceImpl--clxxdh--车辆信息看板--到货---开始写入hdfs");
                hdfsService.writeToHdfs(hdfsConfig.baseClusterPath, hdfsConfig.vehicleArrivalDirtmp, hdfsConfig.vehicleArrivalOutName + sfmStr + hdfsConfig.outNameType, stringContent, true, true, false, false);
                logger.info("TaskServiceImpl--clxxdh--车辆信息看板--到货--写入hdfs完成");
                return true;
            } catch (Exception e) {
                logger.info("TaskServiceImpl--clxxdh--车辆信息看板--到货--查询数据==写入HDFS异常++》" + e);
                return false;
            }
        } else {
            logger.info("TaskServiceImpl--clxxdh--车辆信息看板--到货--抽取数据为 0");
            return true;
        }
    }

    /**
     * @author: joel
     * @description: 上转移到货
     * @params: @param null:
     * @return:
     * @time: 2020/11/25 13:15
     */
    private List<ArrivalInfoTmp> szydh(String start, String end) {
        logger.info("TaskServiceImpl--szydh--车辆信息看板--到货--查询数据--上转移");
        List<ArrivalInfoTmp> arrivalInfoTmpList = vhInfoFHMapper.getSZYDHBack(start, end);
        logger.info("TaskServiceImpl--szydh--车辆信息看板--到货--查询数据--上转移--获取到到数据条数==》" + arrivalInfoTmpList.size());
        return arrivalInfoTmpList;
    }

    /**
     * @author: joel
     * @description: 长途到货
     * @params: @param null:
     * @return:
     * @time: 2020/11/27 15:49
     */
    private List<ArrivalInfoTmp> ctdh(String start, String end) {
        logger.info("TaskServiceImpl--ctdh--车辆信息看板--到货--查询数据--长途到货");
        List<ArrivalInfoTmp> arrivalInfoTmpList = vhInfoFHMapper.getCTDHBack(start, end);
        logger.info("TaskServiceImpl--ctdh--车辆信息看板--到货--查询数据--长途到货--获取到到数据条数==》" + arrivalInfoTmpList.size());
        return arrivalInfoTmpList;
    }

    /**
     * @author: joel
     * @description: 中转到货
     * @params: @param null:
     * @return:
     * @time: 2020/11/27 15:54
     */
    private List<ArrivalInfoTmp> zzdh(String start, String end) {
        logger.info("TaskServiceImpl--zzdh--车辆信息看板--到货--查询数据--中转到货");
        List<ArrivalInfoTmp> arrivalInfoTmpList = vhInfoFHMapper.getZZDHBack(start, end);
        logger.info("TaskServiceImpl--zzdh--车辆信息看板--到货--查询数据--中转到货--获取到到数据条数==》" + arrivalInfoTmpList.size());
        return arrivalInfoTmpList;
    }

}
