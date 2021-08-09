package com.hoau.bigdata.task.label;

import com.hoau.bigdata.config.HdfsConfig;
import com.hoau.bigdata.entity.tm.Label;
import com.hoau.bigdata.impl.HDFSServiceImpl;
import com.hoau.bigdata.mapper.tm.LabelDao;
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
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020/11/17
 * @Time: 16:58
 */
public class LabelTask implements Callable<List<Label>> {

    @Autowired
    HDFSServiceImpl hdfsService;

    private static final Log log = LogFactory.getLog(LabelTask.class);

    private HdfsConfig hdfsConfig = ManageSpringBeans.getBean(HdfsConfig.class);

    private LabelDao labelDao = ManageSpringBeans.getBean(LabelDao.class);

    private CountDownLatch countDownLatch;

    private int scanType;

    private int sec;

    private boolean writeHdfsFlag;

    public LabelTask(CountDownLatch countDownLatch, int scanType, int sec, boolean writeHdfsFlag) {
        this.countDownLatch = countDownLatch;
        this.scanType = scanType;
        this.sec = sec;
        this.writeHdfsFlag = writeHdfsFlag;
    }

    @Override
    public List<Label> call() {
        log.info("============查询扫描类型(1-发车,2-到车): " + scanType + " 且在 " + sec + " 秒及之内标签扫描信息明细写入HDFS任务start=================");
        List<Label> list = new ArrayList<>();
        try {
            list = labelDao.queryLabelList(scanType, sec);
            if (null == list || list.isEmpty()) {
                log.info("查询 " + sec + " 秒及之内标签扫描明细查询结果为空");
            } else {
                log.info("查询 " + sec + " 秒及之内标签扫描明细查询结果条数为：" + list.size());
            }
            if(writeHdfsFlag){
                //到车
                if(2 == scanType){
                    hdfsService.writeToHdfs(hdfsConfig.baseClusterPath, hdfsConfig.dhLabelDir, hdfsConfig.dhLabelOutName,
                            ContentStringUtils.getStringContent(list), true, true, false,false);
                } else if(1 == scanType) {//发车
                    hdfsService.writeToHdfs(hdfsConfig.baseClusterPath, hdfsConfig.fhLabelDir, hdfsConfig.fhLabelOutName,
                            ContentStringUtils.getStringContent(list), true, true, false,false);
                }
            }
        } catch (Exception e) {
            log.error("查询 " + sec + " 秒及之内标签扫描信息明细写入HDFS任务异常:" + e);
        } finally {
            countDownLatch.countDown();
        }
        log.info("============查询扫描类型(1-发车,2-到车): " + scanType + " 且在 " + sec + " 秒及之内标签扫描信息明细写入HDFS任务end=================");
        return list;
    }

}
