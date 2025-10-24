package com.shpp.p2p.cs.akoskovtsev.assignment9;

public class MyQueue<E> {
    private final MyLinkedList<E> linkedList;

    public MyQueue() {
        this.linkedList = new MyLinkedList<>();
    }

    public boolean offer(E e) {
        return linkedList.add(e);
    }

    public E poll() {
        if (linkedList.isEmpty()) {
            return null;
        }
        return linkedList.removeFirst();
    }

    public E peek() {
        if (linkedList.isEmpty()) {
            return null;
        }
        return linkedList.getFirst();
    }

    public boolean empty() {
        return linkedList.isEmpty();
    }
}
