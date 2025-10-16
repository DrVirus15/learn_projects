package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * A class that
 *
 * @param <E>
 */
public class MyArrayList<E> implements Iterable<E> {
    private int capacity = 10;
    private int nElems;
    private E[] list;


    @SuppressWarnings("unchecked")
    public MyArrayList(int initialCapacity) {
        capacity = initialCapacity;
        list = (E[]) new Object[capacity];
        nElems = 0;
    }

    @SuppressWarnings("unchecked")
    public MyArrayList() {
        list = (E[]) new Object[capacity];
        nElems = 0;
    }

    public boolean isEmpty() {
        return (nElems == 0);
    }

    @SuppressWarnings("unchecked")
    public void updateCapacity() {
        capacity = capacity + capacity / 2;
        Object[] newList = new Object[capacity];
        System.arraycopy(list, 0, newList, 0, list.length);
        list = (E[]) newList;
    }

    public void add(int index, E element) {
        if (index < 0 || index > nElems) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nElems);
        }
        if (nElems == capacity) {
            updateCapacity();
        }
        for (int j = nElems - 1; j >= index; j--) {
            list[j + 1] = list[j];
        }
        list[index] = element;
        nElems++;
    }

    public boolean add(E element) {
        if (nElems == capacity) {
            updateCapacity();
        }
        list[nElems] = element;
        nElems++;
        return true;
    }

    public E get(int index) {
        if (index < 0 || index >= nElems) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nElems);
        }
        return list[index];
    }


    public E set(int index, E element) {
        if (index < 0 || index >= nElems) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nElems);
        }
        E oldElement = list[index];
        list[index] = element;
        return oldElement;
    }

    /**
     * Returns the size of list.
     *
     * @return - number of elements.
     */
    public int size() {
        return nElems;
    }

    public boolean contains(Object o) {
        for (int i = 0; i < nElems; i++) {
            if (list[i].equals(o)) {
                return true;
            }
        }
        return false;
    }

    public E remove(int index) {
        if (index < 0 || index >= nElems) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nElems);
        }
        E elem = list[index];
        for (int i = index; i < nElems - 1; i++) {
            list[i] = list[i + 1];
        }
        nElems--;
        list[nElems] = null;
        return elem;
    }

    public boolean remove(Object elem) {
        int i;
        for (i = 0; i < nElems; i++) {
            if (list[i].equals(elem)) {
                break;
            }
        }
        if (i == nElems) {
            return false;
        } else {
            for (int j = i; j < nElems - 1; j++) {
                list[j] = list[j + 1];
            }
            nElems--;
            list[nElems] = null;
            return true;
        }
    }

    public void clear() {
        Arrays.fill(list, null);
        nElems = 0;
    }

    public String toString() {
        StringBuilder stringList = new StringBuilder("[");
        for (int i = 0; i < nElems; i++) {
            if (i != nElems - 1) {
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
                return index < nElems;
            }

            @Override
            public E next() {
                if (index >= nElems) {
                    throw new NoSuchElementException();
                }
                E elem = list[index];
                previous = index;
                index++;
                return elem;
            }

            @Override
            public void remove() {
                if (previous == -1) {
                    throw new IllegalStateException();
                }
                list[previous] = null;
                for (int i = previous; i < nElems-1; i++) {
                    list[i] = list[i+1];
                }
                nElems--;
                index = previous;
                previous = -1;
            }
        };
    }
}