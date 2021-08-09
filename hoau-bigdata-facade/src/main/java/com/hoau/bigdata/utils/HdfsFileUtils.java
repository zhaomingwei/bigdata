package com.hoau.bigdata.utils;

import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FSDataInputStream;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;
import java.io.OutputStream;
import java.net.URI;
import java.nio.charset.Charset;
import java.text.MessageFormat;

/**
 * @Description: Hdfs文件操作
 * @Author: zhaowei
 * @Date: 2020/11/2
 * @Time: 15:50
 */
public class HdfsFileUtils {

    private static final Log log = LogFactory.getLog(HdfsFileUtils.class);

    /**
     * 写入文件
     *
     * @param baseDir  hdfs地址
     * @param filePath 文件地址
     * @param fileName 文件名称
     * @param content  写入的内容
     */
    public static void writeToHdfs(String baseDir, String filePath, String fileName, String content) {
        writeToHdfs(baseDir, filePath, fileName, content, false, false, false);
    }

    public static void writeToHdfs(String baseDir, String filePath, String fileName, String content, boolean flag1, boolean flag2, boolean flag3) {
        writeToHdfs(baseDir, filePath, fileName, content, flag1, flag2, flag3, false);
    }

    /**
     * 追加文件
     *
     * @param baseDir  hdfs地址
     * @param filePath 文件地址
     * @param fileName 文件名称
     * @param content  写入的内容
     * @param flag1    文件不存在是否要新建 true：是  false：否
     * @param flag2    文件存在是否要删除新建 true：是  false：否
     * @param flag3    文件存在是否要尾部追加
     * @param flag4    是否删除目录下所有文件：false：否，只删除目标文件；true：是，删除目录下所有文件
     */
    public static void writeToHdfs(String baseDir, String filePath, String fileName, String content, boolean flag1, boolean flag2, boolean flag3, boolean flag4) {
        Configuration conf = new Configuration();
        //FileSystem.get()方法如果传入的baseDir是一样的话，不设置此值，默认会从走缓存，缓存key是uri+conf : Key key = new Key(uri, conf);
        //设置此值代表每次都新建一个连接
        conf.set("fs.hdfs.impl.disable.cache", "true");
        conf.setBoolean("dfs.support.append", true);
        conf.setBoolean("dfs.client.block.write.replace-datanode-on-failure.enabled", true);
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
        FileSystem fs = null;
        try {
            fs = FileSystem.get(URI.create(baseDir), conf);
            /**文件不存在且入参要求新建**/
            if (!fs.exists(new Path(filePath + fileName)) && flag1) {
                create(fs, filePath, fileName);
                write(baseDir, filePath, fileName, content);
            } else if (!fs.exists(new Path(filePath + fileName)) && !flag1) {//文件不存在且不新建
                log.info("<" + baseDir + filePath + fileName + ">文件不存在!");
            } else if (fs.exists(new Path(filePath + fileName)) && flag2) {//文件存在且要求删除新建
                if (flag4) {
                    delete(fs, filePath);
                } else {
                    delete(fs, filePath + fileName);
                }
                create(fs, filePath, fileName);
                write(baseDir, filePath, fileName, content);
            } else if (fs.exists(new Path(filePath + fileName)) && flag3) {
                log.info("<" + baseDir + filePath + fileName + ">文件已存在,尾部追加!");
                fs.close();
                write(baseDir, filePath, fileName, content);
            }
            fs.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 想删除文件就把文件名加载filepath
     *
     * @param fs
     * @param filePath
     * @return
     */
    public static boolean delete(FileSystem fs, String filePath) {
        try {
            /**删除目录**/
            fs.delete(new Path(filePath), true);
        } catch (Exception e) {
            log.error("fs.delete删除目录<" + filePath + ">失败：" + e);
            return false;
        }
        return true;
    }

    private static boolean create(FileSystem fs, String filePath, String fileName) {
        try {
            /**创建目录**/
            fs.mkdirs(new Path(filePath));
            /**创建文件**/
            fs.create(new Path(filePath + fileName)).close();
        } catch (Exception e) {
            log.error("fs.create创建文件<" + filePath + fileName + ">失败：" + e);
            return false;
        }
        return true;
    }


    private static boolean write(String baseDir, String filePath, String fileName, String content) {
        Configuration conf = new Configuration();
        //FileSystem.get()方法如果传入的baseDir是一样的话，不设置此值，默认会从走缓存，缓存key是uri+conf : Key key = new Key(uri, conf);
        //设置此值代表每次都新建一个连接
        conf.set("fs.hdfs.impl.disable.cache", "true");
        conf.setBoolean("dfs.support.append", true);
        conf.setBoolean("dfs.client.block.write.replace-datanode-on-failure.enabled", true);
        conf.set("dfs.client.block.write.replace-datanode-on-failure.policy", "NEVER");
        FileSystem fs = null;
        try {
            fs = FileSystem.get(URI.create(baseDir), conf);
            OutputStream output = fs.append(new Path(filePath + fileName));
            output.write(content.getBytes("UTF-8"));
            output.close();
            fs.close();
        } catch (Exception e) {
            log.error("fs.write写入文件<" + filePath + fileName + ">失败：" + e);
            return false;
        }
        return true;
    }

    /**
     * 打开HDFS上面的文件并返回String字符串
     *
     * @param path HDFS的相对目录路径，比如：/testDir/b.txt
     * @return FSDataInputStream
     * @author adminstrator
     * @since 1.0.0
     */
    public static String openWithString(String baseDir, String path) {
        Configuration conf = new Configuration();
        //HDFS文件路径
        Path hdfsPath = new Path(path);

        FileSystem fileSystem = null;
        FSDataInputStream inputStream = null;
        try {
//            fileSystem = getFileSystem();
            fileSystem = FileSystem.get(URI.create(baseDir), conf);
            inputStream = fileSystem.open(hdfsPath);

            return IOUtils.toString(inputStream, Charset.forName("UTF-8"));
        } catch (IOException e) {
            log.error(MessageFormat.format("打开HDFS上面的文件失败，path:{0}", path), e);
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    // ignore
                }
            }
        }

        return null;
    }

    /**
     * 打开HDFS上面的文件并转换为Java对象（需要HDFS上门的文件内容为JSON字符串）
     *
     * @param path HDFS的相对目录路径，比如：/testDir/c.txt
     * @return FSDataInputStream
     * @author adminstrator
     * @since 1.0.0
     */
    public static <T extends Object> T openWithObject(String baseDir, String path, Class<T> clazz) {
        //1、获得文件的json字符串
        String jsonStr = openWithString(baseDir, path);

        //2、使用com.alibaba.fastjson.JSON将json字符串转化为Java对象并返回
//        return JsonUtils.toObject(jsonStr, clazz);
        return null;
    }

}
