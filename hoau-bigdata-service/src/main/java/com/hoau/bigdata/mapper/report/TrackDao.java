package com.hoau.bigdata.mapper.report;

import com.hoau.bigdata.entity.track.*;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface TrackDao {



    /**
     * 查询外发单
     * @return
     */
    List<OutWardBillEntity> queryOutWardBill(@Param("sec") int sec);

    /**
     * 查询回单登记
     * @return
     */
    List<ReceiptRegistrationEntity> queryReceiptRegistration(@Param("sec") int sec);


    /**
     * 查询转库记录
     * @return
     */
    List<GoodsTransferEntity> queryGoodsTransfer(@Param("sec") int sec);

    /**
     * 查询易安装
     * @return
     */
    List<EasyInstallEntity> queryEasyInstall(@Param("sec") int sec);

}
