package com.jf.basic.data.structure.stack;

import java.util.Stack;

import static java.lang.System.out;

/**
 * 数据结构之：栈
 * <p>
 * Stack的基本使用：<br>
 * 初始化：Stack stack=new Stack<br>
 * 判断是否为空：stack.empty()<br>
 * 取栈顶值（不出栈）：stack.peek()<br>
 * 进栈：stack.push(Object);<br>
 * 出栈：stack.pop();<br>
 *
 * @author Junfeng
 */
public class DataStructureStack {
    public static void main(String[] arg) {
        Stack<Integer> stack = new Stack<>();
        out.println(" -[DEBUG]-  入栈： " + stack.push(1));
        out.println(" -[DEBUG]-  取栈顶值： " + stack.peek());
        out.println(" -[DEBUG]-  入栈： " + stack.push(1));
        out.println(" -[DEBUG]-  出栈 " + stack.pop());
        out.println(" -[DEBUG]-  空判断 " + stack.empty());
    }
}
