package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;

public class MyTestList {
    private static final String TEST_PASSED = "Test passed: ";
    private static final String TEST_FAILED = "Test FAILED: ";
    private static final int DEFAULT_SIZE = 4;
    private static final int MAXIMUM_SIZE = 12;
    private static final String TEST_STRING = "TEST";

    public static void main(String[] args) {
        testMyList();
    }

    private static void testMyList() {
        LinkedList<String> linkedList = new LinkedList<>();
        ArrayList<String> arrayList = new ArrayList<>();

//        MyLinkedList<String> myLinkedList = new MyLinkedList<>();
//        for (int i = 0; i < MAXIMUM_SIZE; i++) {
//            myLinkedList.add(String.valueOf(i));
//            linkedList.add(String.valueOf(i));
//        }
//        Iterator<String> iteratorLinked = linkedList.iterator();
//        Iterator<String> iterator = myLinkedList.iterator();
//        while (iteratorLinked.hasNext()) {
//            iteratorLinked.next();
//            iteratorLinked.remove();
//        }
//        System.out.println(linkedList);
//
//        while (iterator.hasNext()) {
//            iterator.next();
//            iterator.remove();
//        }
//
        testAddIndexFirstLast(initLinkedList(DEFAULT_SIZE));
        testAddIndexFirstLast(initArrayList(DEFAULT_SIZE));

        testGetSet(initLinkedList(DEFAULT_SIZE));
        testGetSet(initArrayList(DEFAULT_SIZE));

        testGetLastGetFirst(initLinkedList(DEFAULT_SIZE));
        testGetLastGetFirst(initArrayList(DEFAULT_SIZE));

        testRemove(initLinkedList(DEFAULT_SIZE));
        testRemove(initArrayList(DEFAULT_SIZE));

        testRemoveLastFirst(initLinkedList(DEFAULT_SIZE));
        testRemoveLastFirst(initArrayList(DEFAULT_SIZE));

        testClear(initLinkedList(DEFAULT_SIZE));
        testClear(initArrayList(DEFAULT_SIZE));


        testAddAllToString(initLinkedList(MAXIMUM_SIZE));
        testAddAllToString(initArrayList(MAXIMUM_SIZE));

        testIterator(initLinkedList(MAXIMUM_SIZE));
        testIterator(initArrayList(MAXIMUM_SIZE));
//        testUpdateCapacity();
    }

