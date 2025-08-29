package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;

public class MyArrayList<E extends Object> implements Iterable<E> {
    private int capacity = 10;
    private int nElems = 0;
    private Object[] list;

    public MyArrayList(int initialCapacity) {
        capacity = initialCapacity;
        list = new Object[capacity];
    }

    public MyArrayList() {
        list = new Object[capacity];
    }

    public boolean isEmpty() {
        return (nElems == 0);
    }

    public void updateCapacity() {
        capacity = capacity + capacity / 2;
        Object[] newList = new Object[capacity];
        System.arraycopy(list, 0, newList, 0, list.length);
        list = newList;
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

    @SuppressWarnings("unchecked")
    public E get(int index) {
        if (index < 0 || index >= nElems) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nElems);
        }
        return (E) list[index];
    }

    @SuppressWarnings("unchecked")
    public E set(int index, E element) {
        if (index < 0 || index >= nElems) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nElems);
        }
        E oldElement = (E) list[index];
        list[index] = element;
        return oldElement;
    }

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

    @SuppressWarnings("unchecked")
    public E remove(int index) {
        if (index < 0 || index >= nElems) {
            throw new IndexOutOfBoundsException("Index: " + index + ", Size: " + nElems);
        }
        E elem = (E) list[index];
        for (int i = index; i < nElems - 1; i++) {
            list[i] = list[i + 1];
        }
        nElems--;
        list[nElems] = null;
        return elem;
    }

    /**
     *
     * @param elem
     * @return
     */
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

    @Override
    public Iterator<E> iterator() {
        return new MyIterator();
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

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(10);
        list.add(154);
        list.add(999);
        list.add(1);
//        System.out.println("list: " + list);
        list.add(2, 15);
//        System.out.println("list: " + list);
//        System.out.println("set: " + list.set(0, 14));
//        System.out.println("list: " + list);
        Object o = 154;
//        System.out.println("contains? - " + list.contains(o));
        Integer number = 11;
//        System.out.println("list remove(index 0): " + list.remove(0));
//        System.out.println("list remove(object " + o + "): " + list.remove(o));
//        System.out.println(list);
        MyArrayList<Integer> myIntegerList = new MyArrayList<>();
        MyArrayList<String> myStringList = new MyArrayList<>();
        MyArrayList<Double> myDoubleList = new MyArrayList<>(3);
        int elem = 20031;
        myIntegerList.add(elem);
        myIntegerList.add(10);
        myIntegerList.add(1);
        myIntegerList.add(99);
        myIntegerList.add(15);

//        System.out.println(myIntegerList);
//        System.out.println("delete 1");
        Object o2 = 20031;
//        System.out.println("remove MY(object):" + myIntegerList.remove(o2));
//        System.out.println("remove MY(index 2):" + myIntegerList.remove(2));
        myIntegerList.add("kola".length());
        myIntegerList.add((int) 1.5);

        myStringList.add("a");
        myStringList.add("b");
        myStringList.add("c");
        myStringList.add("d");
        myStringList.add("e");

        myDoubleList.add((double) 15);
        myDoubleList.add((double) "kola".length());
        myDoubleList.add(1.5);
        myDoubleList.add(3, 1.5);


        int a = myIntegerList.size();
//        System.out.println(myIntegerList + ", size:" + a);
        myIntegerList.add(1, 88);
//        System.out.println(myIntegerList);
        myIntegerList.add(1);
        myIntegerList.add(1);
        myIntegerList.add(1);
        myIntegerList.add(1);
        myIntegerList.add(1);
        myIntegerList.add(1);
//        System.out.println(myIntegerList + ", size is: " + myIntegerList.size());
        myIntegerList.add(1);
        myIntegerList.add(1);
        myIntegerList.add(1);
//        System.out.println("contains? - " + myIntegerList.contains(o));
        myIntegerList.add(777);
//        System.out.println("my set: " + myIntegerList.set(10, 777));
//        System.out.println(myIntegerList + ", size is: " + myIntegerList.size());
//        System.out.println("size:" + a);
//        System.out.println("arrayList elem 1:" + list.get(1));
//        System.out.println("my Integer elem 0:" + myIntegerList.get(0));
//        System.out.println("my Double elem 1:" + myDoubleList.get(1));
//        System.out.println("my String elem 0:" + myStringList.get(0));
        System.out.println(myDoubleList + ", capacity:" + myDoubleList.capacity);
        System.out.println(myIntegerList);
        System.out.println("============================================================================");


        Iterator<Integer> listIterator = list.iterator();
        Iterator<Integer> integerIterator = myIntegerList.iterator();
//        while (integerIterator.hasNext()) {
//            System.out.println(integerIterator.next());
//        }
//
        int x;
        integerIterator = myIntegerList.iterator();
        while (integerIterator.hasNext()) {
            integerIterator.next();
//            System.out.println("remove: " + x);
            integerIterator.remove();
        }
        System.out.println(myIntegerList);
//        System.out.println(list);
//        System.out.println("=================");
//        int y;
//        while (listIterator.hasNext()) {
//            y = listIterator.next();
//
//            listIterator.remove();
//            System.out.println("remove: " + y);
//        }
//        System.out.println(list);
    }

    private class MyIterator implements Iterator<E> {
        private int index = 0;
        private int previousIndex = 0;
        private boolean isNext = false;

        @Override
        public boolean hasNext() {
            return (index < nElems);
        }

        @SuppressWarnings("unchecked")
        @Override
        public E next() {
            previousIndex = index;
            index++;
            isNext = true;
            return (E) list[previousIndex];
        }

        @Override
        public void remove() {
            if (isNext) {
                MyArrayList.this.remove(previousIndex);
            }
            isNext = false;
            previousIndex--;
            index--;
        }
    }
}