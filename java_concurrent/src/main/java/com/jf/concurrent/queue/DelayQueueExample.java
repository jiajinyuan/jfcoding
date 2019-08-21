package com.jf.concurrent.queue;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

/**
 * 延迟队列示例, 达到超时时间以后才能被消费.
 *
 * @author Junfeng
 */
public class DelayQueueExample {

    /**
     * 每5秒提交一个任务，任务提交后10秒超时，达到超时时间将被打印 .</p>

     * <p>CreateTime: 2019/3/26.</p>
     *
     * @author Junfeng
     */
    public static void main(String[] arg) throws InterruptedException {

        DelayQueue<DelayTask> queue = new DelayQueue<>();

        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss S");

        Thread consumer = new Thread(() -> {
            out.println(" -[DEBUG]- 开始 处理超时 ");
            while (true) {
                try {
                    DelayTask task = queue.take();
                    out.println(" -[DEBUG]-  Task : " + task.toString() + " out : " + sf.format(new Date(task.timeOut)));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        consumer.start();

        Long l = System.currentTimeMillis();
        out.println(" -[DEBUG]-   " + sf.format(new Date(l)));
        queue.add(new DelayTask(l, l + 10000));
        l += 5000;
        queue.add(new DelayTask(l, l + 10000));
        l += 5000;
        queue.add(new DelayTask(l, l + 10000));
        l += 5000;
        queue.add(new DelayTask(l, l + 10000));
        Thread.sleep(1000000);

    }

    protected static class DelayTask implements Delayed {

        private Long create;

        private Long timeOut;

        public DelayTask(Long create, Long timeOut) {
            this.create = create;
            this.timeOut = timeOut;
        }

        public Long getCreate() {
            return create;
        }

        public void setCreate(Long create) {
            this.create = create;
        }

        public Long getTimeOut() {
            return timeOut;
        }

        public void setTimeOut(Long timeOut) {
            this.timeOut = timeOut;
        }

        @Override
        public long getDelay(TimeUnit unit) {
            return unit.convert(timeOut - System.currentTimeMillis(), TimeUnit.MILLISECONDS);
        }

        @Override
        public int compareTo(Delayed o) {
            return timeOut.compareTo(((DelayTask) o).getTimeOut());
        }

    }
}
