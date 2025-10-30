package com.shpp.p2p.cs.akoskovtsev.assignment9;

import java.util.*;

/**
 * A test class for MyArrayList and MyLinkedList implementations of MyList interface.
 */
public class MyTestList {
    /**
     * Test result messages if test passed
     */
    private static final String TEST_PASSED = "Test passed: ";
    /**
     * Test result messages if test failed
     */
    private static final String TEST_FAILED = "Test FAILED: ";
    /**
     * Default size of the list for testing
     */
    private static final int DEFAULT_SIZE = 4;
    /**
     * Maximum size of the list for testing
     */
    private static final int MAXIMUM_SIZE = 12;
    /**
     * Test string constant for test
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
        myList.add(index, expectedString);
        String actualString = myList.get(index);
        myList.addFirst(null);
        myList.addLast(TEST_STRING);
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
        String testName = "addAll() & addAll(index) & toString() & updateCapacity(): " + myList.getClass().getSimpleName();// TODO toString - okremo
        ArrayList<Boolean> testResult = new ArrayList<>();
        // 1
        testResult.add(testAddAllSize(myList, testName));
        // 2
        testResult.add(testAddCollectionEmptyToStart(myList, testName));
        // 3
        testResult.add(testAddCollectionToStart(myList, testName));
        // 4
        testResult.add(testAddCollectionAtFinish(myList, testName));
        // 5
        testResult.add(testAddCollectionToMiddle(myList, testName));
        // 6
        testResult.add(testAddCollectionToLastElement(myList, testName));
        // 7
        testResult.add(testOutOfBounds(myList, testName, myList.size() + 1));
        // 8
        testResult.add(testOutOfBounds(myList, testName, -1));
        for (boolean result : testResult) {
            if (!result) {
                return;
            }
        }
        printPASS(testName);
    }

    private static boolean testOutOfBounds(MyList<String> myList, String testName, int index) {
        ArrayList<String> collectionToAdd = createTestCollection();
        boolean resultTest = false;
        try {
            myList.addAll(index, collectionToAdd);
        } catch (IndexOutOfBoundsException e) {
            resultTest = true;
        }
        if (!resultTest) {
            printFAIL(testName + ": test IndexOutOfBoundsException incorrect");
        }
        return resultTest;
    }

    private static boolean testAddAllSize(MyList<String> myList, String testName) {
        ArrayList<String> collectionToAdd = createTestCollection();
        int requiredNewSize = collectionToAdd.size() + myList.size();
        if (!myList.addAll(collectionToAdd)) {
            printFAIL(testName + ": AddAll() incorrect");
            return false;
        }
        if (myList.size() != requiredNewSize) {
            printFAIL(testName + ": Size incorrect");
            return false;
        }
        return true;
    }

    private static boolean testAddCollectionToLastElement(MyList<String> myList, String testName) {
        ArrayList<String> collectionToAdd = createTestCollection();
        testName += " - " + getCurrentMethodName();
        int indexToInput = myList.size() - 1;
        return testAddCollection(myList, collectionToAdd, testName, indexToInput);
    }


    private static boolean testAddCollectionEmptyToStart(MyList<String> myList, String testName) {
        ArrayList<String> collectionToAdd = new ArrayList<>();
        if (myList.addAll(0, collectionToAdd)) {
            printFAIL(testName);
            return false;
        }
        return true;
    }

    private static boolean testAddCollectionToStart(MyList<String> myList, String testName) {
        ArrayList<String> collectionToAdd = createTestCollection();
        testName += " - " + getCurrentMethodName();
        int indexToInput = 0;

        return testAddCollection(myList, collectionToAdd, testName, indexToInput);
    }

    private static boolean testAddCollectionToMiddle(MyList<String> myList, String testName) {
        ArrayList<String> collectionToAdd = createTestCollection();
        int indexToInput = DEFAULT_SIZE / 2;
        testName += " - " + getCurrentMethodName();
        return testAddCollection(myList, collectionToAdd, testName, indexToInput);
    }

    private static boolean testAddCollectionAtFinish(MyList<String> myList, String testName) {
        testName += " - " + getCurrentMethodName();
        int indexToInput = myList.size();
        ArrayList<String> collectionToAdd = createTestCollection();
        return testAddCollection(myList, collectionToAdd, testName, indexToInput);
    }

    private static boolean testAddCollection(MyList<String> myList,
                                             ArrayList<String> collectionToAdd,
                                             String testName,
                                             int indexToInput) {
        if (indexToInput > myList.size() || indexToInput < 0) {
            return false;
        }

        String expectedFirstElement = (!collectionToAdd.isEmpty() && indexToInput == 0)
                ? collectionToAdd.getFirst()
                : (!myList.isEmpty() ? myList.getFirst() : null);
        String expectedLastElement = (!collectionToAdd.isEmpty() && indexToInput == myList.size())
                ? collectionToAdd.getLast()
                : (!myList.isEmpty() ? myList.getLast() : null);
        String expectedElementAfterAddedCollection = (!myList.isEmpty() && indexToInput != myList.size())
                ? myList.get(indexToInput)
                : null;
        String expectedElementAtIndex = !collectionToAdd.isEmpty()
                ? collectionToAdd.getFirst()
                : (!myList.isEmpty() ? myList.get(indexToInput) : null);
        int requiredNewSize = collectionToAdd.size() + myList.size();
        boolean isIndexAtFinish = (indexToInput == myList.size());

        myList.addAll(indexToInput, collectionToAdd);

        boolean isSizeCorrect = (myList.size() == requiredNewSize);
        boolean isFirstCorrect = myList.getFirst().equals(expectedFirstElement);
        boolean isLastCorrect = myList.get(myList.size() - 1).equals(expectedLastElement);
        boolean isElementAtIndexCorrect = myList.get(indexToInput).equals(expectedElementAtIndex);
        boolean isElementAfterIndexCorrect = isIndexAtFinish ||
                myList.get(indexToInput + collectionToAdd.size()).equals(expectedElementAfterAddedCollection);
        return checkTestConditions(testName,
                isFirstCorrect,
                isLastCorrect,
                isElementAtIndexCorrect,
                isElementAfterIndexCorrect,
                isSizeCorrect);
    }

    private static boolean checkTestConditions(String testName,
                                               boolean... conditions) {
        String[] conditionsNames = {"First Element",
                "Last element",
                "Element at index",
                "Element after index",
                "Size"};
        for (int i = 0; i < conditions.length; i++) {
            if (!conditions[i]) {
                printFAIL(testName + ": " + conditionsNames[i] + " incorrect");
                return false;
            }
        }
        return true;
    }

    private static ArrayList<String> createTestCollection() {
        ArrayList<String> list = new ArrayList<>();
        for (int i = MAXIMUM_SIZE; i > 0; i--) {
            list.add(String.valueOf(i));
        }
        return list;
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
        for (int i = 0; i < MAXIMUM_SIZE; i++) {
            myList.add(String.valueOf(i));
        }
        Iterator<String> myIterator = myList.descendingIterator();
        int elementToRemove = 0;
        int i = 0;
        while (myIterator.hasNext()) {
            i++;
            myIterator.next();
            if (i == MAXIMUM_SIZE / 2) {
                elementToRemove = i;
                myIterator.remove();
            }
        }
        if (myList.contains(String.valueOf(elementToRemove))) {
            printFAIL(testName);
            return;
        }
        myIterator = myList.descendingIterator();
        while (myIterator.hasNext()) {
            myIterator.next();
            myIterator.remove();
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

    private static String getCurrentMethodName() {
        return Thread.currentThread().getStackTrace()[2].getMethodName();
    }
}
