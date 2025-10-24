package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.*;

/**
 * A test class for MyArrayList and MyLinkedList implementations of MyList interface.
 */
public class MyTestList {
    /**
     * Test result messages and default sizes
     */
    private static final String TEST_PASSED = "Test passed: ";
    /**
     * Test result messages and default sizes
     */
    private static final String TEST_FAILED = "Test FAILED: ";
    /**
     * Default size for testing
     */
    private static final int DEFAULT_SIZE = 4;
    /**
     * Maximum size for testing
     */
    private static final int MAXIMUM_SIZE = 12;
    /**
     * Test string constant
     */
    private static final String TEST_STRING = "TEST";

    /**
     * Main method to run the tests.
     *
     * @param args command line arguments
     */
    public static void main(String[] args) {
        testMyList();
    }

    /**
     * Runs a series of tests on MyList implementations.
     */
    private static void testMyList() {

        testAddIndexFirstLast(initLinkedList(DEFAULT_SIZE));
        testAddIndexFirstLast(initArrayList(DEFAULT_SIZE));

        testAddAllToString(initLinkedList(MAXIMUM_SIZE));
        testAddAllToString(initArrayList(MAXIMUM_SIZE));

        testGetSet(initLinkedList(MAXIMUM_SIZE));
        testGetSet(initArrayList(MAXIMUM_SIZE));

        testGetLastGetFirst(initLinkedList(DEFAULT_SIZE));
        testGetLastGetFirst(initArrayList(DEFAULT_SIZE));

        testRemoveLastFirst(initLinkedList(MAXIMUM_SIZE));
        testRemoveLastFirst(initArrayList(MAXIMUM_SIZE));

        testClear(initLinkedList(DEFAULT_SIZE));
        testClear(initArrayList(DEFAULT_SIZE));

        testIterator(initLinkedList(MAXIMUM_SIZE));
        testIterator(initArrayList(MAXIMUM_SIZE));

        testDescendingIterator();
        testQueue();
        testStack();
    }

