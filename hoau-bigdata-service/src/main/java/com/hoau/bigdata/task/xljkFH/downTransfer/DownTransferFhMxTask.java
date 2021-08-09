package com.hoau.bigdata.task.xljkFH.downTransfer;

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
 * @Description: 下转移发货明细
 * @Author: zhaowei
 * @Date: 2020/11/17
 * @Time: 17:21
 */
public class DownTransferFhMxTask implements Callable<List<FhMxAndDhMxOut>> {

    Logger log = LoggerFactory.getLogger(getClass());

    private FhMxDao fhMxDao = ManageSpringBeans.getBean(FhMxDao.class);

    private List<Label> downTransferLabelList;

    private CountDownLatch countDownLatch;

    public DownTransferFhMxTask(CountDownLatch countDownLatch, List<Label> downTransferLabelList) {
        this.countDownLatch = countDownLatch;
        this.downTransferLabelList = downTransferLabelList;
    }

    @Override
    public List<FhMxAndDhMxOut> call() {
        log.info("============查询下转移发货任务start=================");
        List<FhMxAndDhMxOut> list;
        List<FhMxAndDhMxOut> resList = new ArrayList<>();
        try {
            if (null == downTransferLabelList || downTransferLabelList.isEmpty()) {
                log.info("下转移发货明细查询入参为空");
                return resList;
            }
            log.info("下转移发货明细查询入参条数:" + downTransferLabelList.size());
            list = fhMxDao.queryLxFhXzyList(FilterUtils.filterLabelList(downTransferLabelList, "下转移发货"));
            if (null == list || list.isEmpty()) {
                log.info("下转移发货明细查询结果为空");
            } else {
                log.info("下转移发货明细查询结果条数为：" + list.size());
                //先按发车编号去重,在按发车编号分组统计重量、体积、件数,最后聚合输出数据
                MergeDataUtils.getLastData(countEleByFcbh(FilterUtils.filterFhAndDhList(list, "下转移发货")), list, downTransferLabelList, resList, "下转移发货");
                log.info("下转移发货组装返回条数为==>：" + resList.size());
            }
        } catch (Exception e) {
            log.info("查询下转移发货任务异常:" + e);
        } finally {
            countDownLatch.countDown();
        }
        log.info("============查询下转移发货任务end=================");
        return resList;
    }

    /**
     * 按发车编号分组统计该发车编号下所有运单的总重量、总体积、总件数
     *
     * @param reqList
     * @return
     */
    private List<FhMxAndDhMxOut> countEleByFcbh(List<FhMxAndDhMxOut> reqList) {
        return fhMxDao.queryXzyFhTotalDataByFcbh(reqList);
    }

}
