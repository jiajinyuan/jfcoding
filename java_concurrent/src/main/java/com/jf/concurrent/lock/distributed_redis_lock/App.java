package com.jf.concurrent.lock.distributed_redis_lock;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Description: 测试入口
 *
 * @author SF2121
 */
public class App {

    public static void main(String[] arg) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);

        executorService.submit(Task.builder().name("AAA").build());
        executorService.submit(Task.builder().name("BBB").build());
        executorService.submit(Task.builder().name("CCC").build());
        executorService.submit(Task.builder().name("DDD").build());
        executorService.submit(Task.builder().name("EEE").build());

        executorService.shutdown();
    }
}
