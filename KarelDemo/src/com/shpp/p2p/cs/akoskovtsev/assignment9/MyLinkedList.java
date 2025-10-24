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
    private int size = 0;

    public MyLinkedList() {
        first = null;
    }

    @Override
    public void add(int index, E e) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
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
        } else if (index == size) {
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
        size++;
    }

    @Override
    public boolean add(E e) {
        Link newLink = new Link(e);
        if (isEmpty()) {
            first = newLink;
        } else {
            last.next = newLink;
            newLink.previous = last;
        }
        last = newLink;
        size++;
        return true;
    }

    @Override
    public void addFirst(E e) {
        Link newLink = new Link(e);
        if (isEmpty()) {
            last = newLink;
        } else {
            first.previous = newLink;
        }
        newLink.next = first;
        first = newLink;
        size++;
    }

    @Override
    public void addLast(E e) {
        add(e);
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
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (c.isEmpty()) {
            return false;
        }
        if (isEmpty() || index == size) {
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
            size++;
        }
    }

    @Override
    public E set(int index, E e) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        E oldElement;
        if (index == 0) {
            oldElement = first.value;
            first.value = e;
        } else if (index == size - 1) {
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
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (index == 0) {
            return first.value;
        }
        Link current = first;
        if (index < size / 2) {
            for (int i = 0; i < index; i++) {
                current = current.next;
            }
        } else {
            current = last;
            for (int i = size - 1; i > index; i--) {
                current = current.previous;
            }
        }
        return current.value;
    }

    @Override
    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return first.value;
    }

    @Override
    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Link current = last;
        return current.value;
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        E oldElement;
        Link current;
        if (size == 1) {
            oldElement = first.value;
            first = null;
            last = null;
        } else if (index == 0) {
            oldElement = first.value;
            first = first.next;
            first.previous = null;
        } else if (index == size - 1) {
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
        size--;
        return oldElement;
    }

    @Override
    public boolean remove(Object o) {
        if (isEmpty()) {
            return false;
        }
        if (size == 1 && Objects.equals(first.value, o)) {
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
        size--;
        return true;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Link current = first;
        if (size == 1) {
            first = null;
            last = null;
        } else {
            first = current.next;
            first.previous = null;
        }
        current.next = null;
        size--;
        return current.value;
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Link current = last;
        last = current.previous;
        if (size == 1) {
            first = null;
        } else {
            current.previous.next = null;
        }
        size--;
        return current.value;
    }

    @Override
    public void clear() {
        Link current = first;
        for (int i = 0; i < size; i++) {
            current.value = null;
            current = current.next;
        }
        first = null;
        last = null;
        size = 0;
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

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return first == null;
    }

    @Override
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
            private int index = 0;
            private int previousIndex = -1;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                if (index >= size) {
                    throw new NoSuchElementException();
                }
                previousIndex = index;
                return get(index++);
            }

            @Override
            public void remove() {
                if (previousIndex == -1) {
                    throw new IllegalStateException();
                }
                MyLinkedList.this.remove(previousIndex);
                index = previousIndex;
                previousIndex = -1;
            }
        };
    }

    public Iterator<E> descendingIterator() {
        return new Iterator<>() {
            private int index = size - 1;
            private int previousIndex = -1;

            @Override
            public boolean hasNext() {
                return index >= 0;
            }

            @Override
            public E next() {
                if (index < 0) {
                    throw new NoSuchElementException();
                }
                previousIndex = index;
                return get(index--);
            }

            @Override
            public void remove() {
                if (previousIndex == -1) {
                    throw new IllegalStateException();
                }
                MyLinkedList.this.remove(previousIndex);
                previousIndex = -1;
            }
        };
    }
}