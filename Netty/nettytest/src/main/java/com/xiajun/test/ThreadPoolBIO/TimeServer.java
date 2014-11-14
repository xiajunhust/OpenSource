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
 * α�첽IO-�����̳߳صĴ�ͳBIO
 * From ���ַ�[http://ifeve.com/netty-definitive-guide-2-2/]
 * 
 * �����µĿͻ��˽����ʱ�򣬽��ͻ��˵�Socket��װ��һ��Task��������ʵ��java.lang.Runnable�ӿڣ�Ͷ�ݵ���˵��̳߳��н��д���,
 * JDK���̳߳�ά��һ����Ϣ���к�N����Ծ�̶߳���Ϣ�����е�������д���
 * �����̳߳ؿ���������Ϣ���еĴ�С������߳�������ˣ�������Դռ���ǿɿصģ����۶��ٸ��ͻ��˲������ʣ������ᵼ����Դ�ĺľ���崻��� 
 * 
 * α�첽IO�ı׶ˣ�
 * �ײ���Ȼ���õ���ͬ������ģ�ͣ��޷��Ӹ����Ͻ�����⡣
 * α�첽IOʵ���Ͻ���ֻ�Ƕ�֮ǰIO�߳�ģ�͵�һ�����Ż������޷��Ӹ����Ͻ��ͬ��IO���µ�ͨ���߳��������⡣
 * 
 * @author xiajun.xj
 * @version $Id: TimeServer.java, v 0.1 2014��11��11�� ����9:09:22 xiajun.xj Exp $
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
            TimeServerHandlerExecutePool singleExecutor = new TimeServerHandlerExecutePool(50,
                10000);// ����IO�����̳߳�
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
