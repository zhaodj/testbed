package io.github.zhaodj.algorithm;

import java.util.LinkedList;
import java.util.Queue;

public class BinaryTree {

    public static <T> void print(Node<T> root){
        Node<T> split = new Node<>();
        Queue<Node<T>> queue = new LinkedList<>();
        queue.offer(root);
        queue.offer(split);
        boolean isLast = true;
        while(!queue.isEmpty()){
            Node<T> node = queue.poll();
            if(node != split){
                System.out.print(node.getValue().toString() + " ");
                if(node.getLeft() != null){
                    queue.offer(node.getLeft());
                    isLast = false;
                }
                if(node.getRight() != null){
                    queue.offer(node.getRight());
                    isLast = false;
                }
            }else if(!isLast){
                System.out.println();
                queue.offer(split);
                isLast = true;
            }
        }
    }

    public static void main(String[] args) {
        Node<Integer> tree = new Node<>(1,
                new Node<>(2,
                        new Node<>(4, null, null),
                        new Node<>(5, null, null)),
                new Node<>(3,
                        new Node<>(6, null, null),
                        null)
        );
        print(tree);
    }

    public static class Node<T> {
        private T value;
        private Node<T> left;
        private Node<T> right;

        public Node() {
        }

        public Node(T value, Node<T> left, Node<T> right) {
            this.value = value;
            this.left = left;
            this.right = right;
        }

        public T getValue() {
            return value;
        }

        public void setValue(T value) {
            this.value = value;
        }

        public Node<T> getLeft() {
            return left;
        }

        public void setLeft(Node<T> left) {
            this.left = left;
        }

        public Node<T> getRight() {
            return right;
        }

        public void setRight(Node<T> right) {
            this.right = right;
        }

    }

}
