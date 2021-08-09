package com.hoau.bigdata.task.track;

import com.hoau.bigdata.config.HdfsConfig;
import com.hoau.bigdata.config.HostConfig;
import com.hoau.bigdata.entity.track.EasyInstallEntity;
import com.hoau.bigdata.entity.track.GoodsTransferEntity;
import com.hoau.bigdata.entity.track.OutWardBillEntity;
import com.hoau.bigdata.entity.track.ReceiptRegistrationEntity;
import com.hoau.bigdata.impl.HDFSServiceImpl;
import com.hoau.bigdata.mapper.report.TrackDao;
import com.hoau.bigdata.utils.ContentStringUtils;
import com.hoau.bigdata.utils.HdfsFileUtils;
import com.hoau.bigdata.utils.ShellTool;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.IJobHandler;
import com.xxl.job.core.handler.annotation.JobHandler;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @description: 定时执行获取轨迹记录表、扫描写入HDFS功能
 * @author: zxy
 * @create: 2020/12/6 10:58
 */
@JobHandler(value = "TrackRecordTableTask")
@Component
public class TrackRecordTableTask extends IJobHandler {

    private static final Log log = LogFactory.getLog(TrackRecordTableTask.class);

    @Autowired
    private HostConfig hostConfig;

    @Autowired
    private HdfsConfig hdfsConfig;

    @Autowired
    HDFSServiceImpl hdfsService;

    @Autowired
    private TrackDao trackDao;

    /**
     * 个参数，含义依次是：
     * 当前时间几秒内：准备装车、装车完成状态发货明细的数据；
     * 标签扫描记录类型，1-发车，2-到车；
     * 标签扫描记录是当前时间几秒内；
     *
     * @param param
     * @return
     */
    @Override
    public ReturnT<String> execute(String param) {
        log.info("----执行获取轨迹记录定时任务入参pp5:" + param + "----");
        SimpleDateFormat sfd = new SimpleDateFormat("dd-MM-YYYY");
        String logOutName = sfd.format(new Date());
        String[] strArr = null;
        /**
         * 调用的MR参数说明：
         *       第一个参数：输出目标文件HDFS路径 如：hdfs://master:9000/report/statistics/dhmxTable
         *       第二个参数：输出目标文件类型 如：.txt
         *       第三个参数：输出目标文件中产生的临时文件存放路径 如：hdfs://master:9000/report/dhmx/tmp
         *       第四个参数：计划发车时间需要用到的时间数据文件存放路径 如：hdfs://master:9000/report/vehicle/arrival/tmp/timeline.josn
         *       第五个参数：件扫描信息文件路径 如：hdfs://master:9000/report/visualization/dhLabel/dhLabel.txt
         *       第六个参数：输入文件路径
         *       ... : 多个输入文件路径
         * 参数不能小于6个
         */
        String[] execParams = {hdfsConfig.baseClusterPath + hdfsConfig.transDir,
                hdfsConfig.outNameType,
                hdfsConfig.baseClusterPath + hdfsConfig.transTmpDir,
                hdfsConfig.baseClusterPath + hdfsConfig.goodsTransfer + hdfsConfig.goodsTransferOutName,
                hdfsConfig.baseClusterPath + hdfsConfig.easyInstall + hdfsConfig.easyInstallOutName,
                hdfsConfig.baseClusterPath + hdfsConfig.receiptRegistration + hdfsConfig.receiptRegistrationOutName,
                hdfsConfig.baseClusterPath + hdfsConfig.outWardBill + hdfsConfig.outWardBillOutName,
                ">> /usr/local/tool/mapreduce/trackRecord/" + logOutName + ".log 2>&1 &"};
        int mxSec = 60;
        if (StringUtils.isNotBlank(param)) {
            strArr = param.split(",");
        }
        if (null != strArr && strArr.length == 1) {
            mxSec = Integer.parseInt(strArr[0]);
        }

        try {
            log.info("----kaishi:" + param + "----");
            List<ReceiptRegistrationEntity> receiptRegistrationList = queryReceiptRegistration(mxSec);
            log.info("----jeishu:" + receiptRegistrationList.size() + "");
            List<OutWardBillEntity> outWardBillList = queryOutWardBill(mxSec);
            List<EasyInstallEntity> easyInstallEntityList = queryEasyInstall(mxSec);

            List<GoodsTransferEntity> goodsTransferEntityList = queryGoodsTransfer(mxSec);
            /*ExecutorService executor = Executors.newFixedThreadPool(4);
            CountDownLatch taskLatch = new CountDownLatch(4);
            Callable<List<ReceiptRegistrationEntity>> receiptRegistrationTask = new ReceiptRegistrationTask(taskLatch, mxSec);

            Callable<List<OutWardBillEntity>> outWardBillTask = new OutWardBillTask(taskLatch, mxSec);
            Callable<List<EasyInstallEntity>> easyInstallTask = new EasyInstallTask(taskLatch, mxSec);
            Callable<List<GoodsTransferEntity>> goodsTransferTask = new GoodsTransferTask(taskLatch, mxSec);
            Future<List<ReceiptRegistrationEntity>> receiptList = executor.submit(receiptRegistrationTask);

            Future<List<OutWardBillEntity>> outList = executor.submit(outWardBillTask);
            Future<List<EasyInstallEntity>> installList = executor.submit(easyInstallTask);
            Future<List<GoodsTransferEntity>> transferList = executor.submit(goodsTransferTask);

            taskLatch.await();
            receiptRegistrationList = receiptList.get();
            outWardBillList = outList.get();
            easyInstallEntityList = installList.get();
            goodsTransferEntityList = transferList.get();*/
            //件扫描信息抽取列表为空且其他几种情况下一分钟及内数据都为空则不执行MR
            boolean flag = ((null == receiptRegistrationList || receiptRegistrationList.isEmpty())
                    && (null == outWardBillList || outWardBillList.isEmpty())
                    && (null == easyInstallEntityList || easyInstallEntityList.isEmpty())
                    && (null == goodsTransferEntityList || goodsTransferEntityList.isEmpty()));
            if (flag) {
                log.warn("---- " + mxSec + " 秒内最新数据不执行MR命令----");
            } else {
                //hdfs://master:9000/track/statistics/trackTable .json hdfs://master:9000/track/mx hdfs://master:9000/track/visualization/easyInstall/easyInstall.json hdfs://master:9000/track/visualization/goodsTransfer/goodsTransfer.json hdfs://master:9000/track/visualization/outWardBill/outWardBill.json hdfs://master:9000/track/visualization/receiptRegistration/receiptRegistration.json hdfs://master:9000/track/visualization/transInfo/transInfo.json
                log.info("调用远程MR执行命令获取轨迹记录开始");
                //RemoteShellTool tool = new RemoteShellTool(hostConfig.host, hostConfig.userName, hostConfig.password, hostConfig.charset);
                StringBuilder sb = new StringBuilder();
                sb.append(hostConfig.exeTrackRecordCmd).append(" ");
                for (String execParam : execParams) {
                    sb.append(execParam).append(" ");
                }
                log.info("调用远程MR命令获取轨迹记录:<" + sb.toString().trim() + ">");
                boolean exec = ShellTool.SSHCommand(hostConfig.host, hostConfig.userName, hostConfig.password, sb.toString().trim());
                log.info("调用远程MR执行命令结果获取轨迹记录:" + exec + ">");
            }
        } catch (Exception e) {
            log.error("执行获取轨迹记录定时任务异常:" + e);
        }
        return SUCCESS;
    }

