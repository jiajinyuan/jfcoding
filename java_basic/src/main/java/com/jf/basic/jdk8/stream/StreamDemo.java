package com.jf.basic.jdk8.stream;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.System.out;

/**
 * Description: JDK 8 Stream api demo
 *
 * @author SF2121
 */
public class StreamDemo {

    private static List<Person> persons = Arrays.asList(
            new Person("Max", 18),
            new Person("Peter", 23),
            new Person("Pamela", 23),
            new Person("David", 12));

    public static void main(String[] arg) {
        tCollect();
//        tCoolectGroupBy();
//        tCoolectAverage();
//        tCoolectSum();
//        tCoolectJoining();
    }

    /**
     * 将Stream 转换为集合
     */
    private static void tCollect() {
        List<Person> collect = persons.stream().filter(x -> x.getAge() > 18).collect(Collectors.toList());
        for (Person person : collect) {
            out.println(" -[DEBUG]-  list " + person.toString());
        }
        Set<Person> set = persons.stream().filter(p -> p.getAge() > 18).collect(Collectors.toSet());
        for (Person person : set) {
            out.println(" -[DEBUG]-   set " + person.toString());
        }

        // (a, b) -> a + ";" + b)  表示 key出现重复时对两个 value的处理逻辑
        Map<Integer, String> map1 = persons.stream().collect(Collectors.toMap(Person::getAge, Person::getName, (a, b) -> a + ";" + b));
        for (Map.Entry<Integer, String> entry : map1.entrySet()) {
            out.println(" -[DEBUG]-   k2=" + entry.getKey() + "; v2=" + entry.getValue());
        }

        // 会出异常，因为有两个年龄18， 导致key重复了，
        Map<Integer, String> map = persons.stream().collect(Collectors.toMap(Person::getAge, Person::getName));
        for (Map.Entry<Integer, String> entry : map.entrySet()) {
            out.println(" -[DEBUG]-   k=" + entry.getKey() + "; v=" + entry.getValue());
        }
    }

    /**
     * 分组
     */
    private static void tCoolectGroupBy() {
        Map<Integer, List<Person>> map = persons.stream().collect(Collectors.groupingBy(Person::getAge));
        for (Map.Entry<Integer, List<Person>> entry : map.entrySet()) {
            out.println(" -[DEBUG]-   key=" + entry.getKey() + "; " + entry.getValue().toString());
        }
    }

    /**
     * 求平均
     */
    private static void tCoolectAverage() {
        int avg = persons.stream().collect(Collectors.averagingInt(Person::getAge)).intValue();
        out.println(" -[DEBUG]-  avg= " + avg);
    }

    /**
     * 求和
     */
    private static void tCoolectSum() {
        Integer integer = persons.stream().collect(Collectors.summingInt(Person::getAge));
        out.println(" -[DEBUG]-  summingInt: " + integer);
        long sum = persons.stream().collect(Collectors.summarizingInt(Person::getAge)).getSum();
        out.println(" -[DEBUG]-  summarizingInt: " + sum);
        int sum1 = persons.stream().mapToInt(Person::getAge).sum();
        out.println(" -[DEBUG]-  mapToInt: " + sum1);
    }

    /**
     * 元素连接
     */
    private static void tCoolectJoining() {
        String s = persons.stream().map(Person::getName).collect(Collectors.joining(", ", "前缀 ", " 后缀"));
        out.println(" -[DEBUG]-   " + s);
    }


}
