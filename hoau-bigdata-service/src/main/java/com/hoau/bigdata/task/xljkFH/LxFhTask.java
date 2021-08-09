package com.hoau.bigdata.task.xljkFH;

import com.hoau.bigdata.config.HdfsConfig;
import com.hoau.bigdata.config.HostConfig;
import com.hoau.bigdata.entity.report.FhMxAndDhMxOut;
import com.hoau.bigdata.entity.tm.Label;
import com.hoau.bigdata.impl.HDFSServiceImpl;
import com.hoau.bigdata.task.label.LabelTask;
import com.hoau.bigdata.task.xljkFH.delivery.DeliveryFhMxTask;
import com.hoau.bigdata.task.xljkFH.downTransfer.DownTransferFhMxTask;
import com.hoau.bigdata.task.xljkFH.longDistance.LongDistanceFhMxTask;
import com.hoau.bigdata.task.xljkFH.transfer.TransferFhMxTask;
import com.hoau.bigdata.utils.ContentStringUtils;
import com.hoau.bigdata.utils.ShellTool;
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
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Future;

/**
 * @Description: 定时执行获取效率监控看板-发货数据写入HDFS功能
 * @Author: zhaowei
 * @Date: 2020/11/17
 * @Time: 14:15
 */
@JobHandler(value = "LxFhTask")
@Component
public class LxFhTask extends IJobHandler {

    Logger log = LoggerFactory.getLogger(getClass());

