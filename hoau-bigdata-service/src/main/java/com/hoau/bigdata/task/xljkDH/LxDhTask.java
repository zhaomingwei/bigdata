package com.hoau.bigdata.task.xljkDH;

import com.hoau.bigdata.config.HdfsConfig;
import com.hoau.bigdata.entity.report.FhMxAndDhMxOut;
import com.hoau.bigdata.entity.tm.Label;
import com.hoau.bigdata.impl.HDFSServiceImpl;
import com.hoau.bigdata.task.label.LabelTask;
import com.hoau.bigdata.task.xljkDH.longDistance.LongDistanceDhMxTask;
import com.hoau.bigdata.task.xljkDH.upTransfer.SzyDhMxTask;
import com.hoau.bigdata.utils.DateUtils;
import com.hoau.bigdata.utils.ThreadPoolUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * @Description: 定时执行获取效率监控看板-到货数据写入HDFS功能
 * @Author: zhaowei
 * @Date: 2020/11/4
 * @Time: 9:05
 */
@JobHandler(value = "LxDhTask")
@Component
public class LxDhTask extends IJobHandler {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private HdfsConfig hdfsConfig;

    @Autowired
    HDFSServiceImpl hdfsService;

    /**
     * 各参数，含义依次是：
     * 标签扫描记录类型，1-发车，2-到车；
     * 标签扫描记录是当前时间几秒内；
     *
     * @param param
     * @return
     */
    @Override
    public ReturnT<String> execute(String param) {
        long startTime = System.currentTimeMillis();
        log.info("----获取效率监控看板-到货数据定时任务入参:" + param + "----");
        String[] strArr = null;
        int labelScanType = 2;
        int labelSec = 60;
        //1分钟内所有标签扫描记录列表
        List<Label> labelList;
        //长途到货标签扫描记录列表
        List<Label> ctLabelList = new ArrayList<>();
        //上转移到货标签扫描记录列表
        List<Label> szyLabelList = new ArrayList<>();
        //输出到HDFS系统的list
        List<FhMxAndDhMxOut> totalList = new ArrayList<>();
        if (StringUtils.isNotBlank(param)) {
            strArr = param.split(",");
        }
        if (null != strArr && strArr.length == 2) {
            labelScanType = Integer.parseInt(strArr[0]);
            labelSec = Integer.parseInt(strArr[1]);
        }

        try {
            CountDownLatch taskLatch = new CountDownLatch(1);

            //1分钟内标签扫描记录
            Future<List<Label>> labelRes = ThreadPoolUtil.submit(new LabelTask(taskLatch, labelScanType, labelSec, false));

            taskLatch.await();
            labelList = labelRes.get();

            //件扫描信息列表为空
            boolean flag = (null == labelList || labelList.isEmpty());
            //不为空则去查询对应的发货信息
            if (!flag) {
                log.info("==============业务分组开始==============");
                //业务分组
                bszGroup(labelList, ctLabelList, szyLabelList);
                log.info("==============业务分组结束==============");

                CountDownLatch taskLatch1 = new CountDownLatch(2);

                //扫描记录反查长途到货
                Future<List<FhMxAndDhMxOut>> ct = ThreadPoolUtil.submit(new LongDistanceDhMxTask(taskLatch1, ctLabelList));
                //扫描记录反查上转移到货
                Future<List<FhMxAndDhMxOut>> szy = ThreadPoolUtil.submit(new SzyDhMxTask(taskLatch1, szyLabelList));

                taskLatch1.await();

                totalList.addAll(ct.get());
                totalList.addAll(szy.get());

                log.info("---- 需要写入HDFS的效率监控看板-到货信息条数:<" + totalList.size() + ">");
                if (null != totalList && !totalList.isEmpty()) {
                    //写入hdfs
                    writeToHdfs(totalList);
                }
            } else {
                log.warn("---- " + labelSec + " 秒内到货标签扫描没有最新数据----");
            }
        } catch (Exception e) {
            log.error("获取效率监控看板-到货定时任务异常:" + e);
        }
        long diff = System.currentTimeMillis() - startTime;
        log.info("----获取效率监控看板-到货定时任务执行时间:<" + diff + ">毫秒----");
        return SUCCESS;
    }

    private void writeToHdfs(List<FhMxAndDhMxOut> totalList) {
        String dateStr = DateUtils.formatDateToString(new Date(), "yyyyMMdd");
        hdfsService.writeToHdfs(hdfsConfig.baseClusterPath, hdfsConfig.dhMxDir, "/" + dateStr + "/" + dateStr + hdfsConfig.outNameType,
                getStringContent(totalList), true, false, true, false);
    }

    public String getStringContent(List<FhMxAndDhMxOut> list) {
        StringBuilder sb = new StringBuilder();
        for (FhMxAndDhMxOut t : list) {
            sb.append(t.getStringContent()).append("\n");
        }
        return sb.toString();
    }

    /**
     * 将标签扫描按类型分组
     *
     * @param labelList
     * @param ctLabelList  长途
     * @param szyLabelList 上转移
     */
    private void bszGroup(List<Label> labelList, List<Label> ctLabelList, List<Label> szyLabelList) {
        for (Label label : labelList) {
            String unloadID = label.getUNLDPLANID();
            if (StringUtils.isNotBlank(unloadID)) {
                if (unloadID.startsWith("0")) {
                    ctLabelList.add(label);
                } else if (unloadID.startsWith("1")) {
                    szyLabelList.add(label);
                }
            }
        }
    }

}
