package com.jf.orther.time;

import java.util.concurrent.TimeUnit;

import static java.lang.System.out;

/**
 * TimeUnit.
 *
 * @author Junfeng
 */
public class TimeUnitUse {

    public static void main(String[] arg) {
        use1();
    }

    /**
     * 将给定时间单位的时间，转换为目标单位时间，如 1分钟 转换为 60 秒
     *
     * @author Junfeng
     */
    public static void use1() {
        TimeUnit timeUnit = TimeUnit.SECONDS;
        long convert = timeUnit.convert(1, TimeUnit.MINUTES);
        out.println(" -[DEBUG]-   " + convert);
    }
}
