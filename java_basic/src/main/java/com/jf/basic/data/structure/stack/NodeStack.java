package com.jf.basic.data.structure.stack;

/**
 * 链表栈实现
 *
 * @author Junfeng
 */
public class NodeStack<T> {

    /**
     * 栈顶节点
     */
    private Node<T> top = null;

    /**
     * 判断栈师傅已经空了
     *
     * @return boolean
     */
    public boolean empty() {
        return null == top;
    }

    /**
     * 入栈
     *
     * @param data data
     * @return data
     */
    public T push(T data) {
        Node<T> node = new Node<>(data);
        node.setNext(top);
        top = node;
        return data;
    }

    /**
     * 出栈
     *
     * @return data
     */
    public T pop() {
        if (!empty()) {
            T data = top.getData();
            top = top.getNext();
            return data;
        }
        return null;
    }

    /**
     * 取栈顶
     *
     * @return data
     */
    public T peek() {
        if (!empty()) {
            return top.getData();
        }
        return null;
    }

    /**
     * 链表节点
     *
     * @param <T>
     */
    static class Node<T> {
        /**
         * 数据
         */
        private T data;
        /**
         * 下一节点
         */
        private Node<T> next;

        public Node(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }

        public void setData(T data) {
            this.data = data;
        }

        public Node<T> getNext() {
            return next;
        }

        public void setNext(Node<T> next) {
            this.next = next;
        }
    }
}
