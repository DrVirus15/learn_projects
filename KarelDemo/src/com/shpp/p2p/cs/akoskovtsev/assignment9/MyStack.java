package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.EmptyStackException;

/**
 * A simple implementation of a stack data structure using MyArrayList.
 *
 * @param <E> the type of elements in this stack
 */
public class MyStack<E> {
    /**
     * The underlying list to store stack elements.
     */
    private final MyArrayList<E> arrayList;

    /**
     * Constructs an empty stack with default initial capacity.
     */
    public MyStack() {
        this.arrayList = new MyArrayList<>();
    }

    /**
     * Constructs an empty stack with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the stack
     */
    public MyStack(int initialCapacity) {
        this.arrayList = new MyArrayList<>(initialCapacity);
    }

    /**
     * Pushes an element onto the top of the stack.
     *
     * @param e the element to be pushed onto the stack
     * @return the element that was pushed onto the stack
     */
    public E push(E e) {
        if (arrayList.add(e)) {
            return e;
        } else {
            return null;
        }
    }

    /**
     * Removes and returns the element at the top of the stack.
     *
     * @return the element at the top of the stack
     */
    public E pop() {
        if (arrayList.isEmpty()) {
            throw new EmptyStackException();
        }
        E element = arrayList.getLast();
        arrayList.removeLast();
        return element;
    }

    /**
     * Returns the element at the top of the stack without removing it.
     *
     * @return the element at the top of the stack
     */
    public E peek() {
        if (arrayList.isEmpty()) {
            throw new EmptyStackException();
        }
        return arrayList.getLast();
    }

    /**
     * Checks if the stack is empty.
     *
     * @return true if the stack is empty, false otherwise
     */
    public boolean empty() {
        return arrayList.isEmpty();
    }

    /**
     * Returns the number of elements in the stack.
     *
     * @param o the element to search for
     * @return the 1-based position from the top of the stack where the element is located;
     * -1 if the element is not found.
     */
    public int search(Object o) {
        int lastIndex = arrayList.lastIndexOf(o);
        return lastIndex != -1 ? arrayList.size() - lastIndex : lastIndex;
    }
}
