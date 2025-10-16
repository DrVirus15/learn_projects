package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.LinkedList;


public class TestLinkedList {
    public static void main(String[] args) {
        LinkedList<Integer> linkedList = new LinkedList<>();
        linkedList.add(2);
        linkedList.add(3);
        int data = linkedList.peek();
        linkedList.addFirst(0);
        linkedList.add(1,   1);

        System.out.println(linkedList + ", data:" + data);

        MyLinkedList<Integer> myLinkedList = new MyLinkedList<>();
        myLinkedList.addFirst(22);
        myLinkedList.addFirst(10);
        myLinkedList.add( 30);
        myLinkedList.add( 40);
        System.out.println("size: " + myLinkedList.size());
        myLinkedList.add(2, 5);

        System.out.println("get: " + myLinkedList.get(1));
        String out = myLinkedList.toString();
        System.out.println("out: " + out);
    }
}
