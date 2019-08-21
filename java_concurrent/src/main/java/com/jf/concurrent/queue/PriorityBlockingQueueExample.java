package com.jf.concurrent.queue;

import java.util.Random;
import java.util.concurrent.PriorityBlockingQueue;

import static java.lang.System.out;

/**
 * 优先级队列，根据元素实现compareTo 比较方法来确定优先级，优先级高的先被取出
 *
 * @author Junfeng
 */
public class PriorityBlockingQueueExample {

    public static void main(String[] arg) throws InterruptedException {
        PriorityBlockingQueue<Order> queue = new PriorityBlockingQueue<>();

        Thread consumer = new Thread(() -> {
            while (true) {
                try {
                    Order order = queue.take();
                    out.println(" -[DEBUG]-   " + order.getId() + "  n=" + order.getN());
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });

        for (int i = 1; i < 10; i++) {
            Random random = new Random();
            int i1 = random.nextInt(30);
            out.println(" -[DEBUG]-   " + i + "   --- " + i1);
            queue.add(new Order("order: " + i, i1));
        }
        consumer.start();
        Thread.sleep(100000);
    }


    protected static class Order implements Comparable {

        private String id;
        private Integer n;


        public Order(String id, Integer n) {
            this.id = id;
            this.n = n;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public Integer getN() {
            return n;
        }

        public void setN(Integer n) {
            this.n = n;
        }

        @Override
        public int compareTo(Object o) {
            return n.compareTo(((Order) o).getN());
        }
    }

}
