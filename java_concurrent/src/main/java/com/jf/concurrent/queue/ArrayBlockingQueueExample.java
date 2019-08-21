package com.jf.concurrent.queue;

import java.util.concurrent.ArrayBlockingQueue;

import static java.lang.System.out;

/**
 * 数组阻塞队列
 *
 * @author Junfeng
 */
public class ArrayBlockingQueueExample {
    public static void main(String[] arg) throws InterruptedException {

        ArrayBlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);

        Thread producer = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                out.println(" -[DEBUG]-  producer  ");
                try {
                    queue.put(i);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    out.println(" -[DEBUG]-  consumer " + queue.take());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        producer.start();
        consumer.start();

        Thread.sleep(1000000);

    }
}