    private static void testAddIndexFirstLast(MyList<String> myList) {
        String testName = "add() & add(index) & addLast() & addFirst(): " + myList.getClass().getSimpleName();
        int index = 1;
        String expectedString = TEST_STRING;
        myList.add(index, expectedString);
        String actualString = myList.get(index);
        if (actualString.equals(expectedString)) {
            myList.addFirst(null);
            if (myList.getFirst() != null && myList.get(1).equals(String.valueOf(0))) {
                printFAIL(testName);
                return;
            }
            myList.addLast(TEST_STRING);
            if (!TEST_STRING.equals(myList.getLast())) {
                printFAIL(testName);
                return;
            }
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    private static void testAddAllToString(MyList<String> myList) {
        String testName = "addAll() & addAll(index) & toString(): " + myList.getClass().getSimpleName();
        ArrayList<String> expectedList = new ArrayList<>();
        ArrayList<String> arrayList = new ArrayList<>();
        for (int i = 0; i < MAXIMUM_SIZE; i++) {
            expectedList.add(String.valueOf(i));
            arrayList.add(String.valueOf(i));
        }
        //1
        try {
            myList.addAll(myList.size() + 1, arrayList);
        } catch (IndexOutOfBoundsException e){
            printPASS(testName);
        }
        //2
        try {
            myList.addAll(-1, arrayList);
        } catch (IndexOutOfBoundsException e){
            printPASS(testName);
        }
        //3
        ArrayList<String> list = new ArrayList<>();
        if(list.addAll(0, list)){
            printFAIL(testName);
        }else {
            printPASS(testName);
        }
        //4
        MyLinkedList<String> newLinkedList = new MyLinkedList<>();
        newLinkedList.addAll(0, arrayList);
        if(arrayList.toString().equals(newLinkedList.toString())){
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
        //5

        myList.addAll(0, arrayList);
        expectedList.addAll(0, arrayList);
        if(myList.toString().equals(expectedList.toString())){
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
        //6

        myList.addAll(myList.size(), arrayList);
        expectedList.addAll(expectedList.size(), arrayList);
        if(myList.toString().equals(expectedList.toString())){
            printPASS(testName);
        }else {
            printFAIL(testName);
        }
        //7

         myList.addAll(myList.size()/2, arrayList);
        expectedList.addAll(expectedList.size()/2, arrayList);
        if(myList.toString().equals(expectedList.toString())){
            printPASS(testName);
        }else {
            printFAIL(testName);
        }
//        8
        myList.clear();
        myList.add(TEST_STRING);
        myList.addAll(myList.size(), arrayList);
        expectedList.clear();
        expectedList.add(TEST_STRING);
        expectedList.addAll(expectedList.size(), arrayList);
        if(myList.toString().equals(expectedList.toString())){
            printPASS(testName);
        }else {
            printFAIL(testName);
        }
        //9
        arrayList.clear();
        arrayList.add(TEST_STRING);
        myList.addAll(1, arrayList);
        expectedList.addAll(1, arrayList);
        if(myList.toString().equals(expectedList.toString())){
            printPASS(testName);
        }else {
            printFAIL(testName);
        }
    }

    private static MyList<String> initLinkedList(int size) {
        MyList<String> myLinkedListTest = new MyLinkedList<>();
        for (int i = 0; i < size; i++) {
            myLinkedListTest.add(String.valueOf(i));
        }
        return myLinkedListTest;
    }

    private static void testRemoveLastFirst(MyList<String> myList) {
        String testName = "removeLast() & removeFirst() & size(): " + myList.getClass().getSimpleName();
        String firstElement = myList.removeFirst();
        String lastElement = myList.removeLast();
        if (myList.getFirst().equals(String.valueOf(1)) &&
                myList.getLast().equals(String.valueOf(DEFAULT_SIZE - 2)) &&
                myList.size() == DEFAULT_SIZE - 2 &&
                firstElement.equals(String.valueOf(0)) &&
                lastElement.equals(String.valueOf(DEFAULT_SIZE - 1))) {
            printPASS(testName);
        } else {
            printFAIL(testName + myList);
        }
    }


    private static void testGetLastGetFirst(MyList<String> myList) {
        String testName = "getLast() & getFirst(): " + myList.getClass().getSimpleName();
        if (myList.getLast().equals(String.valueOf(DEFAULT_SIZE - 1)) &&
                myList.getFirst().equals(String.valueOf(0))) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    private static void testRemove(MyList<String> myList) {
        String testName = "remove(index) & remove(Object) & contains(): " + myList.getClass().getSimpleName();
        String firstElement = String.valueOf(0);
        String secondElement = String.valueOf(1);
        String lastElement = String.valueOf(DEFAULT_SIZE - 1);
        myList.remove(lastElement);
        if (!myList.contains(lastElement) && myList.size() == DEFAULT_SIZE - 1) {
            myList.remove(firstElement);
            if (myList.contains(firstElement) || !myList.getFirst().equals(secondElement) ||
                    myList.remove(firstElement)) {
                printFAIL(testName);
            } else {
                printPASS(testName);
            }
        } else {
            printFAIL(testName);
        }
    }

    private static void testClear(MyList<String> myList) {
        String testName = "clear() & isEmpty(): " + myList.getClass().getSimpleName();
//        MyArrayList<String> myArrayListTest = initArrayList(DEFAULT_SIZE);
        myList.clear();
        if (myList.isEmpty()) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }


    private static void testUpdateCapacity() {
        String testName = "updateCapacity";
        int testSize = MAXIMUM_SIZE;
        MyArrayList<String> myArrayListTest = initArrayList(testSize);
        if (testSize == myArrayListTest.size() &&
                myArrayListTest.getFirst().equals(String.valueOf(0)) &&
                myArrayListTest.getLast().equals(String.valueOf(MAXIMUM_SIZE - 1))) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    private static MyArrayList<String> initArrayList(int size) {
        MyArrayList<String> myArrayListTest = new MyArrayList<>();
        for (int i = 0; i < size; i++) {
            myArrayListTest.add(String.valueOf(i));
        }
        return myArrayListTest;
    }

    public static void printPASS(String testName) {
        System.out.println(TEST_PASSED + testName);
    }

    public static void printFAIL(String testName) {
        System.err.println(TEST_FAILED + testName);
    }

    private static void testGetSet(MyList<String> myList) {
        String testName = "get() & set(): " + myList.getClass().getSimpleName();
        int testIndex = 1;
        String expectedNewValue = TEST_STRING;
        String valueBeforeSet = myList.get(testIndex);
        String returnedOldValue = myList.set(testIndex, expectedNewValue);
        String actualNewValue = myList.get(testIndex);
        if (expectedNewValue.equals(actualNewValue) && returnedOldValue.equals(valueBeforeSet)) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    private static void testIterator(MyList<String> myList) {
        String testName = "iterator(): " + myList.getClass().getSimpleName();
        Iterator<String> it = myList.iterator();
        while (it.hasNext()) {
            it.next();
            it.remove();
        }
        if (myList.isEmpty()) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }
}
