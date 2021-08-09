package com.hoau.bigdata.impl;

import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.OutputStream;

/**
 * @author ：Joel.Li
 * @date ：2021/1/16 10:42
 * @description： description
 * @version: $version
 */
@Service
public class HDFSServiceImpl {

    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    private FileSystem fileSystem;

    private boolean makeFolder(String path) throws IOException {
        Configuration conf = fileSystem.getConf();
        FileSystem fy = FileSystem.get(conf);
        boolean target = false;
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        if (existFile(path)) {
            return true;
        }
        Path src = new Path(path);
        try {
            target = fy.mkdirs(src);
        } catch (IOException e) {
            logger.error("create folder <" + path + "> failed:" + e.getMessage());
        }
        return target;
    }

    private boolean existFile(String path) throws IOException {
        Configuration conf = fileSystem.getConf();
        FileSystem fy = FileSystem.get(conf);
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        Path src = new Path(path);
        try {
            return fy.exists(src);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return false;
    }

    /**
     * @param baseDir  hdfs地址
     * @param filePath 文件地址
     * @param fileName 文件名称
     * @param content  写入的内容
     * @param flag1    文件不存在是否要新建 true：是  false：否
     * @param flag2    文件存在是否要删除新建 true：是  false：否
     * @param flag3    文件存在是否要尾部追加
     * @param flag4    是否删除目录下所有文件：false：否，只删除目标文件；true：是，删除目录下所有文件
     * @author: joel
     * @description: 写入hdfs
     * @return: boolean
     * @time: 2021/1/16 11:17
     */
    public boolean writeToHdfs(String baseDir, String filePath, String fileName, String content, boolean flag1, boolean flag2, boolean flag3, boolean flag4) {
        try {
            Configuration conf = fileSystem.getConf();
            FileSystem fy = FileSystem.get(conf);
            boolean fileCheck = existFile(baseDir + filePath + fileName);
            //文件不存在新建
            if (!fileCheck && flag1) {
                if (createFile(filePath, fileName, fy)) {
                    return write(filePath, fileName, content);
                } else {
                    return false;
                }
            }
            //文件不存在且不新建
            else if (!fileCheck && !flag1) {
                logger.info("<" + filePath + fileName + "> File is not exist!!");
                return false;
            }
            //文件存在且要求删除新建
            else if (fileCheck && flag2) {
                //删除目录文件下所有文件
                if (flag4) {
                    deleteFile(filePath);
                }
                //只删目标文件
                else {
                    deleteFile(filePath + fileName);
                }
                if(createFile(filePath, fileName, fy)){
                    return write(filePath, fileName, content);
                } else {
                    logger.info("file create failed");
                }
            }
            //文件存在，尾部追加
            else if (fileCheck && flag3) {
                return write(filePath, fileName, content);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.info("写入异常==》" + e.getMessage());
            return false;
        }
        return false;
    }

    private boolean write(String filePath, String fileName, String content) throws IOException {
        Configuration conf = fileSystem.getConf();
        FileSystem fy = FileSystem.get(conf);
        try {
            OutputStream output = fy.append(new Path(filePath + fileName));
            output.write(content.getBytes("UTF-8"));
            output.close();
            fy.close();
        } catch (Exception e) {
            logger.error("fs.write写入文件<" + filePath + fileName + ">失败：" + e);
            return false;
        }
        return true;
    }

    private boolean createFile(String filePath, String fileName, FileSystem fy) throws IOException {
        if (makeFolder(filePath)) {
            try {
                fy.create(new Path(filePath + fileName)).close();
                return true;
            } catch (IOException e) {
                logger.info("create file <" + filePath + fileName + "> IOException");
                return false;
            }
        } else {
            logger.info("filePath <" + filePath + fileName + "> failed,please check path");
            return false;
        }
    }


    private boolean deleteFile(String path) throws IOException {
        Configuration conf = fileSystem.getConf();
        FileSystem fy = FileSystem.get(conf);
        boolean target = false;
        if (StringUtils.isEmpty(path)) {
            return false;
        }
        if (!existFile(path)) {
            return false;
        }
        Path src = new Path(path);
        try {
            target = fy.delete(src);
        } catch (IOException e) {
            logger.error(e.getMessage());
        }
        return target;
    }

}
