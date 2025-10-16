package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.ArrayList;
import java.util.Iterator;

public class TestArrayList {
    private static final String TEST_PASSED = "Test passed. ";
    private static final String TEST_FAILED = "Test FAILED. ";

    public static void main(String[] args) {
        testSize();
        testAdd();
        testIterator();

        ArrayList<String> test = new ArrayList<>();
        test.add("2");
        test.add("2");
        Iterator<String> iterator = test.iterator();
        iterator.next();
        iterator.remove();

        System.out.println(iterator.hasNext());
    }

    private static void testIterator() {
        MyArrayList<String> myArrayListTest = new MyArrayList<>();
        myArrayListTest.add("test");
        myArrayListTest.add("null");
        myArrayListTest.add(null);
        myArrayListTest.add("test");
        Iterator<String> it = myArrayListTest.iterator();
        if (it.hasNext()) {
            for (int i = 0; i < myArrayListTest.size(); i++) {
                it.next();
            }
            if(!it.hasNext()){
                System.out.println("Test iterator: " + TEST_PASSED);
            } else {
                System.err.println(TEST_FAILED);
            }
        } else {
            System.err.println(TEST_FAILED);
        }
    }

    private static void testSize() {
        MyArrayList<String> myArrayListTest = new MyArrayList<>();
        int expectedSize = 0;
        int actualSize = myArrayListTest.size();
        String message = "Expected size is: " + expectedSize + ", actual size is: " + actualSize;
        if (expectedSize == actualSize) {
            System.out.println("Test size:     " + TEST_PASSED + message);
        } else {
            System.err.println(TEST_FAILED + message);
        }

    }

    private static void testAdd() {
        MyArrayList<String> myArrayListTest = new MyArrayList<>();
        int expectedSize = 4;
        myArrayListTest.add(null);
        myArrayListTest.add(null);
        myArrayListTest.add("null");
        myArrayListTest.add("test");
        int actualSize = myArrayListTest.size();
        String message = "Expected size is: " + expectedSize + ", actual size is: " + actualSize;
        if (expectedSize == actualSize) {
            System.out.println("Test add:      " + TEST_PASSED + message);
        } else {
            System.err.println(TEST_FAILED + message);
            System.out.println("             List is: " + myArrayListTest);
        }
    }
}
