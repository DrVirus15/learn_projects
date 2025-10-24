package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.*;

/**
 * A simple implementation of a dynamic array-based list.
 *
 * @param <E> the type of elements in this list
 */
public class MyArrayList<E> implements Iterable<E>, MyList<E> {
    /**
     * The default initial capacity of the list.
     */
    private static final int DEFAULT_CAPACITY = 10;
    /**
     * The current capacity of the list.
     */
    private int capacity = DEFAULT_CAPACITY;
    /**
     * The current size of the list.
     */
    private int size;
    /**
     * The array to store the list elements.
     */
    private E[] list;

    /**
     * Constructs an empty list with the specified initial capacity.
     *
     * @param initialCapacity the initial capacity of the list
     */
    @SuppressWarnings("unchecked")
    public MyArrayList(int initialCapacity) {
        capacity = initialCapacity;
        list = (E[]) new Object[capacity];
        size = 0;
    }

    /**
     * Constructs an empty list with the default initial capacity.
     */
    @SuppressWarnings("unchecked")
    public MyArrayList() {
        list = (E[]) new Object[capacity];
        size = 0;
    }

    /**
     * Updates the capacity of the list to accommodate at least the specified minimum capacity.
     *
     * @param minCapacity the minimum capacity required
     */
    @SuppressWarnings("unchecked") //TODO: capacity warning
    private void updateCapacity(int minCapacity) {
        int newCapacity = capacity + capacity / 2;
        if (minCapacity > newCapacity) {
            newCapacity = minCapacity;
        }
        Object[] newList = new Object[newCapacity];
        System.arraycopy(list, 0, newList, 0, size);
        list = (E[]) newList;
    }

    /**
     * Adds the specified element to the end of the list.
     * @param element the element to be added
     * @return true if the element was added successfully
     */
    @Override
    public boolean add(E element) {
        if (size == capacity) {
            updateCapacity(0);
        }
        list[size] = element;
        size++;
        return true;
    }

    /**
     * Adds the specified element at the specified index in the list.
     * @param index the index at which the element is to be added
     * @param element the element to be added
     */
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

    /**
     * Adds the specified element at the beginning of the list.
     * @param element the element to be added
     */
    @Override
    public void addFirst(E element) {
        add(0, element);
    }

    /**
     * Adds the specified element at the end of the list.
     * @param element the element to be added
     */
    @Override
    public void addLast(E element) {
        add(element);
    }

    /**
     * Adds all elements from the specified collection to the end of the list.
     * @param c the collection containing elements to be added
     * @return true if the list changed as a result of the call
     */
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

    /**
     * Adds all elements from the specified collection starting at the specified index in the list.
     * @param index the index at which to insert the first element from the specified collection
     * @param c the collection containing elements to be added
     * @return true if the list changed as a result of the call
     */
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

    /**
     * Replaces the element at the specified position in the list with the specified element.
     * @param index the index of the element to replace
     * @param element the element to be stored at the specified position
     * @return the element previously at the specified position
     */
    @Override
    public E set(int index, E element) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        E oldElement = list[index];
        list[index] = element;
        return oldElement;
    }

    /**
     * Returns the element at the specified position in the list.
     * @param index the index of the element to return
     * @return the element at the specified position in the list
     */
    @Override
    public E get(int index) {
        if (index < 0 || index >= size) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + size);
        }
        return list[index];
    }

    /**
     * Returns the last element in the list.
     * @return the last element in the list
     */
    @Override
    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list[size - 1];
    }

    /**
     * Returns the first element in the list.
     * @return the first element in the list
     */
    @Override
    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return list[0];
    }

    /**
     * Removes the element at the specified position in the list.
     * @param index the index of the element to be removed
     * @return the element that was removed from the list
     */
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

    /**
     * Removes the first occurrence of the specified element from the list, if it is present.
     * @param elem the element to be removed from the list, if present
     * @return true if the list contained the specified element, false otherwise
     */
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

    /**
     * Removes and returns the last element from the list.
     * @return the last element from the list
     */
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

    /**
     * Removes and returns the first element from the list.
     * @return the first element from the list
     */
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

    /**
     * Returns the index of the first occurrence of the specified element in the list,
     * or -1 if the list does not contain the element.
     * @param o the element to search for
     * @return the index of the first occurrence of the specified element in the list,
     * or -1 if not found
     */
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

    /**
     * Returns the index of the last occurrence of the specified element in the list,
     * or -1 if the list does not contain the element.
     * @param o the element to search for
     * @return the index of the last occurrence of the specified element in the list,
     * or -1 if not found
     */
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

    /**
     * Removes all elements from the list.
     */
    @Override
    public void clear() {
        Arrays.fill(list, null);
        size = 0;
    }

    /**
     * Checks if the list contains the specified element.
     * @param o the element to search for
     * @return true if the list contains the specified element, false otherwise
     */
    @Override
    public boolean contains(Object o) {
        for (int i = 0; i < size; i++) {
            if (Objects.equals(list[i], o)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Returns the number of elements in the list.
     * @return the number of elements in the list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if the list is empty.
     * @return true if the list is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return size == 0;
    }

    /**
     * Returns a string representation of the list.
     * @return a string representation of the list
     */
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

    /**
     * Returns an iterator over the elements in the list.
     * @return an iterator over the elements in the list
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int index = 0;
            private int previous = -1;

            /**
             * Checks if there are more elements to iterate over.
             * @return true if there are more elements, false otherwise
             */
            @Override
            public boolean hasNext() {
                return index < size;
            }

            /**
             * Returns the next element in the iteration.
             * @return the next element in the iteration
             */
            @Override
            public E next() {
                if (index >= size) {
                    throw new NoSuchElementException();
                }
                previous = index;
                return list[index++];
            }

            /**
             * Removes the last element returned by this iterator.
             */
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