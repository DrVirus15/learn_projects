package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.LinkedList;


public class TestLinkedList {
    private static final String TEST_PASSED = "Test passed: ";
    private static final String TEST_FAILED = "Test FAILED: ";
    private static final int DEFAULT_SIZE = 4;
    private static final int MAXIMUM_SIZE = 12;
    private static final String TEST_STRING = "TEST";



    public static void main(String[] args) {
        LinkedList<String> linkedList = new LinkedList<>();
        MyLinkedList<String> myArrayListTest = initTestList(DEFAULT_SIZE);


//        testToString();
//        testAddIndex();
//        testGetSet();
//        testGetLastGetFirst();
//        testRemove();
//        testRemoveLastFirst();
//        testIterator();
//        testAddAllToString();
//        testClear();
//        testUpdateCapacity();
    }

    private static void testGetLastGetFirst() {
        String testName = "getLast() & getFirst()";
        MyLinkedList<String> myLinkedList = initTestList(DEFAULT_SIZE);
        if (myLinkedList.getLast().equals(String.valueOf(DEFAULT_SIZE - 1)) &&
                myLinkedList.getFirst().equals(String.valueOf(0))) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    private static void testGetSet() {
        String testName = "get() & set()";
        String expectedString = TEST_STRING;
        MyLinkedList<String> myLinkedList = initTestList(DEFAULT_SIZE);
        myLinkedList.set(0, expectedString);
        String actualString = myLinkedList.getFirst();
        if (expectedString.equals(actualString)) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    private static void testAddIndex() {
        String testName = "add(index) & add() & addLast() & addFirst()";
        int index = 1;
        String expectedString = index + "";
        MyLinkedList<String> myLinkedList = initTestList(DEFAULT_SIZE);
        myLinkedList.add(index, expectedString);
        String actualString = myLinkedList.get(index);
        if (actualString.equals(expectedString)) {
            myLinkedList.addFirst(null);
            if (myLinkedList.getFirst() != null && myLinkedList.get(1).equals(String.valueOf(0))) {
                printFAIL(testName);
                return;
            }
            myLinkedList.addLast(TEST_STRING);
            if (!TEST_STRING.equals(myLinkedList.getLast())) {
                printFAIL(testName);
                return;
            }
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }
    private static void testToString() {
        String testName = "toString()";
        MyLinkedList<String> myLinkedListTest = initTestList(DEFAULT_SIZE);
        LinkedList<String> linkedList = new LinkedList<>();
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            linkedList.add(String.valueOf(i));
        }
        if(linkedList.toString().equals(myLinkedListTest.toString())){
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }
    private static MyLinkedList<String> initTestList(int size) {
        MyLinkedList<String> myLinkedListTest = new MyLinkedList<>();
        for (int i = 0; i < size; i++) {
            myLinkedListTest.add(String.valueOf(i));
        }
        return myLinkedListTest;
    }

    public static void printPASS(String testName) {
        System.out.println(TEST_PASSED + testName);
    }

    public static void printFAIL(String testName) {
        System.err.println(TEST_FAILED + testName);
    }
}
