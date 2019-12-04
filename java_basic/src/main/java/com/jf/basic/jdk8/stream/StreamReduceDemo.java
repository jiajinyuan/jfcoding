package com.jf.basic.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ForkJoinPool;

import static java.lang.System.out;

/**
 * Stream reduce 操作演示
 * <p>
 * Reduce 组合 Stream 中所有的元素，然后产生一个单独的结果
 *
 * @author Junfeng
 */
public class StreamReduceDemo {

    private static List<Person> persons = Arrays.asList(
            new Person("Max", 18),
            new Person("Peter", 23),
            new Person("Pamela", 23),
            new Person("David", 12));


    public static void main(String[] arg) {
        reduce1();
        out.println(" --- * --- * --- * --- * --- * --- * --- ");
        reduce2();
        out.println(" --- * --- * --- * --- * --- * --- * --- ");
        reduce3();
        out.println(" --- * --- * --- * --- * --- * --- * --- ");
        reduce3Parallel();
        out.println(" --- * --- * --- * --- * --- * --- * --- ");
        porkJoinPool();
    }

    /**
     * 只需要一个二元操作函数 BinaryOperator
     */
    private static void reduce1() {
        persons.stream().reduce((p1, p2) -> p1.getAge() > p2.getAge() ? p1 : p2).ifPresent(out::println);
    }

    /**
     * 指定一个特殊的目标值
     */
    private static void reduce2() {
        // 一个目标特殊的值
        Person p = persons.stream().reduce(new Person("我最大", 80),
                (p1, p2) -> p1.getAge() > p2.getAge() ? p1 : p2);

        out.println(" -[reduce2]-  Person: " + p);

        Integer s = persons.stream().reduce(0, (sum, pe) -> sum += pe.getAge(), Integer::sum);

        out.println(" -[reduce2]-  Integer: " + s);
    }

    /**
     * BiFunction 两个参数的函数接口
     */
    private static void reduce3() {
        Person reduce = persons.stream().reduce(new Person("我最大", 80),
                (p1, p2) -> p1.getAge() > p2.getAge() ? p1 : p2, (sum1, sum2) -> sum1);
        out.println(" -[reduce3]-  Person: " + reduce);

        String sa = persons.stream().reduce("我最大", (str, pe) -> {
            out.println(" -[reduce3]-  str= " + str + "； pe= " + pe);
            return str + pe.getName();
        }, (a, b) -> {
            // 不会被执行
            out.println(" -[reduce3]-  a= " + a + "; b= " + b);
            return a + b;
        });
        out.println(" -[reduce3]-  String: " + sa);
    }

    /**
     * 并行运行reduce
     */
    private static void reduce3Parallel() {
        String s = persons.parallelStream().reduce("我最大", (str, pe) -> {
            out.println(" -[reduce3Parallel]-  str= " + str + "； pe= " + pe);
            return str + pe.getName();
        }, (a, b) -> {
            out.println(" -[reduce3Parallel]-  a= " + a + "; b= " + b);
            return a + b;
        });
        out.println(" -[reduce3Parallel]-  String:" + s);
    }

    private static void porkJoinPool() {
        ForkJoinPool pool = ForkJoinPool.commonPool();
        out.println(" -[并行度]-   " + pool.getParallelism());
        // 池中当前线程数，最大值不超过并行度
        out.println(" -[池大小]-   " + pool.getPoolSize());

    }

}
