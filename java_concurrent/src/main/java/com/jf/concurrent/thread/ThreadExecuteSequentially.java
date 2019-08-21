package com.jf.concurrent.thread;

import java.util.concurrent.Semaphore;

/**
 * 线程依次交替执行，使用信号量控制多个线程依次交替执行.
 *
 * @author Junfeng
 */
public class ThreadExecuteSequentially {

    private static int n = 0;

    public static void main(String[] arg) throws InterruptedException {

        ttt(3);

    }

    /**
     * 线程依次交替执行 .</p>

     * <p>CreateTime: 2019/3/26.</p>
     *
     * @param N 线程数
     * @author Junfeng
     */
    public static void ttt(int N) throws InterruptedException {
        Thread[] threads = new Thread[N];
        final Semaphore[] syncObjects = new Semaphore[N];
        for (int i = 0; i < N; i++) {
            syncObjects[i] = new Semaphore(1);
            if (i != N - 1) {
                syncObjects[i].acquire();
            }
        }
        for (int i = 0; i < N; i++) {
            final Semaphore lastSemphore = i == 0 ? syncObjects[N - 1] : syncObjects[i - 1];
            final Semaphore curSemphore = syncObjects[i];
            final int index = i;
            threads[i] = new Thread(() -> {
                try {
                    while (true) {
                        // 获取前一个的 信号量，如果前一个释放了则 当前开始执行，并释放当前信号量，提供下一线程执行
                        lastSemphore.acquire();
                        System.out.println("thread" + index + ": " + n++);
                        if (n > 100) {
                            System.exit(0);
                        }
                        curSemphore.release();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }

            });
            threads[i].start();
        }
    }
}
