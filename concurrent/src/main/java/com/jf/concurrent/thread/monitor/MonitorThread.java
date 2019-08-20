package com.jf.concurrent.thread.monitor;

import com.google.common.collect.Lists;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 使用一个线程监控其他线程
 *
 * @author Junfeng
 */
public class MonitorThread {

    private static final ExecutorService CHECK_EXECUTOR = Executors.newSingleThreadExecutor();

    public static void main(String[] arg) {
        List<String> list = Lists.newArrayList();
    }

}
