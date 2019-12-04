package com.jf.basic.jdk8.stream;

import java.util.stream.IntStream;

import static java.lang.System.out;

/**
 * 并行Stream 演示
 *
 * @author Junfeng
 */
public class ParallelStream {

    public static void main(String[] arg) {

//        parallelStrem();
        out.println(" --- * --- * --- * --- * --- * --- * --- ");
        parallelStremStored();
        out.println(" --- * --- * --- * --- * --- * --- * --- ");

    }

    /**
     * 并行Stream
     */
    private static void parallelStrem() {
        IntStream.range(0, 6).parallel().mapToObj(i -> {
            out.printf(" -[mapToObj]-  %s , [%s]\n", i, Thread.currentThread().getName());
            return "A" + i;
        }).forEach(x -> {
            out.printf(" -[forEach]-  %s, [%s]\n", x, Thread.currentThread().getName());
        });
    }

    /**
     * 并行运行中排序
     */
    private static void parallelStremStored() {
        IntStream.range(0, 6).parallel().mapToObj(i -> {
            out.printf(" -[mapToObj]-  %s , [%s]\n", i, Thread.currentThread().getName());
            return "A" + i;
        }).sorted((o1, o2) -> {
            out.printf(" -[sorted]-  o1=%s, o2=%s, [%s]\n ", o1, o2, Thread.currentThread().getName());
            return o1.compareTo(o2);
        }).filter(x -> {
            out.printf(" -[filter]-  %s , [%s]\n", x, Thread.currentThread().getName());
            return true;
        }).forEach(x -> {
            out.printf(" -[forEach]-  %s, [%s]\n", x, Thread.currentThread().getName());
        });
    }
}
