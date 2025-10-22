package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

public class MyLinkedList<E> implements Iterable<E>, MyList<E> {

    private class Link {

        private E value;

        private Link next;
        private Link previous;

        public Link(E data) {
            value = data;
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

    @Override
    public boolean contains(Object o) {
        if (isEmpty()) {
            return false;
        }
        Link current = first;
        while (current != null) {
            if (Objects.equals(current.value, o)) {
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public E peek() {
        return first.value;
    }

    public E set(int index, E e) {
        if (index < 0 || index >= nElems) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nElems);
        }
        E oldElement;
        if (index == 0) {
            oldElement = first.value;
            first.value = e;
        } else if (index == nElems - 1) {
            oldElement = last.value;
            last.value = e;
        } else {
            Link current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            oldElement = current.value;
            current.value = e;
        }
        return oldElement;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= nElems) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nElems);
        }
        E oldElement;
        Link current;
        if (nElems == 1) {
            oldElement = first.value;
            first = null;
            last = null;
        } else if (index == 0) {
            oldElement = first.value;
            first = first.next;
            first.previous = null;
        } else if (index == nElems - 1) {
            oldElement = last.value;
            last = last.previous;
            last.next = null;
        } else {
            current = first;
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
            oldElement = current.value;
            current.previous.next = current.next;
            current.next.previous = current.previous;
        }
        nElems--;
        return oldElement;
    }

    @Override
    public boolean remove(Object o) {
        if (isEmpty()) {
            return false;
        }
        if (nElems == 1 && Objects.equals(first.value, o)) {
            first = null;
            last = null;
        } else if (Objects.equals(first.value, o)) {
            first = first.next;
            first.previous = null;
        } else if (Objects.equals(last.value, o)) {
            last = last.previous;
            last.next = null;
        } else {
            Link current = first;
            while (!Objects.equals(current.value, o)) {
                current = current.next;
                if (current == null) {
                    return false;
                }
            }
            current.next.previous = current.previous;
            current.previous.next = current.next;
        }
        nElems--;
        return true;
    }

    public void add(int index, E e) {
        if (index < 0 || index > nElems) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nElems);
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

    public E get(int index) { // TODO можна покращити цей метод, пошук з кінця
        if (index < 0 || index >= nElems) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nElems);
        }
        if (index == 0) {
            return first.value;
        }
        Link current = first;
        if (index < nElems / 2) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            for (int i = nElems; i > index; i--) {
                current = current.previous;
            }
        }
        return current.value;
    }

    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Link current = first;
        return current.value;
    }

    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Link current = last;
        return current.value;
    }

    public void addLast(E e) {
        add(e);
    }

    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Link current = last;
        last = current.previous;
        if (nElems == 1) {
            first = null;
        } else {
            current.previous.next = null;
        }
        nElems--;
        return current.value;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        if (c.isEmpty()) {
            return false;
        }
        for (E element : c) {
            add(element);
        }
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > nElems) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nElems);
        }
        if (c.isEmpty()) {
            return false;
        }
        if (isEmpty() || index == nElems) {
            for (E element : c) {
                add(element);
            }
        } else if (index == 0) {
            connectList(null, first, c);
        } else {
            Link nextNode = first;
            for (int i = 0; i < index; i++) {
                nextNode = nextNode.next;
            }
            connectList(nextNode.previous, nextNode, c);
        }
        return true;
    }

    private void connectList(Link prevNode, Link nextNode, Collection<? extends E> c) {
        for (E element : c) {
            Link newLink = new Link(element);
            if (prevNode == null) {
                first = newLink;
                first.next = nextNode;
                nextNode.previous = first;
                prevNode = first;
            } else {
                nextNode.previous = newLink;
                newLink.next = nextNode;
                prevNode.next = newLink;
                newLink.previous = prevNode;
                prevNode = newLink;
            }
            nElems++;
        }
    }

    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Link current = first;
        if (nElems == 1) {
            first = null;
            last = null;
        } else {
            first = current.next;
            first.previous = null;
        }
        current.next = null;
        nElems--;
        return current.value;
    }

    public String toString() {
        Link current = first;
        StringBuilder stringList = new StringBuilder("[");
        while (current != null) {
            stringList.append(current.value);
            current = current.next;
            if (current != null) {
                stringList.append(", ");
            }
        }
        stringList.append("]");
        return stringList.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int iteratorIndex = 0;
            private int previousIndex = -1;

            @Override
            public boolean hasNext() {
                return iteratorIndex < nElems;
            }

            @Override
            public E next() {
                if (iteratorIndex >= nElems) {
                    throw new NoSuchElementException();
                }
                previousIndex = iteratorIndex;
                return get(iteratorIndex++);
            }

            @Override
            public void remove() {
                if (previousIndex == -1) {
                    throw new IllegalStateException();
                }
                MyLinkedList.this.remove(previousIndex);
                iteratorIndex = previousIndex;
                previousIndex = -1;
            }
        };
    }

}
