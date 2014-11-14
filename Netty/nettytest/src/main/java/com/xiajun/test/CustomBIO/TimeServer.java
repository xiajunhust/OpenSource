/**
 * XXX.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.CustomBIO;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * ��ͳ��BIO���-�����
 * From�����ַ�[http://ifeve.com/netty-definitive-guide-2-1/]
 * 
 * BIO��Ҫ����������ÿ����һ���µĿͻ����������ʱ������˱��봴��һ���µ��̴߳����½���Ŀͻ�����·��һ���߳�ֻ�ܴ���һ���ͻ������ӡ�
 * �ڸ����ܷ�����Ӧ������������Ҫ�����ǧ������ͻ��˵Ĳ������ӣ�����ģ����Ȼ�޷���������ܡ��߲�������ĳ�����
 * Ϊ�˸Ľ�һ�߳�һ����ģ�ͣ��������ݽ�����һ��ͨ���̳߳ػ�����Ϣ����ʵ��1�����߶���̴߳���N���ͻ��˵�ģ�ͣ�
 * �������ĵײ�ͨ�Ż�����Ȼʹ��ͬ������IO�����Ա���Ϊ ��α�첽�������濴��α�첽�Ƿ��ܹ��������ǶԸ����ܡ��߲������������
 * 
 * @author xiajun.xj
 * @version $Id: TimeServer.java, v 0.1 2014��11��11�� ����8:50:25 xiajun.xj Exp $
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
                // ����Ĭ��ֵ
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
