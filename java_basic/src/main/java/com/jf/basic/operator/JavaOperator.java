package com.jf.basic.operator;

import static java.lang.System.out;

/**
 * java 运算符 <br>
 * 逻辑运算符(&&,||,！) <br>
 * 算数运算符（+, -, *, / ,+=） <br>
 * 位运算符（^,|,&） <br>
 * 其他运算符（三元运算符） <br>
 * <p>
 * 参考 https://cloud.tencent.com/developer/article/1338265
 *
 * @author Junfeng
 */
public class JavaOperator {

    public static void main(String[] arg) {

        // &（按位与）
        operator1();
        // &&（逻辑与）
        operator2();
        // |（按位或）
        operator3();
        // ||（逻辑或）
        operator4();
        // ^（异或运算符）
        operator5();
        // <<（左移运算符）
        operator6();
        // >>（右移运算符）
        operator7();
        // ~（取反运算符）
        operator8();
    }

    /**
     * &（按位与）
     * <p>
     * &按位与的运算规则是将两边的数转换为二进制位，然后运算最终值，运算规则即(两个为真才为真)1&1=1 , 1&0=0 , 0&1=0 , 0&0=0
     * <p>
     * 3的二进制位是0000 0011 ， 5的二进制位是0000 0101 ， 那么就是011 & 101，由按位与运算规则得知，001 & 101等于0000 0001，最终值为1
     * <p>
     * 7的二进制位是0000 0111，那就是111 & 101等于101，也就是0000 0101，故值为5
     */
    private static void operator1() {
        int i = 3 & 5, y = 5 & 7;
        // i = 1, y = 5
        out.println(" -[DEBUG]-   i=" + i + " y=" + y);
    }

    /**
     * &&（逻辑与）
     * <p>
     * &&逻辑与也称为短路逻辑与，先运算&&左边的表达式，一旦为假，后续不管多少表达式，均不再计算，一个为真，再计算右边的表达式，两个为真才为真。
     */
    private static void operator2() {
        String str = null;
        if (str != null && (100 / 0) == 0) {
            out.println(" -[DEBUG]-  后续 (100/0) == 0 不会执行");
            out.println(" -[DEBUG]-  如果执行 (100/0) == 0 会出异常");
        } else {
            out.println(" -[DEBUG]-  必然执行到这里 ");
        }
    }

    /**
     * |（按位或）
     * <p>
     * |按位或和&按位与计算方式都是转换二进制再计算，不同的是运算规则(一个为真即为真)1|0 = 1 , 1|1 = 1 , 0|0 = 0 , 0|1 = 1
     * <p>
     * 6的二进制位0000 0110 , 2的二进制位0000 0010 , 110|010为110，最终值0000 0110，故6|2等于6
     */
    private static void operator3() {
        int i = 6 | 2;
        // i= 6
        out.println(" -[DEBUG]-   i = " + i);
    }

    /**
     * ||（逻辑或）
     * <p>
     * 逻辑或||的运算规则是一个为真即为真，后续不再计算，一个为假再计算右边的表达式。
     */
    private static void operator4() {
        if (5 > 3 || 3 > 5) {
            out.println(" -[DEBUG]-  结果为真 ");
        }
    }

    /**
     * ^（异或运算符）
     * <p>
     * ^异或运算符顾名思义，异就是不同，其运算规则为1^0 = 1 , 1^1 = 0 , 0^1 = 1 , 0^0 = 0
     * <p>
     * 5的二进制位是0000 0101 ， 9的二进制位是0000 1001，也就是0101 ^ 1001,结果为1100 , 00001100的十进制位是12
     */
    private static void operator5() {
        int i = 5 ^ 9;
        // i = 12
        out.println(" -[DEBUG]-   i=" + i);
    }

    /**
     * <<（左移运算符）
     * <p>
     * 5<<2的意思为5的二进制位往左挪两位，右边补0，5的二进制位是0000 0101 ， 就是把有效值101往左挪两位就是0001 0100 ，正数左边第一位补0，负数补1，等于乘于2的n次方，十进制位是20
     */
    private static void operator6() {
        int i = 5 << 2;
        // i = 20
        out.println(" -[DEBUG]-   i=" + i);
    }

    /**
     * >>（右移运算符）
     * <p>
     * 凡位运算符都是把值先转换成二进制再进行后续的处理，5的二进制位是0000 0101，右移两位就是把101左移后为0000 0001，正数左边第一位补0，负数补1，等于除于2的n次方，结果为1
     */
    private static void operator7() {
        int i = 5 >> 2;
        // i = 1
        out.println(" -[DEBUG]-   i=" + i);
    }

    /**
     * ~（取反运算符）
     * <p>
     * 取反就是1为0,0为1,5的二进制位是0000 0101，取反后为1111 1010，值为-6
     */
    private static void operator8() {
        int i = -5;
        out.println(" -[DEBUG]-   i=" + i);
    }
}
