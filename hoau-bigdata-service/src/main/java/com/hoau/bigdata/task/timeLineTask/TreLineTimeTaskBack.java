package com.hoau.bigdata.task.timeLineTask;

import com.hoau.bigdata.config.HdfsConfig;
import com.hoau.bigdata.entity.report.TimeLineEntity;
import com.hoau.bigdata.impl.HDFSServiceImpl;
import com.hoau.bigdata.mapper.report.VHInfoFHMapper;
import com.hoau.bigdata.utils.ContentStringUtils;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author ：Joel.Li
 * @date ：2020/11/25 16:06
 * @description： 线路时间抽取
 * @version: $version
 */
@JobHandler(value = "TreLineTimeTaskBack")
@Component
public class TreLineTimeTaskBack extends IJobHandler {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private HdfsConfig hdfsConfig;

    @Autowired
    private VHInfoFHMapper vhInfoFHMapper;

    @Autowired
    HDFSServiceImpl hdfsService;

    @Override
    public ReturnT<String> execute(String param) throws Exception {
        try {
            timeLineWriteHdfs();
            return SUCCESS;
        } catch (Exception e) {
            logger.info("timeLineWrite==时间线路抽取异常--》" + e.getMessage());
            return FAIL;
        }
    }


    private void timeLineWriteHdfs() {
        logger.info("timeLineWrite--抽取线路时间数据");
        List<TimeLineEntity> lineTime = findLineTime();
        logger.info("-时间线路总数据量===》" + lineTime.size());
//        String stringContent = JsonUtils.toJson(lineTime);
        String stringContent = ContentStringUtils.getStringContent(lineTime);
        try {
            logger.info("timeLineWrite==时间线路--开始写入hdfs");
//            HdfsFileUtils.writeToHdfs(hdfsConfig.hdfsPath, hdfsConfig.vehicleArrivalDirtmp, hdfsConfig.vehicleArrivalTimeLine + hdfsConfig.outNameType, stringContent, true, true, false);
            hdfsService.writeToHdfs(hdfsConfig.baseClusterPath, hdfsConfig.vehicleArrivalDirtmp, hdfsConfig.vehicleArrivalTimeLine + hdfsConfig.outNameType, stringContent, true, true, false, false);

            logger.info("timeLineWrite==时间线路--写入hdfs完成");
        } catch (Exception e) {
            logger.info("timeLineWrite==时间线路==写入HDFS异常++》");
            e.printStackTrace();

        }

    }

    private List<TimeLineEntity> findLineTime() {
        logger.info("TreLineTimeTaskBack--抽取线路时间数据");
        List<TimeLineEntity> allList = new ArrayList<TimeLineEntity>();
        logger.info("抽取线路时间数据--上转移");
        List<TimeLineEntity> szyLineTime = vhInfoFHMapper.szyLineTime();
        logger.info("抽取线路时间数据--长途");
        List<TimeLineEntity> ctLineTime = vhInfoFHMapper.ctLineTime();

        allList.addAll(szyLineTime);
        allList.addAll(ctLineTime);

        logger.info("抽取线路时间数据----获取到到数据条数==》" + allList.size());
        return allList;
    }
}
