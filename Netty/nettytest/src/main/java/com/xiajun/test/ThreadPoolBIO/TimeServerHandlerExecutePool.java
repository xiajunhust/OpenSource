/**
 * Alipay.com Inc.
 * Copyright (c) 2004-2014 All Rights Reserved.
 */
package com.xiajun.test.ThreadPoolBIO;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ʱ��������������̳߳�
 * 
 * @author xiajun.xj
 * @version $Id: TimeServerHandlerExecutePool.java, v 0.1 2014��11��11�� ����9:11:40 xiajun.xj Exp $
 */
public class TimeServerHandlerExecutePool {

    private final ExecutorService executor;

    public TimeServerHandlerExecutePool(int maxPoolSize, int queueSize) {
        executor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(), maxPoolSize,
            120L, TimeUnit.SECONDS, new ArrayBlockingQueue(queueSize));
    }

    public void execute(java.lang.Runnable task) {
        executor.execute(task);
    }
}
