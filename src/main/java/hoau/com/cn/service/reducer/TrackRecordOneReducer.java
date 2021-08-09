package hoau.com.cn.service.reducer;

import com.google.gson.Gson;
import hoau.com.cn.entity.*;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Reducer;

/**
 * @description:
 * @author: zxy
 * @create: 2020/12/7 15:44
 */
public class TrackRecordOneReducer extends Reducer<Text, Text, Text, Text> {

    Gson gson = new Gson();
    EasyInstallEntity easyInstallEntity = new EasyInstallEntity();
    GoodsTransferEntity goodsTransferEntity = new GoodsTransferEntity();
    OutWardBillEntity outWardBillEntity = new OutWardBillEntity();
    ReceiptRegistrationEntity receiptRegistrationEntity = new ReceiptRegistrationEntity();


    @Override
    protected void reduce(Text key, Iterable<Text> value, Context context) {
        try {
            StringBuilder builder = new StringBuilder("");
            EasyInstallEntity installEntity = new EasyInstallEntity();
            GoodsTransferEntity transferEntity = new GoodsTransferEntity();
            OutWardBillEntity wardBillEntity = new OutWardBillEntity();
            ReceiptRegistrationEntity registrationEntity = new ReceiptRegistrationEntity();
            int registrationNum = 0;
            int wardBillNum= 0;
            int transferNum= 0;
            int installNum= 0;
            for (Text item : value) {
                String val = item.toString();
                System.out.println("运单记录"+val);
                if (val.startsWith("EasyInstallEntity")){
                    easyInstallEntity = gson.fromJson(val.replaceAll("EasyInstallEntity", ""), EasyInstallEntity.class);

                    if (installEntity.getRecordTime() != null){
                        if (easyInstallEntity.getRecordTime().compareTo(installEntity.getRecordTime()) > 0){
                            BeanUtils.copyProperties(installEntity,easyInstallEntity);
                        }
                    } else {
                        BeanUtils.copyProperties(installEntity,easyInstallEntity);
                    }
                    installNum = 1;
                } else if (val.startsWith("GoodsTransferEntity")){
                    goodsTransferEntity = gson.fromJson(val.replaceAll("GoodsTransferEntity", ""), GoodsTransferEntity.class);

                    if (transferEntity.getRecordTime() != null){
                        if (goodsTransferEntity.getRecordTime().compareTo(transferEntity.getRecordTime()) > 0){
                            BeanUtils.copyProperties(transferEntity,goodsTransferEntity);
                        }
                    } else {
                        BeanUtils.copyProperties(transferEntity,goodsTransferEntity);
                    }
                    transferNum = 1;

                    if (goodsTransferEntity != null){
                        if (builder.toString().equals("")){
                            builder.append(goodsTransferEntity.getRecordTime());
                        } else if (goodsTransferEntity.getRecordTime().compareTo(builder.toString()) > 0){
                            builder.delete(0,20);
                            builder.append(goodsTransferEntity.getRecordTime());
                        }
                    }
                }else if (val.startsWith("OutWardBillEntity")){
                    outWardBillEntity = gson.fromJson(val.replaceAll("OutWardBillEntity", ""), OutWardBillEntity.class);
                    if (wardBillEntity.getGenerationDate() != null){
                        if (outWardBillEntity.getGenerationDate().compareTo(wardBillEntity.getGenerationDate()) > 0){
                            BeanUtils.copyProperties(wardBillEntity,outWardBillEntity);
                        }
                    } else {
                        BeanUtils.copyProperties(wardBillEntity,outWardBillEntity);
                    }
                    wardBillNum = 1;
                }else if (val.startsWith("ReceiptRegistrationEntity")){
                    receiptRegistrationEntity = gson.fromJson(val.replaceAll("ReceiptRegistrationEntity", ""), ReceiptRegistrationEntity.class);
                    if (registrationEntity.getRecordTime() != null){
                        if (receiptRegistrationEntity.getRecordTime().compareTo(registrationEntity.getRecordTime()) > 0){
                            BeanUtils.copyProperties(registrationEntity,receiptRegistrationEntity);
                        }
                    } else {
                        BeanUtils.copyProperties(registrationEntity,receiptRegistrationEntity);
                    }
                    registrationNum = 1;
                }
            }
            if (registrationNum == 1){
                TrackRecordEntity entity = new TrackRecordEntity();
                entity.setCurrentStateTime(registrationEntity.getRecordTime());
                entity.setWaybillNo(registrationEntity.getWaybillNo());
                entity.setStateType(registrationEntity.getStateType());
                entity.setDeliveryCompany(registrationEntity.getGenerationCompany());
                entity.setTransferType(registrationEntity.getTransferType());
                System.out.println("保存回单登记"+entity.toString());
                context.write(key, new Text(entity.toString()));
            }
            if (wardBillNum == 1){
                TrackRecordEntity entity = new TrackRecordEntity();
                entity.setWaybillNo(wardBillEntity.getWaybillNo());
                entity.setCurrentStateTime(wardBillEntity.getGenerationDate());
                entity.setDeliveryCompany(wardBillEntity.getUnloadingPlace());
                entity.setDestinationCompany(wardBillEntity.getGenerationCompany());
                entity.setLoadType(wardBillEntity.getLoadType());
                entity.setStateType(wardBillEntity.getStateType());
                entity.setTransferType(wardBillEntity.getTransferType());
                System.out.println("保存外发单"+entity.toString());
                context.write(key, new Text(entity.toString()));
            }
            if (transferNum == 1){
                TrackRecordEntity entity = new TrackRecordEntity();
                entity.setWaybillNo(transferEntity.getWaybillNo());
                entity.setCurrentStateTime(transferEntity.getRecordTime());
                entity.setDeliveryCompany(transferEntity.getTransferCompany());
                entity.setStateType(transferEntity.getStateType());
                System.out.println("保存转库记录"+entity.toString());
                context.write(key, new Text(entity.toString()));
            }
            if (installNum == 1){
                TrackRecordEntity entity = new TrackRecordEntity();
                entity.setCurrentStateTime(installEntity.getRecordTime());
                entity.setDeliveryCompany(installEntity.getInstallationStatus());
                entity.setWaybillNo(installEntity.getWaybillNo());
                entity.setStateType(installEntity.getStateType());
                System.out.println("保存安装平台"+entity.toString());
                context.write(key, new Text(entity.toString()));
            }
        } catch (Exception e) {
            System.out.println("FhMxAndDhMxReducer->exception:" + e);
        }
    }

}
