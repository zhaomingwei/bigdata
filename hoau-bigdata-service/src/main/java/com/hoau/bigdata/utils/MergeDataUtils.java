package com.hoau.bigdata.utils;

import com.hoau.bigdata.entity.report.FhMxAndDhMxOut;
import com.hoau.bigdata.entity.tm.Label;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import java.util.List;

/**
 * @Description: TODO
 * @Author: zhaowei
 * @Date: 2020.12.25
 * @Time: 10:26
 */
public class MergeDataUtils {

    private static final Log log = LogFactory.getLog(MergeDataUtils.class);

    /**
     * 合并数据
     * @param reqList 发车编号下所有运单号总重量、总体积、总件数数据
     * @param list 按标签扫描记录查询的发到货信息
     * @param labelList 标签扫描记录
     * @param resList 返回列表
     * @param message 业务信息
     */
    public static void getLastData(List<FhMxAndDhMxOut> reqList, List<FhMxAndDhMxOut> list,
                                   List<Label> labelList, List<FhMxAndDhMxOut> resList, String message) {
        FhMxAndDhMxOut f;
        Label label;
        if (null == reqList || reqList.isEmpty()) {
            log.info(message + "按发车编号分组统计该发车编号下所有运单的总重量、总体积、总件数结果为空");
        } else {
            //将总重量、总体积、总件数赋值给之前的查询结果
            for (int i = 0; i < reqList.size(); i++) {
                for (int j = 0; j < list.size(); j++) {
                    if (reqList.get(i).getFCBH().equals(list.get(j).getFCBH())
                            && reqList.get(i).getTJGSCODE().equals(list.get(j).getTJGSCODE())) {
                        list.get(j).setTZL(reqList.get(i).getTZL());
                        list.get(j).setTJS(reqList.get(i).getTJS());
                        list.get(j).setTTJ(reqList.get(i).getTTJ());
                    }
                }
            }
            for (int i = 0; i < list.size(); i++) {
                f = list.get(i);
                for (int j = 0; j < labelList.size(); j++) {
                    label = labelList.get(j);
                    if (f.getZXDH().equals(label.getPLANID()) &&
                            f.getYDBH().equals(label.getCSGNID())) {
                        FhMxAndDhMxOut f1 = new FhMxAndDhMxOut();
                        BeanUtils.copyProperties(f, f1);
                        f1.setLBLID(label.getLBLID());
                        f1.setRCVMSGDATE(label.getRCVMSGDATE());
                        f1.setSCANDATE(label.getSCANDATE());
                        resList.add(f1);
                    }
                }
            }
        }
    }

}
