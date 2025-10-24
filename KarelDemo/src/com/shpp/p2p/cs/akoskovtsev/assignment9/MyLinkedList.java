package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.Collection;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

/**
 * A simple implementation of a doubly linked list data structure.
 *
 * @param <E> the type of elements in this list
 */
public class MyLinkedList<E> implements Iterable<E>, MyList<E> {
    /**
     * A private inner class representing a link in the doubly linked list.
     */
    private class Link {

        private E value;

        private Link next;
        private Link previous;

        public Link(E data) {
            value = data;
        }

    }

    /**
     * The first link in the linked list.
     */
    private Link first;
    /**
     * The last link in the linked list.
     */
    private Link last;
    /**
     * The number of elements in the linked list.
     */
    private int size = 0;

    /**
     * Constructs an empty linked list.
     */
    public MyLinkedList() {
        first = null;
    }

    /**
     * Adds an element at the specified index in the linked list.
     *
     * @param index - position to add
     * @param e     - element to add
     */
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

    /**
     * Adds an element to the end of the linked list.
     *
     * @param e - element to add
     * @return true if the element was added successfully
     */
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

    /**
     * Adds an element at the beginning of the linked list.
     *
     * @param e - element to add
     */
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

    /**
     * Adds an element at the end of the linked list.
     *
     * @param e - element to add
     */
    @Override
    public void addLast(E e) {
        add(e);
    }

    /**
     * Adds all elements from the specified collection to the end of the linked list.
     *
     * @param c - collection of elements to add
     * @return true if the collection was added successfully
     */
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

    /**
     * Adds all elements from the specified collection starting at the specified index in the linked list.
     *
     * @param index - position to start adding
     * @param c     - collection of elements to add
     * @return true if the collection was added successfully
     */
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

    /**
     * Connects a collection of elements between two nodes in the linked list.
     *
     * @param prevNode - the node before the collection
     * @param nextNode - the node after the collection
     * @param c        - collection of elements to add
     */
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

    /**
     * Replaces the element at the specified index in the linked list with the specified element.
     *
     * @param index - position to set
     * @param e     - element to set
     * @return the old element at the specified index
     */
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

    /**
     * Retrieves the element at the specified index in the linked list.
     *
     * @param index - position to get
     * @return the element at the specified index
     */
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

    /**
     * Retrieves the first element in the linked list.
     *
     * @return the first element
     */
    @Override
    public E getFirst() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        return first.value;
    }

    /**
     * Retrieves the last element in the linked list.
     *
     * @return the last element
     */
    @Override
    public E getLast() {
        if (isEmpty()) {
            throw new NoSuchElementException();
        }
        Link current = last;
        return current.value;
    }

    /**
     * Removes the element at the specified index in the linked list.
     *
     * @param index - position to remove
     * @return the removed element
     */
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

    /**
     * Removes the first occurrence of the specified element from the linked list.
     *
     * @param o - element to remove
     * @return true if the element was removed successfully
     */
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

    /**
     * Removes and returns the first element in the linked list.
     *
     * @return the removed first element
     */
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

    /**
     * Removes and returns the last element in the linked list.
     *
     * @return the removed last element
     */
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

    /**
     * Clears the linked list, removing all elements.
     */
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

    /**
     * Checks if the linked list contains the specified element.
     *
     * @param o - element to check
     * @return true if the element is found, false otherwise
     */
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

    /**
     * Returns the number of elements in the linked list.
     *
     * @return the size of the linked list
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Checks if the linked list is empty.
     *
     * @return true if the linked list is empty, false otherwise
     */
    @Override
    public boolean isEmpty() {
        return first == null;
    }

    /**
     * Returns a string representation of the linked list.
     *
     * @return a string representation of the linked list
     */
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

    /**
     * Returns an iterator over the elements in the linked list.
     *
     * @return an iterator over the elements in the linked list
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<>() {
            private int index = 0;
            private int previousIndex = -1;

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
             * @return the next element
             */
            @Override
            public E next() {
                if (index >= size) {
                    throw new NoSuchElementException();
                }
                previousIndex = index;
                return get(index++);
            }

            /**
             * Removes the last element returned by the iterator.
             */
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

    /**
     * Returns an iterator that traverses the linked list in reverse order.
     *
     * @return an iterator that traverses the linked list in reverse order
     */
    public Iterator<E> descendingIterator() {
        return new Iterator<>() {
            private int index = size - 1;
            private int previousIndex = -1;

            /**
             * Checks if there are more elements to iterate over in reverse order.
             * @return true if there are more elements, false otherwise
             */
            @Override
            public boolean hasNext() {
                return index >= 0;
            }

            /**
             * Returns the next element in the reverse iteration.
             * @return the next element
             */
            @Override
            public E next() {
                if (index < 0) {
                    throw new NoSuchElementException();
                }
                previousIndex = index;
                return get(index--);
            }

            /**
             * Removes the last element returned by the reverse iterator.
             */
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