/**
 * XXX.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.ThreadPoolBIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import com.xiajun.test.CustomBIO.TimeServerHandler;

/**
 * 伪异步IO-即带线程池的传统BIO
 * From 李林峰[http://ifeve.com/netty-definitive-guide-2-2/]
 * 
 * 当有新的客户端接入的时候，将客户端的Socket封装成一个Task（该任务实现java.lang.Runnable接口）投递到后端的线程池中进行处理,
 * JDK的线程池维护一个消息队列和N个活跃线程对消息队列中的任务进行处理。
 * 由于线程池可以设置消息队列的大小和最大线程数，因此，它的资源占用是可控的，无论多少个客户端并发访问，都不会导致资源的耗尽和宕机。 
 * 
 * 伪异步IO的弊端：
 * 底层依然采用的是同步阻塞模型，无法从根本上解决问题。
 * 伪异步IO实际上仅仅只是对之前IO线程模型的一个简单优化，它无法从根本上解决同步IO导致的通信线程阻塞问题。
 * 
 * @author xiajun.xj
 * @version $Id: TimeServer.java, v 0.1 2014年11月11日 下午9:09:22 xiajun.xj Exp $
 */
public class TimeServer {

    /**
     * @param args
     * @throws IOException
     */
    public static void main(String[] args) throws IOException {
        int port = 8080;
        if (args != null && args.length > 0) {
            try {
                port = Integer.valueOf(args[0]);
            } catch (NumberFormatException e) {
                // 采用默认值
            }
        }
        ServerSocket server = null;
        try {
            server = new ServerSocket(port);
            System.out.println("The time server is start in port : " + port);
            Socket socket = null;
            TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50,
                10000);// 创建IO任务线程池
            while (true) {
                socket = server.accept();
                singleExecutor.execute(new TimeServerHandler(socket));
            }
        } finally {
            if (server != null) {
                System.out.println("The time server close");
                server.close();
                server = null;
            }
        }
    }
}
