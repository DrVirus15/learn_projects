package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.Collection;
import java.util.Iterator;

public interface MyList<E> {
    void addFirst(E element);

    void addLast(E element);

    void add(int index, E element);

    void add(E element);

    E getFirst();

    E getLast();

    E get(int index);

    E set(int index, E element);

    E remove(int index);

    boolean remove(Object element);

    int size();

    boolean contains(Object o);

    E removeFirst();

    E removeLast();

    boolean addAll(Collection<? extends E> c);

    boolean addAll(int index, Collection<? extends E> c);

    boolean isEmpty();

    void clear();

    Iterator<E> iterator();
}
