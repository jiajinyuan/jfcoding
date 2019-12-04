package com.jf.basic.jdk8.stream;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.ToString;

/**
 * Stream 演示类
 *
 * @author Junfeng
 */
@AllArgsConstructor
@ToString
@Data
public class Person {
    /**
     * 名称
     */
    private String name;
    /**
     * 年龄
     */
    private int age;
}