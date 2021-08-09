package com.hoau.bigdata.task.xljkDH.upTransfer;

import com.hoau.bigdata.entity.report.FhMxAndDhMxOut;
import com.hoau.bigdata.entity.tm.Label;
import com.hoau.bigdata.mapper.report.DhMxDao;
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
 * @Description: 上转移到货明细
 * @Author: zhaowei
 * @Date: 2020/12/10
 * @Time: 17:09
 */
public class SzyDhMxTask implements Callable<List<FhMxAndDhMxOut>> {

    Logger log = LoggerFactory.getLogger(getClass());

    private DhMxDao dhMxDao = ManageSpringBeans.getBean(DhMxDao.class);

    private CountDownLatch countDownLatch;

    private List<Label> szyLabelList;

    public SzyDhMxTask(CountDownLatch countDownLatch, List<Label> szyLabelList) {
        this.countDownLatch = countDownLatch;
        this.szyLabelList = szyLabelList;
    }

    @Override
    public List<FhMxAndDhMxOut> call() {
        log.info("============查询上转移到货任务start=================");
        List<FhMxAndDhMxOut> list;
        List<FhMxAndDhMxOut> resList = new ArrayList<>();
        try {
            if (null == szyLabelList || szyLabelList.isEmpty()) {
                log.info("上转移到货明细查询入参为空");
                return resList;
            }
            log.info("上转移到货明细查询入参条数:" + szyLabelList.size());
            list = dhMxDao.queryLxDhSzyList(FilterUtils.filterLabelList(szyLabelList,"上转移到货"));
            if (null == list || list.isEmpty()) {
                log.info("上转移到货查询结果为空");
            } else {
                log.info("上转移到货查询结果条数为：" + list.size());
                //先按发车编号去重,在按发车编号分组统计重量、体积、件数,最后聚合输出数据
                MergeDataUtils.getLastData(countEleByFcbh(FilterUtils.filterFhAndDhList(list, "上转移到货")), list, szyLabelList, resList, "上转移到货");
                log.info("上转移到货组装返回条数为==>：" + resList.size());
            }
        } catch (Exception e) {
            log.info("查询上转移到货任务异常:" + e);
        } finally {
            countDownLatch.countDown();
        }
        log.info("============查询上转移到货任务end=================");
        return resList;
    }

    /**
     * 按发车编号分组统计该发车编号下所有运单的总重量、总体积、总件数
     *
     * @param reqList
     * @return
     */
    private List<FhMxAndDhMxOut> countEleByFcbh(List<FhMxAndDhMxOut> reqList) {
        return dhMxDao.querySzyDhTotalDataByFcbh(reqList);
    }

}
