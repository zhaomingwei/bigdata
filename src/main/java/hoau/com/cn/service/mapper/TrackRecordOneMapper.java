package hoau.com.cn.service.mapper;

import com.google.gson.Gson;
import hoau.com.cn.entity.*;
import org.apache.hadoop.io.LongWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

/**
 * @description:
 * @author: zxy
 * @create: 2020/12/7 15:43
 */
public class TrackRecordOneMapper extends Mapper<LongWritable, Text, Text, Text> {

    Text k = new Text();
    EasyInstallEntity easyInstallEntity = new EasyInstallEntity();
    GoodsTransferEntity goodsTransferEntity = new GoodsTransferEntity();
    OutWardBillEntity outWardBillEntity = new OutWardBillEntity();
    ReceiptRegistrationEntity receiptRegistrationEntity = new ReceiptRegistrationEntity();
    Gson gson = new Gson();
    StringBuilder sb;
    /**
     * map方法是提供给map task进程来调用的，map task进程是每读取一行文本来调用一次我们自定义的map方法
     * map task在调用map方法时，传递的参数：
     * 一行的起始偏移量LongWritable作为key
     * 一行的文本内容Text作为value
     */
    @Override
    protected void map(LongWritable key, Text value, Context context) {
        String val = value.toString();
        sb = new StringBuilder();
        try {
            if (val.startsWith("EasyInstallEntity")){
                easyInstallEntity = gson.fromJson(val.replaceAll("EasyInstallEntity", ""), EasyInstallEntity.class);
                if (easyInstallEntity != null){
                    k.set(easyInstallEntity.getWaybillNo());
                }
            } else if (val.startsWith("GoodsTransferEntity")){
                goodsTransferEntity = gson.fromJson(val.replaceAll("GoodsTransferEntity", ""), GoodsTransferEntity.class);
                if (goodsTransferEntity != null){
                    k.set(goodsTransferEntity.getWaybillNo());
                }
            }else if (val.startsWith("OutWardBillEntity")){
                outWardBillEntity = gson.fromJson(val.replaceAll("OutWardBillEntity", ""), OutWardBillEntity.class);
                if (outWardBillEntity != null){
                    k.set(outWardBillEntity.getWaybillNo());
                }
            }else if (val.startsWith("ReceiptRegistrationEntity")){
                receiptRegistrationEntity = gson.fromJson(val.replaceAll("ReceiptRegistrationEntity", ""), ReceiptRegistrationEntity.class);
                if (receiptRegistrationEntity != null){
                    k.set(receiptRegistrationEntity.getWaybillNo());
                }
            }
            //System.out.println("mapper->:key:" + k + ", value:" + val);
            context.write(k, value);
        }catch (Exception e){
            System.out.println("FhMxAndDhMxMapper-exception:" + e);
        }
    }
}