    /**
     * Tests the add, add(index), addFirst, addLast methods of MyList.
     *
     * @param myList the MyList instance to test
     */
    private static void testAddIndexFirstLast(MyList<String> myList) {
        String testName = "add() & add(index) & addLast() & addFirst(): " + myList.getClass().getSimpleName();
        int index = 1;
        String expectedString = TEST_STRING;
        myList.addFirst(null);
        myList.addLast(TEST_STRING);
        myList.add(index, expectedString);
        String actualString = myList.get(index);
        if (myList.getFirst() == null &&
                TEST_STRING.equals(myList.getLast()) &&
                actualString.equals(expectedString)) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    /**
     * Tests the addAll, addAll(index), toString, and updateCapacity methods of MyList.
     * All methods are indexed starting from 1 for easier tracking of test cases.
     * #1 - Test adding an empty collection at index 0.
     * #2 - Test adding a collection to a new MyLinkedList at index 0.
     * #3 - Test adding a collection at index 0.
     * #4 - Test adding a collection at the end of the list.
     * #5 - Test adding a collection in the middle of the list.
     * #6 - Test adding a collection at last element position.
     * #7 - Test adding a collection at an index greater than size (should throw exception).
     * #8 - Test adding a collection at a negative index (should throw exception).
     *
     * @param myList - the MyList instance to test
     */
    private static void testAddAllToString(MyList<String> myList) {
        String testName = "addAll() & addAll(index) & toString() & updateCapacity(): " + myList.getClass().getSimpleName();

        ArrayList<String> expectedList = new ArrayList<>();
        ArrayList<String> arrayList = new ArrayList<>();
        MyLinkedList<String> newLinkedList = new MyLinkedList<>();
        int index = 0;
        // 1
        index++;
        if (myList.addAll(0, arrayList)) {
            printFAIL(testName + " #" + index);
            return;
        }
        // 2
        index++;
        for (int i = 0; i < myList.size(); i++) {
            expectedList.add(String.valueOf(i));
            arrayList.add(String.valueOf(i));
        }
        newLinkedList.addAll(0, arrayList);
        if (!isMyListValid(newLinkedList, arrayList)) {
            printFAIL(testName + " #" + index);
            return;
        }
        // 3
        index++;
        myList.addAll(0, arrayList);
        expectedList.addAll(0, arrayList);
        if (!isMyListValid(myList, expectedList)) {
            printFAIL(testName + " #" + index);
            return;
        }
        // 4
        index++;
        myList.addAll(arrayList);
        expectedList.addAll(arrayList);
        if (!isMyListValid(myList, expectedList)) {
            printFAIL(testName + " #" + index);
            return;
        }
        // 5
        index++;
        myList.addAll(myList.size() / 2, arrayList);
        expectedList.addAll(expectedList.size() / 2, arrayList);
        if (!isMyListValid(myList, expectedList)) {
            printFAIL(testName + " #" + index);
            return;
        }
        // 6
        index++;
        myList.addAll(myList.size() - 1, arrayList);
        expectedList.addAll(expectedList.size() - 1, arrayList);
        if (!isMyListValid(myList, expectedList)) {
            printFAIL(testName + " #" + index);
            return;
        }
        // 7
        index++;
        boolean exceptionTest8 = false;
        try {
            myList.addAll(myList.size() + 1, arrayList);
        } catch (IndexOutOfBoundsException e) {
            exceptionTest8 = true;
        }
        // 8
        index++;
        boolean exceptionTest9 = false;
        try {
            myList.addAll(-1, arrayList);
        } catch (IndexOutOfBoundsException e) {
            exceptionTest9 = true;
        }
        if (!exceptionTest8 || !exceptionTest9) {
            String testNumber = !exceptionTest8 ? " #7" : " #" + index;
            printFAIL(testName + testNumber);
        } else {
            printPASS(testName);
        }
    }

    /**
     * Validates if the MyList instance matches the expected ArrayList.
     *
     * @param myList       - the MyList instance to validate
     * @param expectedList - the expected ArrayList
     * @return - true if both lists are equal, false otherwise
     */
    private static boolean isMyListValid(MyList<String> myList, ArrayList<String> expectedList) {
        return myList.toString().equals(expectedList.toString());
    }

    /**
     * Tests the get and set methods of MyList.
     *
     * @param myList - the MyList instance to test
     */
    private static void testGetSet(MyList<String> myList) {
        String testName = "get() & set(): " + myList.getClass().getSimpleName();
        int testIndex = MAXIMUM_SIZE / 2;
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

    /**
     * Tests the getLast and getFirst methods of MyList.
     *
     * @param myList - the MyList instance to test
     */
    private static void testGetLastGetFirst(MyList<String> myList) {
        String testName = "getLast() & getFirst(): " + myList.getClass().getSimpleName();
        if (myList.getLast().equals(String.valueOf(DEFAULT_SIZE - 1)) &&
                myList.getFirst().equals(String.valueOf(0))) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    /**
     * Tests the removeLast, removeFirst, remove(index), remove(Object), contains, and size methods of MyList.
     *
     * @param myList - the MyList instance to test
     */
    private static void testRemoveLastFirst(MyList<String> myList) {
        String testName = "removeLast() & " +
                "removeFirst() & " +
                "remove(index) & " +
                "remove(Object) & " +
                "contains() & " +
                "size() & " +
                "updateCapacity: " + myList.getClass().getSimpleName();
        ArrayList<String> expectedList = new ArrayList<>();
        int index = DEFAULT_SIZE;
        for (int i = 0; i < myList.size(); i++) {
            expectedList.add(String.valueOf(i));
        }
        if (!myList.removeFirst().equals(expectedList.removeFirst()) &&
                !myList.removeLast().equals(expectedList.removeLast())) {
            printFAIL(testName);
            return;
        }
        if (!myList.remove(index).equals(expectedList.remove(index))) {
            printFAIL(testName);
            return;
        }
        String element = myList.getLast();
        myList.remove(element);
        expectedList.remove(element);
        if (!isMyListValid(myList, expectedList) && myList.contains(element)) {
            printFAIL(testName);
        } else {
            printPASS(testName);
        }
    }

    /**
     * Tests the clear and isEmpty methods of MyList.
     *
     * @param myList - the MyList instance to test
     */
    private static void testClear(MyList<String> myList) {
        String testName = "clear() & isEmpty(): " + myList.getClass().getSimpleName();
        myList.clear();
        if (myList.isEmpty()) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    /**
     * Tests the iterator method of MyList.
     *
     * @param myList - the MyList instance to test
     */
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

    /**
     * Tests the descendingIterator method of MyLinkedList.
     */
    private static void testDescendingIterator() {
        String testName = "DescendingIterator(): MyLinkedList";
        MyLinkedList<String> myList = new MyLinkedList<>();
        LinkedList<String> expectedList = new LinkedList<>();
        for (int i = 0; i < MAXIMUM_SIZE; i++) {
            myList.add(String.valueOf(i));
            expectedList.add(String.valueOf(i));
        }
        Iterator<String> myIterator = myList.descendingIterator();
        Iterator<String> expectedIterator = expectedList.descendingIterator();
        while (myIterator.hasNext()) {
            if (myIterator.next().equals(expectedIterator.next())) {
                myIterator.remove();
                expectedIterator.remove();
            }
        }
        if (myList.isEmpty()) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    /**
     * Tests the offer, peek, poll, and empty methods of MyQueue.
     */
    private static void testQueue() {
        String testName = "offer() & peek() & poll() & empty(): MyQueue";
        MyQueue<String> myQueue = new MyQueue<>();
        Deque<String> expectedDeque = new LinkedList<>();
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            myQueue.offer(String.valueOf(i));
            expectedDeque.offer(String.valueOf(i));
        }
        if (!myQueue.peek().equals(expectedDeque.peek())) {
            printFAIL(testName);
            return;
        }
        while (!myQueue.empty()) {
            if (!myQueue.poll().equals(expectedDeque.poll())) {
                printFAIL(testName);
                return;
            }
        }
        if (myQueue.empty()) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    /**
     * Tests the push, peek, pop, and empty methods of MyStack.
     */
    private static void testStack() {
        String testName = "push() & peek() & pop() & empty(): MyStack";
        MyStack<String> myStack = new MyStack<>();
        Stack<String> expectedStack = new Stack<>();
        for (int i = 0; i < DEFAULT_SIZE; i++) {
            myStack.push(String.valueOf(i));
            expectedStack.push(String.valueOf(i));
        }
        if (!myStack.peek().equals(expectedStack.peek())) {
            printFAIL(testName);
            return;
        }
        while (!myStack.empty()) {
            if (!myStack.pop().equals(expectedStack.pop())) {
                printFAIL(testName);
                return;
            }
        }
        if (myStack.empty()) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    /**
     * Initializes a MyArrayList with string representations of integers from 0 to size-1.
     *
     * @param size the size of the MyArrayList to initialize
     * @return the initialized MyArrayList
     */
    private static MyArrayList<String> initArrayList(int size) {
        MyArrayList<String> myArrayListTest = new MyArrayList<>();
        for (int i = 0; i < size; i++) {
            myArrayListTest.add(String.valueOf(i));
        }
        return myArrayListTest;
    }

    /**
     * Initializes a MyLinkedList with string representations of integers from 0 to size-1.
     *
     * @param size the size of the MyLinkedList to initialize
     * @return the initialized MyLinkedList
     */
    private static MyLinkedList<String> initLinkedList(int size) {
        MyLinkedList<String> myLinkedListTest = new MyLinkedList<>();
        for (int i = 0; i < size; i++) {
            myLinkedListTest.add(String.valueOf(i));
        }
        return myLinkedListTest;
    }

    /**
     * Prints a message indicating that a test has passed.
     *
     * @param testName the name of the test
     */
    public static void printPASS(String testName) {
        System.out.println(TEST_PASSED + testName);
    }

    /**
     * Prints a message indicating that a test has failed.
     *
     * @param testName the name of the test
     */
    public static void printFAIL(String testName) {
        System.err.println(TEST_FAILED + testName);
    }
}
