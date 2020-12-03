//package hoau.com.cn.service.job.income.check;
//
//import hoau.com.cn.entity.income.check.DbInfo;
//import hoau.com.cn.entity.income.check.RecvWaybillEntity;
//import hoau.com.cn.service.mapper.income.check.RecvWaybillMapper;
//import hoau.com.cn.service.reducer.income.check.RecvWaybillReducer;
////import hoau.com.cn.utils.Config;
//import org.apache.hadoop.conf.Configuration;
//import org.apache.hadoop.util.ToolRunner;
//
//import java.time.LocalDate;
//import java.time.LocalDateTime;
//import java.time.format.DateTimeFormatter;
//
///**
// * 应收款_付清考核_运单明细；每天生成1次
// * Created by haijin-w on 2020/10/28.
// */
//public class ReceivableJob {
//    public static void main(String[] args) throws Exception {
//        System.out.println("应收款_BO_付清运单明细开始时间:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//
//        Configuration configuration = new Configuration();
//
//        DbInfo dbInfo = new DbInfo();
//        //50报表库
//        dbInfo.setDriverClass("driver.class");
//        dbInfo.setDriverUrl("driver.url");
//        dbInfo.setDriverUserName("driver.username");
//        dbInfo.setDriverPwd("driver.password");
//
//        WriteToHdfsBySqlTool writeToHdfsTool = new WriteToHdfsBySqlTool();
//        writeToHdfsTool.setJobName("ReceivableJob");
//        writeToHdfsTool.setToolClassConf(WriteToHdfsBySqlTool.class);
//        writeToHdfsTool.setEntityMxClass(RecvWaybillEntity.class);
//        //writeToHdfsTool.setOutName(Config.getProperty("recvWaybillEntity.outName").replace(".log",LocalDate.now().format(DateTimeFormatter.ofPattern("_yyyyMMdd")).concat(".log")));
//        writeToHdfsTool.setOutName("/recvWaybillEntity.log".replace(".log",LocalDate.now().format(DateTimeFormatter.ofPattern("_yyyyMMdd")).concat(".log")));
//        //writeToHdfsTool.setTargetDir(Config.getProperty("baseDir") + Config.getProperty("recvWaybillEntity.dir")+ LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));//每天生成一次
//        writeToHdfsTool.setTargetDir("hdfs://10.39.251.159:9000" + "/report/incomeCheck/recvWaybillEntity"+ LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")));//每天生成一次
//        writeToHdfsTool.setQuerySql(getSql("query"));
//        writeToHdfsTool.setCountSql(getSql("count"));
//        writeToHdfsTool.setMapperClass(RecvWaybillMapper.class);
//        writeToHdfsTool.setReducerClass(RecvWaybillReducer.class);
//        writeToHdfsTool.setDbInfo(dbInfo);
//
//        int res = ToolRunner.run(configuration, writeToHdfsTool, args);
//
//        System.out.println("应收款_BO_付清运单明细结束时间:" + LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
//        System.exit(res);
//    }
//
//    /**
//     *
//     * @param type  "query","count"
//     * @return
//     */
//    private static String getSql(String type){
//        StringBuffer querySql = new StringBuffer();
//        StringBuffer countSql = new StringBuffer();
//        querySql.append("select t.* from(")
//                .append("SELECT RC.YSMD,")
//                .append("       DECODE(RC.YSMD,'N物联M1','N北京',RC.YSYJGS) YSYJGS,")
//                .append("       CASE WHEN RC.YSMD = 'N物联M1' THEN '华北事业部' ELSE ")
//                .append("       DECODE(RC.JYDQ,'上海大区','上海事业部',RC.SYB) END SYB,")
//                .append("       CASE WHEN RC.YSMD = 'N物联M1' THEN '北京大区' ELSE ")
//                .append("       DECODE(RC.JYDQ,'上海大区','上海事业部',RC.JYDQ) END JYDQ,")
//                .append("       RC.YSMDSSLQ,")
//                .append("       DECODE(RC.YSMD,'N物联M1','华北事业部财务部',DECODE(RC.YSMDDQ,'上海大区','上海事业部财务部',RC.YSMDSYB)) CWSYB,")
//                .append("       DECODE(RC.YSMD,'N物联M1','北京大区财务部',DECODE(RC.YSMDDQ,'上海大区','上海事业部财务部',RC.YSMDDQ)) CWDQ,")
//                .append("       NVL(RC.KHBM,(SELECT KHBH FROM HYDATA.LD_KHXX KX WHERE KX.KHMC = RC.KHMC AND KX.SFYX = 1 AND KX.KHMC NOT IN ('无') AND ROWNUM = 1)) KHBM,")
//                .append("       RC.KHMC,")
//                .append("       RC.YDBH,")
//                .append("       CASE")
//                .append("         WHEN (KH.KHLB = 4 AND KH.KHBH NOT IN ('897601','902758','903135','902825','904062')) THEN")
//                .append("          '战略'")
//                .append("        /** WHEN REGEXP_LIKE(RC.KHMC, '敏华') THEN")
//                .append("          '战略'*/")
//                .append("         WHEN EXISTS (SELECT 1 FROM REPORT_RECV_ZLKH_INCLUDE RI WHERE RI.KHBH IS NOT NULL AND RI.KHBH = RC.KHBM) THEN")
//                .append("           '战略'")
//                .append("         WHEN EXISTS (SELECT 1 FROM REPORT_RECV_ZLKH_INCLUDE RI WHERE RI.KHBH IS NULL AND RI.KHMC = RC.KHMC) THEN")
//                .append("          '战略'")
//                .append("         ELSE")
//                .append("          '非战略'")
//                .append("       END SFZLKH,")
//                .append("       RC.JSFS,")
//                .append("       RC.TYRQ,")
//                .append("       RC.ZL,")
//                .append("       DECODE(RC.SFDY, 'Y', '是', 'N', '否', '否') SFDY,")
//                .append("       DECODE(RC.SF7TYN, 'Y', '是', 'N', '否', '否') SF7TYN,")
//                .append("       RC.FHGS,")
//                .append("       RC.DHGS,")
//                .append("       RC.YDWZ,")
//                .append("       DECODE(RC.YDZT,")
//                .append("              0,")
//                .append("              '在途',")
//                .append("              1,")
//                .append("              '在库',")
//                .append("              2,")
//                .append("              '送达',")
//                .append("              3,")
//                .append("              '呆货',")
//                .append("              4,")
//                .append("              '死货',")
//                .append("              5,")
//                .append("              '注销',")
//                .append("              6,")
//                .append("              '异常',")
//                .append("              7,")
//                .append("              '已提走',")
//                .append("              8,")
//                .append("              '提货中',")
//                .append("              '送货中') YDZT,")
//                .append("       RC.HJJE,")
//                .append("       RC.YSJE,")
//                .append("       RC.TYRLXR,")
//                .append("       RC.TYRLXDH,")
//                .append("       RC.SHRLXR,")
//                .append("       RC.SHRLXDH,")
//                .append("       RC.FDQRGS,")
//                .append("       RC.LDYSZGS,")
//                .append("       RC.HWMC,")
//                .append("       RC.BZFS,")
//                .append("       RC.YSLX,")
//                .append("       TO_CHAR(RC.SDSJ, 'YYYY/MM/DD HH24:MI:SS') SDSJ,")
//                .append("       RC.KHSSMD,")
//                .append("       RC.YDLBFH,")
//                .append("       RC.SJKPJE,")
//                .append("       RC.SPHM,")
//                .append("       DECODE(RC.SFPT, 'Y', '是', 'N', '否', '否') SFPT,")
//                .append("       DECODE(RC.SF180TYN, 'Y', '是', 'N', '否', '否') SF180TYN,")
//                .append("       DECODE(RC.PFZT, 0, '未赔付', 1, '已赔付', '') PFZT,")
//                .append("       CASE WHEN RC.SALES_MANAGER IS NULL THEN '' ELSE  RC.SALES_MANAGER END  SALES_MANAGER,")
//                .append("       RC.JS,")
//                .append("       RC.TMDDH,")
//                .append("       '' BZ,")
//                .append("       RC.SHRDZ,")
//                .append("       RC.CWSLJE,")
//                .append("       RC.CWYSKJE,")
//                .append("       RC.HJJE - (NVL(RC.CWSLJE,0) - NVL(RC.CWHCJE,0)) CWSJYSK,")
//                .append("      '' salesManagerPunish,")
//                .append("      '' seniorMarketingPunish,")
//                .append("      '' regionalManagerPunish")
//                .append("  FROM REPORT_RECERVABLES RC")
//                .append("  LEFT JOIN HYDATA.LD_KHXX KH")
//                .append("    ON KH.KHBH = RC.KHBM")
//                .append("  LEFT JOIN REPORT_JY_MDYS MD ")
//                .append("    ON MD.GSJC = RC.YSMD")
//                .append(" WHERE 1=1 AND RC.TYRQ < TRUNC(SYSDATE)")
//                .append("   AND RC.ydbh not in(select ydbh FROM REPORT_RECERVABLES_exclude)")
//                .append("   ) t")
//                .append("   where t.JSFS ='发货人付清'")
//                .append("   and t.SFZLKH = '非战略'")
//                .append("   and t.SF7TYN='否'");
//        switch (type){
//            case "query":
//                return  querySql.toString();
//            case "count":
//                countSql.append("select count(*) from(").append(querySql).append(")");
//                return countSql.toString();
//            default:
//                return "传入类型query或count，获得对应类型";
//        }
//    }
//
//}
