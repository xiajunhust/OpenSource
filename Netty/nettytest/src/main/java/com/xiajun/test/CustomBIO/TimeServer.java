/**
 * XXX.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.CustomBIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 传统的BIO编程-服务端
 * From：李林峰[http://ifeve.com/netty-definitive-guide-2-1/]
 * 
 * BIO主要的问题在于每当有一个新的客户端请求接入时，服务端必须创建一个新的线程处理新接入的客户端链路，一个线程只能处理一个客户端连接。
 * 在高性能服务器应用领域，往往需要面向成千上万个客户端的并发连接，这种模型显然无法满足高性能、高并发接入的场景。
 * 为了改进一线程一连接模型，后来又演进出了一种通过线程池或者消息队列实现1个或者多个线程处理N个客户端的模型，
 * 由于它的底层通信机制依然使用同步阻塞IO，所以被称为 “伪异步”，下面看看伪异步是否能够满足我们对高性能、高并发接入的诉求。
 * 
 * @author xiajun.xj
 * @version $Id: TimeServer.java, v 0.1 2014年11月11日 下午8:50:25 xiajun.xj Exp $
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
            while (true) {
                socket = server.accept();
                new Thread(new TimeServerHandler(socket)).start();
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
