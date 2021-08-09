package com.hoau.bigdata.task.track;

import com.hoau.bigdata.config.HdfsConfig;
import com.hoau.bigdata.entity.track.OutWardBillEntity;
import com.hoau.bigdata.impl.HDFSServiceImpl;
import com.hoau.bigdata.mapper.report.TrackDao;
import com.hoau.bigdata.utils.ContentStringUtils;
import com.hoau.bigdata.utils.ManageSpringBeans;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @description: 外发单
 * @author: zxy
 * @create: 2020/12/6 14:36
 */
public class OutWardBillTask implements Callable<List<OutWardBillEntity>> {

    private static final Log log = LogFactory.getLog(OutWardBillTask.class);

    private HdfsConfig hdfsConfig = ManageSpringBeans.getBean(HdfsConfig.class);

    private TrackDao trackDao = ManageSpringBeans.getBean(TrackDao.class);

    private CountDownLatch countDownLatch;

    @Autowired
    HDFSServiceImpl hdfsService;

    private int sec;

    public OutWardBillTask(CountDownLatch countDownLatch, int sec) {
        this.countDownLatch = countDownLatch;
        this.sec = sec;
    }

    @Override
    public List<OutWardBillEntity> call() {
        log.info("============查询外发单明细并写入HDFS任务start=================");
        List<OutWardBillEntity> list = new ArrayList<>();
        try {
            list = trackDao.queryOutWardBill(sec);
            if (null == list || list.isEmpty()) {
                log.info("外发单结果为空");
            } else {
                log.info("外发单结果条数为：" + list.size());
            }
            hdfsService.writeToHdfs(hdfsConfig.baseClusterPath, hdfsConfig.outWardBill, hdfsConfig.outWardBillOutName,
                    ContentStringUtils.getStringContent(list), true, true, false,false);
        } catch (Exception e) {
            log.info("查询外发单明细并写入HDFS任务异常:" + e);
        } finally {
            countDownLatch.countDown();
        }
        log.info("============查询外发单明细写入HDFS任务end=================");
        return list;
    }
}