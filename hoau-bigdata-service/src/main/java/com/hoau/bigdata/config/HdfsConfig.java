package com.hoau.bigdata.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020/11/17
 * @Time: 14:21
 */
@Configuration
public class HdfsConfig {

    @Value("${base.dir}")
    public String hdfsPath;

    @Value("${base.cluster.path}")
    public String baseClusterPath;

    @Value("${hdfs.outNameType}")
    public String outNameType;

    @Value("${inTimeDhMx.dir}")
    public String inTimeDhMxDir;

    @Value("${inTimeDhMx.outName}")
    public String inTimeDhMxOutName;

    @Value("${outTimeDhMx.dir}")
    public String outTimeDhMxDir;

    @Value("${outTimeDhMx.outName}")
    public String outTimeDhMxOutName;

    @Value("${inTimeSZYDhMx.dir}")
    public String inTimeSZYDhMxDir;

    @Value("${inTimeSZYDhMx.outName}")
    public String inTimeSZYDhMxOutName;

    @Value("${outTimeSZYDhMx.dir}")
    public String outTimeSZYDhMxDir;

    @Value("${outTimeSZYDhMx.outName}")
    public String outTimeSZYDhMxOutName;

    @Value("${dh.label.dir}")
    public String dhLabelDir;

    @Value("${dh.label.outName}")
    public String dhLabelOutName;

    @Value("${fh.label.dir}")
    public String fhLabelDir;

    @Value("${fh.label.outName}")
    public String fhLabelOutName;

    @Value("${dhMx.dir}")
    public String dhMxDir;

    @Value("${dhmx.tmp.dir}")
    public String dhmxTmpDir;

    @Value("${inTimeDelivery.dir}")
    public String inTimeDeliveryDir;

    @Value("${inTimeDelivery.outName}")
    public String inTimeDeliveryOutName;

    @Value("${outTimeDelivery.dir}")
    public String outTimeDeliveryDir;

    @Value("${outTimeDelivery.outName}")
    public String outTimeDeliveryOutName;

    @Value("${inTimeDownTransfer.dir}")
    public String inTimeDownTransferDir;

    @Value("${inTimeDownTransfer.outName}")
    public String inTimeDownTransferOutName;

    @Value("${outTimeDownTransfer.dir}")
    public String outTimeDownTransferDir;

    @Value("${outTimeDownTransfer.outName}")
    public String outTimeDownTransferOutName;

    @Value("${inTimeLongDis.dir}")
    public String inTimeLongDisDir;

    @Value("${inTimeLongDis.outName}")
    public String inTimeLongDisOutName;

    @Value("${outTimeLongDis.dir}")
    public String outTimeLongDisDir;

    @Value("${outTimeLongDis.outName}")
    public String outTimeLongDisOutName;

    @Value("${inTimeTrans.dir}")
    public String inTimeTransDir;

    @Value("${inTimeTrans.outName}")
    public String inTimeTransOutName;

    @Value("${outTimeTrans.dir}")
    public String outTimeTransDir;

    @Value("${outTimeTrans.outName}")
    public String outTimeTransOutName;

    @Value("${fhMx.dir}")
    public String fhMxDir;

    @Value("${trans.dir}")
    public String transDir;

    @Value("${fhmx.tmp.dir}")
    public String fhmxTmpDir;

    @Value("${trans.tmp.dir}")
    public String transTmpDir;

    @Value("${vehicle.arrival.dir.tmp}")
    public String vehicleArrivalDirtmp;

    @Value("${vehicle.arrival.dir.out.tmp}")
    public String vehicleArrivalDirOutTmp;

    @Value("${vehicle.arrival.dir.info}")
    public String vehicleArrivalDirInfo;

    @Value("${vehicle.arrival.outName}")
    public String vehicleArrivalOutName;

    @Value("${vehicle.arrival.tileline}")
    public String vehicleArrivalTimeLine;

    @Value("${transInfo.dir}")
    public String transInfo;

    @Value("${transInfo.outName}")
    public String transInfoOutName;

    @Value("${outWardBill.dir}")
    public String outWardBill;

    @Value("${outWardBill.outName}")
    public String outWardBillOutName;

    @Value("${receiptRegistration.dir}")
    public String receiptRegistration;

    @Value("${receiptRegistration.outName}")
    public String receiptRegistrationOutName;

    @Value("${goodsTransfer.dir}")
    public String goodsTransfer;

    @Value("${goodsTransfer.outName}")
    public String goodsTransferOutName;

    @Value("${easyInstall.dir}")
    public String easyInstall;

    @Value("${easyInstall.outName}")
    public String easyInstallOutName;

    @Value("${lxfh.dir}")
    public String lxfhDir;

    @Value("${lxfh.outName}")
    public String lxfhOutName;

}