    @Autowired
    private HostConfig hostConfig;

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
        log.info("----获取效率监控看板-发货数据定时任务入参:" + param + "----");
        String[] strArr = null;
//        String logOutName = DateUtil.formatDate(new Date(), "dd-MM-yyyy");
        String logOutName = "2021-08-09";
        /**
         * 调用的MR参数说明：
         *       第一个参数：输出目标文件HDFS路径 如：hdfs://master:9000/report/statistics/dhmxTable
         *       第二个参数：输出目标文件类型 如：.json
         *       第三个参数：输出目标文件中产生的临时文件存放路径 如：hdfs://master:9000/report/dhmx/tmp
         *       第四个参数：计划发车时间需要用到的时间数据文件存放路径 如：hdfs://master:9000/report/vehicle/arrival/tmp/timeline.josn
         *       第五个参数：件扫描信息文件路径 如：hdfs://master:9000/report/visualization/dhLabel/dhLabel.json
         *       第六个参数：输入文件路径
         *       ... : 多个输入文件路径
         * 参数不能小于6个
         */
        String[] execParams = {hdfsConfig.baseClusterPath + hdfsConfig.fhMxDir,
                hdfsConfig.outNameType,
                hdfsConfig.baseClusterPath + hdfsConfig.fhmxTmpDir,
                hdfsConfig.baseClusterPath + hdfsConfig.vehicleArrivalDirtmp + hdfsConfig.vehicleArrivalTimeLine + hdfsConfig.outNameType,
                hdfsConfig.baseClusterPath + hdfsConfig.lxfhDir + hdfsConfig.lxfhOutName,
                ">> /usr/local/tool/mapreduce/lxfh_logs/" + logOutName + ".log 2>&1 &"
        };
        int labelScanType = 1;
        int labelSec = 60;
        //1分钟内所有标签扫描记录列表
        List<Label> labelList;
        //派送发货标签扫描记录列表
        List<Label> deliveryLabelList = new ArrayList<>();
        //下转移发货标签扫描记录列表
        List<Label> downTransferLabelList = new ArrayList<>();
        //长途发货标签扫描记录列表
        List<Label> longDistanceLabelList = new ArrayList<>();
        //中转发货标签扫描记录列表
        List<Label> transferLabelList = new ArrayList<>();
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
            boolean flag = ((null == labelList || labelList.isEmpty()));
            //不为空则去查询对应的发货信息
            if (!flag) {
                log.info("==============业务分组开始==============");
                bszGroup(labelList, deliveryLabelList, downTransferLabelList, longDistanceLabelList, transferLabelList);
                log.info("==============业务分组结束==============");

                CountDownLatch taskLatch1 = new CountDownLatch(4);

                //扫描记录反查派送发货
                Future<List<FhMxAndDhMxOut>> ps = ThreadPoolUtil.submit(new DeliveryFhMxTask(taskLatch1, deliveryLabelList));
                //扫描记录反查下转移发货
                Future<List<FhMxAndDhMxOut>> xzy = ThreadPoolUtil.submit(new DownTransferFhMxTask(taskLatch1, downTransferLabelList));
                //扫描记录反查长途发货
                Future<List<FhMxAndDhMxOut>> ct = ThreadPoolUtil.submit(new LongDistanceFhMxTask(taskLatch1, longDistanceLabelList));
                //扫描记录反查中转发货
                Future<List<FhMxAndDhMxOut>> zz = ThreadPoolUtil.submit(new TransferFhMxTask(taskLatch1, transferLabelList));

                taskLatch1.await();

                totalList.addAll(ps.get());
                totalList.addAll(xzy.get());
                totalList.addAll(ct.get());
                totalList.addAll(zz.get());

                log.info("---- 需要写入HDFS的效率监控看板-发货信息条数:<" + totalList.size() + ">");
                if (null != totalList && !totalList.isEmpty()) {
                    //写入hdfs
                    writeToHdfs(totalList);
                    //通过shell调用MapReduce脚本
                    callShell(execParams);
                }
            } else {
                log.warn("---- " + labelSec + " 秒内发货标签扫描没有最新数据不执行MR命令----");
            }
        } catch (Exception e) {
            log.error("获取效率监控看板-发货定时任务异常:" + e);
        }
        long diff = System.currentTimeMillis() - startTime;
        log.info("----获取效率监控看板-发货定时任务执行时间:<" + diff + ">毫秒----");
        return SUCCESS;
    }

    private void callShell(String[] execParams) {
        log.info("效率监控看板-发货调用远程MR执行命令开始");
        StringBuilder sb = new StringBuilder();
        sb.append(hostConfig.exeLxFhCmd).append(" ");
        for (String execParam : execParams) {
            sb.append(execParam).append(" ");
        }
        log.info("效率监控看板-发货调用远程MR命令:<" + sb.toString().trim() + ">");
        boolean bln = ShellTool.SSHCommand(hostConfig.host, hostConfig.userName, hostConfig.password, sb.toString().trim());
        log.info("效率监控看板-发货调用远程MR执行命令结果:" + bln);
    }

    private void writeToHdfs(List<FhMxAndDhMxOut> totalList) {
        hdfsService.writeToHdfs(hdfsConfig.baseClusterPath, hdfsConfig.lxfhDir, hdfsConfig.lxfhOutName,
                ContentStringUtils.getStringContent(totalList), true, true, false, false);
    }

    /**
     * 将标签扫描按类型分组
     *
     * @param labelList
     * @param deliveryLabelList     派送
     * @param downTransferLabelList 下转移
     * @param longDistanceLabelList 长途发货
     * @param transferLabelList     中转
     */
    private void bszGroup(List<Label> labelList, List<Label> deliveryLabelList, List<Label> downTransferLabelList, List<Label> longDistanceLabelList,
                          List<Label> transferLabelList) {
        for (Label label : labelList) {
            String loadID = label.getLOADPLANID();
            String delvID = label.getDELIVERPLANID();
            if (StringUtils.isNotBlank(delvID)) {
                deliveryLabelList.add(label);
            } else if (StringUtils.isNotBlank(loadID)) {
                if (loadID.startsWith("0")) {
                    longDistanceLabelList.add(label);
                    transferLabelList.add(label);
                } else if (loadID.startsWith("2")) {
                    downTransferLabelList.add(label);
                }
            }
        }
    }

}
