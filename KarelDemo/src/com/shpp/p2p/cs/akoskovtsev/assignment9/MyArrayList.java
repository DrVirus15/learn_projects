package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.*;

public class MyArrayList<E> implements Iterable<E>, MyList<E> {
    private final int DEFAULT_CAPACITY = 10;
    private int capacity = DEFAULT_CAPACITY;
    private int size;
    private E[] list;


    @SuppressWarnings("unchecked")
    public MyArrayList(int initialCapacity) {
        capacity = initialCapacity;
        list = (E[]) new Object[capacity];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    public MyArrayList() {
        list = (E[]) new Object[capacity];
        size = 0;
    }

    @SuppressWarnings("unchecked")
    private void updateCapacity(int minCapacity) {
        int newCapacity = capacity + capacity / 2;
        if (minCapacity > newCapacity) {
            newCapacity = minCapacity;
        }
        Object[] newList = new Object[newCapacity];
        System.arraycopy(list, 0, newList, 0, size);
        list = (E[]) newList;
    }

    public void ensureCapacity(int minCapacity) {
        if (capacity < minCapacity) {
            updateCapacity(minCapacity);
        }
    }

    @Override
    public boolean add(E element) {
        if (size == capacity) {
            updateCapacity(0);
        }
        list[size] = element;
        size++;
        return true;
    }

    @Override
    public void add(int index, E element) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        if (size == capacity) {
            updateCapacity(0);
        }
        for (int j = size - 1; j >= index; j--) {
            list[j + 1] = list[j];
        }
        list[index] = element;
        size++;
    }

    @Override
    public void addFirst(E element) {
        if (size == capacity) {
            updateCapacity(0);
        }
        int size = this.size;
        for (int i = size; i > 0; i--) {
            list[i] = list[i - 1];
        }
        list[0] = element;
        this.size++;
    }

    @Override
    public void addLast(E element) {
        add(element);
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        int sizeToAdd = c.size();
        if (c.isEmpty()) {
            return false;
        }
        updateCapacity(size + sizeToAdd);
        int index = size;
        for (E element : c) {
            list[index++] = element;
        }
        size += sizeToAdd;
        return true;
    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        int sizeToAdd = c.size();
        if (sizeToAdd == 0) {
            return false;
        }
        updateCapacity(size + sizeToAdd);
        int startIndex = index + sizeToAdd;
        int lastElementOfList = size - 1;
        for (int i = size + sizeToAdd - 1; i >= startIndex; i--) {
            list[i] = list[lastElementOfList--];
        }
        for (E element : c) {
            list[index++] = element;
        }
        size += sizeToAdd;
        return true;
    }

    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        E oldElement = list[index];
        list[index] = element;
        return oldElement;
    }

    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return list[index];
    }

    @Override
    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list[size - 1];
    }

    @Override
    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list[0];
    }

    @Override
    public E remove(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        E elem = list[index];
        for (int i = index; i < size - 1; i++) {
            list[i] = list[i + 1];
        }
        size--;
        list[size] = null;
        return elem;
    }

    @Override
    public boolean remove(Object elem) {
        if (isEmpty()) {
            return false;
        }
        int i;
        for (i = 0; i < size; i++) {
            if (Objects.equals(list[i], elem)) {
                break;
            }
        }
        if (i == size) {
            return false;
        } else {
            for (int j = i; j < size - 1; j++) {
                list[j] = list[j + 1];
            }
            size--;
            list[size] = null;
            return true;
        }
    }

    @Override
    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        E temp = list[size - 1];
        list[size - 1] = null;
        size--;
        return temp;
    }

    @Override
    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        E temp = list[0];
        for (int i = 0; i < size - 1; i++) {
            list[i] = list[i + 1];
        }
        list[size - 1] = null;
        size--;
        return temp;
    }

    public int indexOf(Object o) {
        int index = -1;
        if (isEmpty()) {
            return index;
        }
        for (int i = 0; i < size; i++) {
            if (Objects.equals(list[i], o)) {
                return i;
            }
        }
        return index;
    }

    public int lastIndexOf(Object o) {
        int index = -1;
        if (isEmpty()) {
            return index;
        }
        for (int i = size - 1; i >= 0; i--) {
            if (Objects.equals(list[i], o)) {
                return i;
            }
        }
        return index;
    }

    @Override
    public void clear() {
        Arrays.fill(list, null);
        size = 0;
    }

    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(list[i], o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    @Override
    public String toString() {
        StringBuilder stringList = new StringBuilder("[");
        for (int i = 0; i < size; i++) {
            if (i != size - 1) {
                stringList.append(list[i]).append(", ");
            } else {
                stringList.append(list[i]);
            }
        }
        stringList.append("]");
        return stringList.toString();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int index = 0;
            private int previous = -1;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                if (index >= size) {
                    throw new NoSuchElementException();
                }
                previous = index;
                return list[index++];
            }

            @Override
            public void remove() {
                if (previous == -1) {
                    throw new IllegalStateException();
                }
                MyArrayList.this.remove(previous);
                index = previous;
                previous = -1;
            }
        };
    }
}