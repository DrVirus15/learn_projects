package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.EmptyStackException;

public class MyStack<E> {
    private final MyArrayList<E> arrayList;

    public MyStack() {
        this.arrayList = new MyArrayList<>();
    }

    public MyStack(int initialCapacity) {
        this.arrayList = new MyArrayList<>(initialCapacity);
    }

    public E push(E e) {
        if (arrayList.add(e)) {
            return e;
        } else {
            return null;
        }
    }

    public E pop() {
        if (arrayList.isEmpty()) {
            throw new EmptyStackException();
        }
        E element = arrayList.getLast();
        arrayList.removeLast();
        return element;
    }

    public E peek() {
        if (arrayList.isEmpty()) {
            throw new EmptyStackException();
        }
        return arrayList.getLast();
    }

    public boolean empty() {
        return arrayList.isEmpty();
    }

    public int search(Object o) {
        int lastIndex = arrayList.lastIndexOf(o);
        return lastIndex != -1 ? arrayList.size() - lastIndex : lastIndex;
    }
}
