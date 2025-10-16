package com.shpp.p2p.cs.akoskovtsev.assignment9;

public class MyLinkedList<E> {

    private class Link {

        private E value;
        private Link next;
        private Link previous;

        @SuppressWarnings("unchecked")
        public Link(Object data) {
            value = (E) data;
        }
    }

    private Link first;
    private Link last;
    private int nElems = 0;

    public MyLinkedList() {
        first = null;
    }

    public void clear() {
        Link current = first;
        for (int i = 0; i < nElems; i++) {
            current.value = null;
            current = current.next;
        }
        first = null;
        last = null;
        nElems = 0;
    }


    public void addFirst(E e) {
        Link newLink = new Link(e);
        if (isEmpty()) {
            last = newLink;
        } else {
            first.previous = newLink;
        }
        newLink.next = first;
        first = newLink;
        nElems++;
    }

    public int size() {
        return nElems;
    }

    public E peek(){
        return first.value;
    }

    public void set(int index, E e) {
        if (index >= nElems) {
            throw new IndexOutOfBoundsException();
        }
        if (index == 0) {
            first.value = e;
        } else if (index == nElems - 1) {
            last.value = e;
        } else {
            Link current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            current.value = e;
        }
    }

    public void add(int index, E e) {
        if (index > nElems) {
            throw new IndexOutOfBoundsException();
        }
        Link current = first;
        Link newLink = new Link(e);
        if (index == 0) {
            if (first == null) {
                last = newLink;
            } else {
                current.previous = newLink;
                newLink.next = current;
            }
            first = newLink;
        } else if (index == nElems) {
            last.next = newLink;
            newLink.previous = last;
            last = newLink;
        } else {
            for (int i = 1; i < index; i++) {
                current = current.next;
            }
            newLink.previous = current;
            newLink.next = current.next;
            current.next.previous = newLink;
            current.next = newLink;
        }
        nElems++;
    }

    public void add(E e) {
        Link newLink = new Link(e);
        if (isEmpty()) {
            first = newLink;
        } else {
            last.next = newLink;
            newLink.previous = last;
        }
        last = newLink;
        nElems++;
    }

    public boolean isEmpty() {
        return first == null;
    }

    public E get(int index) {
        Link current = first;
        if (index > 0) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        }
        return current.value;
    }

    public String toString() {
        Link current = first;
        StringBuilder stringList = new StringBuilder("[");
        while (current != null) {
            stringList.append(current.value);
            stringList.append(", ");
            current = current.next;
        }
        stringList.replace(stringList.length() - 2, stringList.length() - 1, "]");
        return stringList.toString();
    }
}
