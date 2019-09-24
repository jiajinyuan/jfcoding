package com.jf.basic.data.structure;

/**
 * 数据结构之： 数组
 * <p>
 * 再内存中连续存储多个元素的结构，在内存中的分配也是连续的
 * <p>
 * 优点：
 * 1、按照索引查询元素速度快<br>
 * 2、按照索引遍历数组方便<br>
 * <p>
 * 缺点：
 * 1、数组的大小固定后就无法扩容了<br>
 * 2、数组只能存储一种类型的数据<br>
 * 3、添加，删除的操作慢，因为要移动其他的元素。<br>
 * <p>
 * 适用场景：
 * 频繁查询，对存储空间要求不大，很少增加和删除的情况。
 *
 * @author Junfeng
 */
public class DataStructureArray {

    public static void main(String[] arg) {
        int[] data = new int[10];
        data[0] = 1;
    }

}
