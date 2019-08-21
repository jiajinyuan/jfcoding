package com.jf.concurrent.thread;

import java.util.Random;
import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

/**
 * 交换机示例，两个线程(多个不行，只能是一对线程)可以进行互相交换对象的会和点, 交换数据为阻塞方法
 *
 * @author Junfeng
 */
public class ExchangerExample {

    public static void main(String[] arg) throws InterruptedException {

        ExchangerExample example = new ExchangerExample();
        example.run();

    }

    private void run() throws InterruptedException {
        ExecutorService pool = Executors.newCachedThreadPool();
        Exchanger<Integer> exchanger = new Exchanger<>();

        ExchangerProducer producer = new ExchangerProducer(exchanger);
        ExchangerConsumer consumer = new ExchangerConsumer(exchanger);

        pool.submit(producer);
        pool.submit(consumer);

        TimeUnit.SECONDS.sleep(5);
        pool.shutdown();

    }

    class ExchangerProducer implements Runnable {
        private Exchanger<Integer> exchanger;
        private Integer data;

        ExchangerProducer(Exchanger<Integer> exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(1);
                data = new Random().nextInt(20);
                out.println(" -[DEBUG]-   before : " + data);
                data = exchanger.exchange(data);
                out.println(" -[DEBUG]-   after  : " + data);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }

    class ExchangerConsumer implements Runnable {
        private Exchanger<Integer> exchanger;
        private int data;

        ExchangerConsumer(Exchanger<Integer> exchanger) {
            this.exchanger = exchanger;
        }

        @Override
        public void run() {
            try {
                out.println(" -[DEBUG]-  " + Thread.currentThread().getName() + " .................");
                out.println(" -[DEBUG]-  " + Thread.currentThread().getName() + " cousumer : " + exchanger.exchange(data));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
