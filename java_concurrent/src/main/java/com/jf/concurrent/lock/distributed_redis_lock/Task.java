package com.jf.concurrent.lock.distributed_redis_lock;

import lombok.Builder;
import lombok.Data;
import org.redisson.Redisson;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;

import static java.lang.System.out;

/**
 * Description: TODO
 *
 * @author SF2121
 */
@Data
@Builder
public class Task implements Runnable {

    private String name;

    @Override
    public void run() {
        Config c = new Config();
        // 哨兵模式
//        c.useSentinelServers().setMasterName("redis-master").setScanInterval(2000)
//                .addSentinelAddress("redis://10.0.8.170:26379")
//                .setDatabase(0);

        // 集群模式
//        c.useClusterServers().setScanInterval(2000).addNodeAddress("redis://10.0.8.170:6379");

        // 单机模式
        c.useSingleServer().setAddress("redis://10.0.8.170:6379").setDatabase(0);
        c.setCodec(new org.redisson.client.codec.StringCodec());
        RedissonClient client = Redisson.create(c);

        RLock lock = client.getLock("jfLock");
        try {
            lock.lock();
            out.println(" --[INFO]--  " + name + " 抢到锁，倒数 5 秒后解锁:");
            for (int i = 1; i <= 5; i++) {
                out.print(" " + i);
                Thread.sleep(1000);
            }
            out.println();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            out.println(" --[INFO]--  " + name + " 释放锁");
            lock.unlock();
        }

        client.shutdown();
    }
}