    private List<GoodsTransferEntity> queryGoodsTransfer(int mxSec) {
        log.info("============查询转库记录明细并写入HDFS任务start=================");
        List<GoodsTransferEntity> list = new ArrayList<>();
        try {
            list = trackDao.queryGoodsTransfer(mxSec);
            if (null == list || list.isEmpty()) {
                log.info("转库记录结果为空");
            } else {
                log.info("转库记录结果条数为：" + list.size());
                log.info("转库记录信息"+ContentStringUtils.getStringContent(list));
            }
            HdfsFileUtils.writeToHdfs(hdfsConfig.hdfsPath, hdfsConfig.goodsTransfer, hdfsConfig.goodsTransferOutName,
                    ContentStringUtils.getStringContent(list), true, true, false,false);
        } catch (Exception e) {
            log.info("查询转库记录明细并写入HDFS任务异常:" + e);
        }
        log.info("============查询转库记录明细写入HDFS任务end=================");
        return list;
    }

    private List<EasyInstallEntity> queryEasyInstall(int mxSec) {
        log.info("============查询易安装明细并写入HDFS任务start=================");
        List<EasyInstallEntity> list = new ArrayList<>();
        try {
            list = trackDao.queryEasyInstall(mxSec);
            if (null == list || list.isEmpty()) {
                log.info("易安装结果为空");
            } else {
                log.info("易安装结果条数为：" + list.size());
                log.info("易安装记录信息"+ContentStringUtils.getStringContent(list));
            }
            HdfsFileUtils.writeToHdfs(hdfsConfig.hdfsPath, hdfsConfig.easyInstall, hdfsConfig.easyInstallOutName,
                    ContentStringUtils.getStringContent(list), true, true, false,false);
        } catch (Exception e) {
            log.info("查询易安装明细并写入HDFS任务异常:" + e);
        }
        log.info("============查询易安装明细写入HDFS任务end=================");
        return list;
    }

    private List<ReceiptRegistrationEntity> queryReceiptRegistration(int mxSec) {
        log.info("============查询回单登记明细并写入HDFS任务start=================");
        List<ReceiptRegistrationEntity> list = new ArrayList<>();
        try {
            list = trackDao.queryReceiptRegistration(mxSec);
            if (null == list || list.isEmpty()) {
                log.info("回单登记结果为空");
            } else {
                log.info("回单登记结果条数为：" + list.size());
                log.info("回单登记记录信息"+ContentStringUtils.getStringContent(list));
            }
            HdfsFileUtils.writeToHdfs(hdfsConfig.hdfsPath, hdfsConfig.receiptRegistration, hdfsConfig.receiptRegistrationOutName,
                    ContentStringUtils.getStringContent(list), true, true, false,false);
        } catch (Exception e) {
            log.info("查询回单登记明细并写入HDFS任务异常:" + e);
        }
        log.info("============查询回单登记明细写入HDFS任务end=================");
        return list;
    }

    public List<OutWardBillEntity> queryOutWardBill(int sec){
        log.info("============查询外发单明细并写入HDFS任务start=================");
        List<OutWardBillEntity> list = new ArrayList<>();
        try {
            list = trackDao.queryOutWardBill(sec);
            if (null == list || list.isEmpty()) {
                log.info("外发单结果为空");
            } else {
                log.info("外发单结果条数为：" + list.size());
                log.info("外发单记录信息"+ContentStringUtils.getStringContent(list));
            }
            HdfsFileUtils.writeToHdfs(hdfsConfig.hdfsPath, hdfsConfig.outWardBill, hdfsConfig.outWardBillOutName,
                    ContentStringUtils.getStringContent(list), true, true, false,false);
        } catch (Exception e) {
            log.info("查询外发单明细并写入HDFS任务异常:" + e);
        } finally {
            System.out.println("结束");
        }
        log.info("============查询外发单明细写入HDFS任务end=================");
        return list;
    }
}
