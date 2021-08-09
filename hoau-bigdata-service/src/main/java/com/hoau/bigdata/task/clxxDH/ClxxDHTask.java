package com.hoau.bigdata.task.clxxDH;

import com.hoau.bigdata.config.HdfsConfig;
import com.hoau.bigdata.config.HostConfig;
import com.hoau.bigdata.entity.report.ArrivalInfoTmp;
import com.hoau.bigdata.impl.HDFSServiceImpl;
import com.hoau.bigdata.mapper.report.VHInfoFHMapper;
import com.hoau.bigdata.utils.ContentStringUtils;
import com.hoau.bigdata.utils.ShellTool;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author ：Joel.Li
 * @date ：2020/11/25 10:30
 * @description： 车辆信息到货MR
 * @version: $version
 */
@JobHandler(value = "ClxxDHTask")
@Component
public class ClxxDHTask extends IJobHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HostConfig hostConfig;

    @Autowired
    private HdfsConfig hdfsConfig;

    @Autowired
    HDFSServiceImpl hdfsService;

    @Autowired
    private VHInfoFHMapper vhInfoFHMapper;


    @Override
    public ReturnT<String> execute(String param) throws Exception {
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-YYYY");
        SimpleDateFormat sfm = new SimpleDateFormat("YYYYMMddHHmm");
        String logOutName = sfd.format(new Date());
        String sfmStr = sfm.format(new Date());
        String[] execParams = {hdfsConfig.baseClusterPath + hdfsConfig.vehicleArrivalDirInfo + hdfsConfig.vehicleArrivalOutName,
                hdfsConfig.outNameType,
                hdfsConfig.baseClusterPath + hdfsConfig.vehicleArrivalDirOutTmp,
                hdfsConfig.baseClusterPath + hdfsConfig.vehicleArrivalDirtmp + hdfsConfig.vehicleArrivalOutName + sfmStr + hdfsConfig.outNameType,
                hdfsConfig.baseClusterPath + hdfsConfig.vehicleArrivalDirtmp + hdfsConfig.vehicleArrivalTimeLine + hdfsConfig.outNameType,
                hdfsConfig.vehicleArrivalDirtmp + hdfsConfig.vehicleArrivalOutName + sfmStr + hdfsConfig.outNameType, ">> /usr/local/tool/mapreduce/clxxdh_logs/" + logOutName + ".log 2>&1 &"};
        try {
            //数据抽取
            int i = dhxxWrite(sfmStr);
            if (i > 0) {
                logger.info("车辆信息看板-到货调用远程MR执行命令开始");
                StringBuilder sb = new StringBuilder();
                sb.append(hostConfig.exeClxxDHCmd).append(" ");
                for (String execParam : execParams) {
                    sb.append(execParam).append(" ");
                }
                logger.info("车辆信息看板-到货调用远程MR命令:<" + sb.toString().trim() + ">");
                boolean bln = ShellTool.SSHCommand(hostConfig.host, hostConfig.userName, hostConfig.password, sb.toString().trim());
                logger.info("车辆信息看板-到货调用远程MR执行命令结果:" + bln);
            } else {
                logger.info("车辆信息看板-到货调用远程MR执行命令--无数据聚合");
            }
            return SUCCESS;
        } catch (Exception e) {
            logger.info("车辆信息看板--到货---数据抽取异常===》" + e.getMessage());
            return FAIL;
        }
    }

    /**
     * @author: joel
     * @description: 到货信息写入HDFS
     * @params: @param null:
     * @return:
     * @time: 2020/11/25 11:38
     */
    public int dhxxWrite(String sfmStr) {
        logger.info("车辆信息看板--到货---开始");
        int size = 0;
        List<ArrivalInfoTmp> cldh = new ArrayList<ArrivalInfoTmp>();

        //上转移到货
        List<ArrivalInfoTmp> szydh = szydh();
        //长途到货
        List<ArrivalInfoTmp> ctdh = ctdh();
        //中转到货
        List<ArrivalInfoTmp> zzdh = zzdh();

        cldh.addAll(szydh);
        cldh.addAll(ctdh);
        cldh.addAll(zzdh);
        size = cldh.size();
        logger.info("车辆信息看板--到货---总数据量===》" + cldh.size());
        if (size > 0) {
            String stringContent = ContentStringUtils.getStringContent(cldh);
            try {
                logger.info("车辆信息看板--到货---开始写入hdfs");
                hdfsService.writeToHdfs(hdfsConfig.baseClusterPath, hdfsConfig.vehicleArrivalDirtmp, hdfsConfig.vehicleArrivalOutName + sfmStr + hdfsConfig.outNameType, stringContent, true, true, false, false);
                logger.info("车辆信息看板--到货--写入hdfs完成");
            } catch (Exception e) {
                logger.info("车辆信息看板--到货--查询数据==写入HDFS异常++》" + e);
            }
        }
        return size;
    }

    /**
     * @author: joel
     * @description: 上转移到货
     * @params: @param null:
     * @return:
     * @time: 2020/11/25 13:15
     */
    private List<ArrivalInfoTmp> szydh() {
        logger.info("车辆信息看板--到货--查询数据--上转移");
        List<ArrivalInfoTmp> arrivalInfoTmpList = vhInfoFHMapper.getSZYDH();
//        logger.info("上转移==抽取数据==>" + JsonUtil.toJson(arrivalInfoTmpList));
        logger.info("车辆信息看板--到货--查询数据--上转移--获取到到数据条数==》" + arrivalInfoTmpList.size());
        return arrivalInfoTmpList;
    }

    /**
     * @author: joel
     * @description: 长途到货
     * @params: @param null:
     * @return:
     * @time: 2020/11/27 15:49
     */
    private List<ArrivalInfoTmp> ctdh() {
        logger.info("车辆信息看板--到货--查询数据--长途到货");
        List<ArrivalInfoTmp> arrivalInfoTmpList = vhInfoFHMapper.getCTDH();
//        logger.info("长途到货==抽取数据==>" + JsonUtil.toJson(arrivalInfoTmpList));
        logger.info("车辆信息看板--到货--查询数据--长途到货--获取到到数据条数==》" + arrivalInfoTmpList.size());
        return arrivalInfoTmpList;
    }

    /**
     * @author: joel
     * @description: 中转到货
     * @params: @param null:
     * @return:
     * @time: 2020/11/27 15:54
     */
    private List<ArrivalInfoTmp> zzdh() {
        logger.info("车辆信息看板--到货--查询数据--中转到货");
        List<ArrivalInfoTmp> arrivalInfoTmpList = vhInfoFHMapper.getZZDH();
//        logger.info("中转到货==抽取数据==>" + JsonUtil.toJson(arrivalInfoTmpList));
        logger.info("车辆信息看板--到货--查询数据--中转到货--获取到到数据条数==》" + arrivalInfoTmpList.size());
        return arrivalInfoTmpList;
    }
}
