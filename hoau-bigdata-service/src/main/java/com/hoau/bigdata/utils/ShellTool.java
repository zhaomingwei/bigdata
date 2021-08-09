package com.hoau.bigdata.utils;

import com.jcraft.jsch.*;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @author ：Joel.Li
 * @date ：2021/2/2 11:26
 * @description： description
 * @version: $version
 */
public class ShellTool {
    private static final Log log = LogFactory.getLog(ShellTool.class);

    public static boolean SSHCommand(String host, String user, String pass, String command) {
        return SSHCommand(host, user, pass, 22, command);
    }

    public static boolean SSHCommand(String host, String user, String pass, int port, String command) {
        JSch jsch = new JSch();
        Session session = null;
        Channel channel = null;
        try {
            session = jsch.getSession(user, host, port);
            session.setPassword(pass);
            session.setTimeout(2000);
            Properties config = new Properties();
            config.put("StrictHostKeyChecking", "no");
            session.setConfig(config);
            session.connect();
            channel = session.openChannel("exec");
            ChannelExec execChannel = (ChannelExec) channel;
            execChannel.setCommand(command);
            InputStream in = channel.getInputStream();
            channel.connect();
            StringBuffer sb = new StringBuffer();
            int c = -1;
            while ((c = in.read()) != -1) {
                sb.append((char) c);
            }
//            System.out.println("输出结果是：" + sb.toString());
            log.info("执行MR结果：" + sb.toString());
            in.close();
            return true;
        } catch (JSchException e) {
            e.printStackTrace();
            log.info("执行MR结果异常：" + e);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("执行MR结果异常：" + e);
        } finally {
            channel.disconnect();
            session.disconnect();
        }
        return false;
    }
}
