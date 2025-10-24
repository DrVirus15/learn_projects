package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.Collection;
import java.util.Iterator;

/**
 *
 * @param <E>
 */
public interface MyList<E> {
    boolean add(E element);

    void add(int index, E element);

    void addFirst(E element);

    void addLast(E element);

    boolean addAll(Collection<? extends E> c);

    boolean addAll(int index, Collection<? extends E> c);

    E set(int index, E element);

    E get(int index);

    E getFirst();

    E getLast();

    E remove(int index);

    boolean remove(Object element);

    E removeFirst();

    E removeLast();

    void clear();

    boolean contains(Object o);

    int size();

    boolean isEmpty();

    Iterator<E> iterator();
}
