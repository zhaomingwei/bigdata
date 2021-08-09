package com.hoau.bigdata.task.xljkFH.delivery;

import com.hoau.bigdata.entity.report.FhMxAndDhMxOut;
import com.hoau.bigdata.entity.tm.Label;
import com.hoau.bigdata.mapper.report.FhMxDao;
import com.hoau.bigdata.utils.FilterUtils;
import com.hoau.bigdata.utils.ManageSpringBeans;
import com.hoau.bigdata.utils.MergeDataUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * @Description: 派送明细
 * @Author: zhaowei
 * @Date: 2020/11/17
 * @Time: 17:18
 */
public class DeliveryFhMxTask implements Callable<List<FhMxAndDhMxOut>> {

    Logger log = LoggerFactory.getLogger(getClass());

    private FhMxDao fhMxDao = ManageSpringBeans.getBean(FhMxDao.class);

    private List<Label> deliveryLabelList;

    private CountDownLatch countDownLatch;

    public DeliveryFhMxTask(CountDownLatch countDownLatch, List<Label> deliveryLabelList) {
        this.countDownLatch = countDownLatch;
        this.deliveryLabelList = deliveryLabelList;
    }

    @Override
    public List<FhMxAndDhMxOut> call() {
        log.info("============查询派送发货任务start=================");
        List<FhMxAndDhMxOut> list;
        List<FhMxAndDhMxOut> resList = new ArrayList<>();
        try {
            if (null == deliveryLabelList || deliveryLabelList.isEmpty()) {
                log.info("派送发货明细查询入参为空");
                return resList;
            }
            log.info("派送发货明细查询入参条数:" + deliveryLabelList.size());
            list = fhMxDao.queryLxFhPsList(FilterUtils.filterLabelList(deliveryLabelList, "派送"));
            if (null == list || list.isEmpty()) {
                log.info("派送发货明细查询结果为空");
            } else {
                log.info("派送发货明细查询结果条数为：" + list.size());
                //先按发车编号去重,在按发车编号分组统计重量、体积、件数,最后聚合输出数据
                MergeDataUtils.getLastData(countEleByFcbh(FilterUtils.filterFhAndDhList(list, "派送")), list, deliveryLabelList, resList, "派送");
                log.info("派送发货组装返回条数为==>：" + resList.size());
            }
        } catch (Exception e) {
            log.info("查询派送发货异常:" + e);
        } finally {
            countDownLatch.countDown();
        }
        log.info("============查询派送发货任务end=================");
        return resList;
    }

    /**
     * 按发车编号分组统计该发车编号下所有运单的总重量、总体积、总件数
     *
     * @param reqList
     * @return
     */
    private List<FhMxAndDhMxOut> countEleByFcbh(List<FhMxAndDhMxOut> reqList) {
        return fhMxDao.queryPsFhTotalDataByFcbh(reqList);
    }

}