package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.*;

/**
 * A class that
 *
 * @param <E>
 */
public class MyArrayList<E> implements Iterable<E>, MyList<E> {
    private final int DEFAULT_CAPACITY = 10;
    private int capacity = DEFAULT_CAPACITY;
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
        return nElems == 0;
    }

    @SuppressWarnings("unchecked")
    private void updateCapacity(int minCapacity) {
        int newCapacity = capacity + capacity / 2;
        if (minCapacity > newCapacity) {
            newCapacity = minCapacity;
        }
        Object[] newList = new Object[newCapacity];
        System.arraycopy(list, 0, newList, 0, nElems);
        list = (E[]) newList;
    }

    public void ensureCapacity(int minCapacity) {
        if (capacity < minCapacity) {
            updateCapacity(minCapacity);
        }
    }

    public void add(int index, E element) {
        if (index < 0 || index > nElems) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nElems);
        }
        if (nElems == capacity) {
            updateCapacity(0);
        }
        for (int j = nElems - 1; j >= index; j--) {
            list[j + 1] = list[j];
        }
        list[index] = element;
        nElems++;
    }

    public void add(E element) {
        if (nElems == capacity) {
            updateCapacity(0);
        }
        list[nElems] = element;
        nElems++;
    }

    public void addFirst(E element) {
        if (nElems == capacity) {
            updateCapacity(0);
        }
        int size = nElems;
        for (int i = size; i > 0; i--) {
            list[i] = list[i - 1];
        }
        list[0] = element;
        nElems++;
    }


    public void addLast(E element) {
        add(element);
    }

    public boolean addAll(Collection<? extends E> c) {
        int sizeToAdd = c.size();
        if (c.isEmpty()) {
            return false;
        }
        updateCapacity(nElems + sizeToAdd);
        int index = nElems;
        for (E element : c) {
            list[index++] = element;
        }
        nElems += sizeToAdd;
        return true;
    }


    public boolean addAll(int index, Collection<? extends E> c) {
        if (index < 0 || index > nElems) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nElems);
        }
        int sizeToAdd = c.size();
        if (sizeToAdd == 0) {
            return false;
        }
        updateCapacity(nElems + sizeToAdd);
        int startIndex = index + sizeToAdd;
        int lastElementOfList = nElems - 1;
        for (int i = nElems + sizeToAdd - 1; i >= startIndex; i--) {
            list[i] = list[lastElementOfList--];
        }
        for (E element : c) {
            list[index++] = element;
        }
        nElems += sizeToAdd;
        return true;
    }

    public E get(int index) {
        if (index < 0 || index >= nElems) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nElems);
        }
        return list[index];
    }

    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list[nElems - 1];
    }

    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list[0];
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
            if (Objects.equals(list[i], o)) {
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
        if(isEmpty()){
            return false;
        }
        int i;
        for (i = 0; i < nElems; i++) {
            if (Objects.equals(list[i], elem)) {
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

    public E removeLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        E temp = list[nElems - 1];
        list[nElems - 1] = null;
        nElems--;
        return temp;
    }

    public E removeFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        E temp = list[0];
        for (int i = 0; i < nElems - 1; i++) {
            list[i] = list[i + 1];
        }
        list[nElems - 1] = null;
        nElems--;
        return temp;
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
                previous = index;
                return list[index++];
            }

            @Override
            public void remove() {
                if (previous == -1) {
                    throw new IllegalStateException();
                }
                MyArrayList.this.remove(previous);


                /*
                for (int i = previous; i < nElems - 1; i++) {
                    list[i] = list[i + 1];
                }
                nElems--;
                list[nElems] = null;*/
                index = previous;
                previous = -1;
            }
        };
    }
}