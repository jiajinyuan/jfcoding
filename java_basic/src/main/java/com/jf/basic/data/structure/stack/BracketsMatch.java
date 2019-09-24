package com.jf.basic.data.structure.stack;

import java.util.Stack;

/**
 * 栈的经典应用：括号匹配
 *
 * @author Junfeng
 */
public class BracketsMatch {

    public static void main(String[] arg) {
        BracketsMatch bracketsMatch = new BracketsMatch();
        String inputStr1 = "{[(2+4)+(3-5)/9]*4+1}*{[(2-4)+(3-5)*9]*(4+1)}";
        String inputStr2 = "{[(2+4)+(3-5)/9]*4+1}*{[(2-4)+(3-5)*9]*(4+1}";
        String inputStr3 = "{[(2+4)+(3-5)/9]*4+1}*[(2-4)+(3-5)*9]*(4+1)}";

        System.out.println(bracketsMatch.bracketsMatch(inputStr1));
        System.out.println(bracketsMatch.bracketsMatch(inputStr2));
        System.out.println(bracketsMatch.bracketsMatch(inputStr3));
    }

    public int bracketsMatch(String str) {
        Stack<Character> stack = new Stack<>();
        for (int i = 0; i < str.length(); i++) {
            char c = str.charAt(i);
            if (isLeftBrackets(c)) {
                stack.push(c);
            } else if (isRightBrackets(c)) {
                if (!stack.empty() && leftMatchRight(stack.peek(), c)) {
                    stack.pop();
                } else {
                    return i;
                }
            }
        }
        if (stack.empty()) {
            return 0;
        } else {
            return -1;
        }
    }

    private boolean leftMatchRight(char left, char right) {
        if (left == '(' && right == ')') {
            return true;
        }
        if (left == '[' && right == ']') {
            return true;
        }
        if (left == '{' && right == '}') {
            return true;
        }
        return false;
    }

    private boolean isLeftBrackets(char c) {
        return c == '(' || c == '[' || c == '{';
    }

    private boolean isRightBrackets(char c) {
        return c == ')' || c == ']' || c == '}';
    }
}
