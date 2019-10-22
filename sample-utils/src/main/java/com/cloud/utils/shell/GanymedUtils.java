/*
 * 文件名：GanymedUtils.java
 * 版权：Copyright 2012-2016 YLINK Tech. Co. Ltd. All Rights Reserved. 
 * 描述： 新增 GanymedUtils.java
 * 修改人：lizhi 
 * 修改时间：2019年8月19日
 * 修改内容：新增
 */
package com.cloud.utils.shell;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import ch.ethz.ssh2.ChannelCondition;
import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;
import ch.ethz.ssh2.StreamGobbler;

/**
 * @Title:  GanymedUtils.java
 * @Package: com.ylink.utils.shell.GanymedUtils.java
 * @Description: 
 * @Company: ylink
 * @author  lizhi 
 * @date  2019年8月19日 下午5:44:56
 */
public class GanymedUtils {

	private static final Logger logger = LoggerFactory.getLogger(GanymedUtils.class);

	/** 超时时间 */
    private static final int TIME_OUT = 1000 * 5 * 60;
    
    /**
     * 登录远端服务器
     *
     * @param ip       主机地址
     * @param userName 用户名
     * @param password 密码
     * @return 当前的连接
     * @throws IOException
     */
    public static Connection login(String ip, String userName, String password) throws IOException {
        Connection connection = new Connection(ip);
        connection.connect();
        return connection.authenticateWithPassword(userName, password) ? connection : null;
    }
    
    /**
     * 执行一个命令
     *
     * @param ip       主机ip
     * @param userName 用户名
     * @param password 密码
     * @param scripts  需要执行的脚本
     * @param charset  字符编码
     * @return ShellResult类
     * @throws Exception
     */
    public static ShellResult exec(String ip, String userName, String password, String scripts, Charset charset) throws IOException {

        Connection connection = login(ip, userName, password);
        if (connection == null) {
            throw new RuntimeException("登录远程服务器出现异常,ip为:" + ip);
        }

        // Open a new {@link Session} on this connection
        Session session = connection.openSession();

		try (InputStream stdOut = new StreamGobbler(session.getStdout()); InputStream stdErr = new StreamGobbler(session.getStderr())) {
			// Execute a command on the remote machine.
			session.execCommand(scripts);
			String outStr = processStream(stdOut, charset.name());
			String outErr = processStream(stdErr, charset.name());
			session.waitForCondition(ChannelCondition.EXIT_STATUS, TIME_OUT);
			int exitStatus = session.getExitStatus();
			return new ShellResult(outStr, outErr, exitStatus);
		}
    }

    /**
     * 执行脚本
     *
     * @param in      输入流
     * @param charset 字符编码
     * @return
     * @throws IOException
     */
    private static String processStream(InputStream in, String charset) throws IOException {
        byte[] buf = new byte[1024];
        StringBuilder sb = new StringBuilder();
        while (in.read(buf) != -1) {
            sb.append(new String(buf, charset));
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        try {
            ShellResult status = exec("172.168.200.53", "bkfp", "bkfp", "/home/bkfp/bkfp/bkfp-channel/shutdown.sh", StandardCharsets.UTF_8);
            System.out.println(">>>>>>Result>>>>>>>");
            System.out.println(status.getResult());
            System.out.println(">>>>>>ErrorMsg>>>>>>>>");
            System.out.println(status.getErrorMsg());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
