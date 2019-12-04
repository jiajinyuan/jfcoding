package com.jf.basic.jdk8.stream;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static java.lang.System.out;

/**
 * flatMap 操作示例
 * <p>
 * map 将 Stream 中的对象转成另外一种类型的对象
 * <p>
 * flatMap  将 Stream 中的对象转成任意种类型
 *
 * @author Junfeng
 */
public class StreamFlatMapDemo {
    private static List<Foo> foos = new ArrayList<>();

    public static void main(String[] arg) {
        // create foos
        IntStream.range(1, 4).forEach(i -> foos.add(new Foo("Foo" + i)));

        // create bars
        foos.forEach(f -> IntStream.range(1, 4).forEach(i -> f.bars.add(new Bar("Bar" + i + " : " + f.name))));


        // 将所有foo集合中的bar集合，抽出来，抽成一个list
        foos.stream().flatMap(foo -> foo.getBars().stream()).forEach(bar -> out.println(bar.getName()));

        out.println(" --- * --- * --- * --- * --- * --- * --- ");

        // 以上操作全部使用Stream
        IntStream.range(1, 4).mapToObj(i -> new Foo("foo" + i))
                .peek(f -> IntStream.range(1, 4).mapToObj(i -> new Bar("bar" + i + " = " + f.getName())).forEach(f.getBars()::add))
                .flatMap(foo -> foo.getBars().stream())
                .forEach(bar -> out.println(bar.getName()));




    }

    /**
     * Stream 演示类
     *
     * @author Junfeng
     */
    @RequiredArgsConstructor
    @ToString
    @Data
    public static class Foo {
        @NonNull
        private String name;
        private List<Bar> bars = new ArrayList<>();
    }

    /**
     * Stream 演示类2
     *
     * @author Junfeng
     */
    @AllArgsConstructor
    @ToString
    @Data
    public static class Bar {
        private String name;
    }
}
