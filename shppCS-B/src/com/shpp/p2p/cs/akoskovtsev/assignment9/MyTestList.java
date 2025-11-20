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
        testAdd(initLinkedList(DEFAULT_SIZE));
        testAdd(initArrayList(DEFAULT_SIZE));

        testAddAll(initLinkedList(MAXIMUM_SIZE));
        testAddAll(initArrayList(MAXIMUM_SIZE));

        testGetSet(initLinkedList(MAXIMUM_SIZE));
        testGetSet(initArrayList(MAXIMUM_SIZE));

        testGetLastGetFirst(initLinkedList(DEFAULT_SIZE));
        testGetLastGetFirst(initArrayList(DEFAULT_SIZE));

        testRemove(initLinkedList(MAXIMUM_SIZE));
        testRemove(initArrayList(MAXIMUM_SIZE));

        testClear(initLinkedList(DEFAULT_SIZE));
        testClear(initArrayList(DEFAULT_SIZE));

        testIterator(initLinkedList(MAXIMUM_SIZE));
        testIterator(initArrayList(MAXIMUM_SIZE));

        testToString(initArrayList(DEFAULT_SIZE));
        testToString(initLinkedList(DEFAULT_SIZE));

        testSize(initArrayList(DEFAULT_SIZE));
        testSize(initLinkedList(DEFAULT_SIZE));

        testContains(initArrayList(DEFAULT_SIZE));
        testContains(initLinkedList(DEFAULT_SIZE));

        testDescendingIterator();

        testQueue();
        testStack();
    }

    /**
     * Tests the size method of MyList.
     *
     * @param myList the MyList instance to test
     */
    private static void testSize(MyList<String> myList) {
        String testName = "size(): " + myList.getClass().getSimpleName();
        if (myList.size() == DEFAULT_SIZE) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    /**
     * Tests the contains method of MyList.
     *
     * @param myList the MyList instance to test
     */
    private static void testContains(MyList<String> myList) {
        String testName = "contains(): " + myList.getClass().getSimpleName();
        ArrayList<Boolean> testResult = new ArrayList<>();
        testResult.add(testNotContainsObject(myList, testName));
        testResult.add(testContainsObject(myList, testName));
        testResult.add(testContainsNull(myList, testName));
        for (boolean result : testResult) {
            if (!result) {
                return;
            }
        }
        printPASS(testName);
    }

    /**
     * Tests if MyList does not contain a specific object.
     *
     * @param myList   - the MyList instance to test
     * @param testName - the name of the test
     * @return true if the test passed, false otherwise
     */
    private static boolean testNotContainsObject(MyList<String> myList, String testName) {
        String testCaseName = testName + " - " + getCurrentMethodName();
        if (myList.contains(TEST_STRING)) {
            printFAIL(testCaseName);
            return false;
        }
        return true;
    }

    /**
     * Tests if MyList contains null after adding it.
     *
     * @param myList   - the MyList instance to test
     * @param testName - the name of the test
     * @return true if the test passed, false otherwise
     */
    private static boolean testContainsNull(MyList<String> myList, String testName) {
        String testCaseName = testName + " - " + getCurrentMethodName();
        myList.add(null);
        if (!myList.contains(null)) {
            printFAIL(testCaseName);
            return false;
        }
        return true;
    }

    /**
     * Tests if MyList contains a specific object after adding it.
     *
     * @param myList   the MyList instance to test
     * @param testName the name of the test
     * @return true if the test passed, false otherwise
     */
    private static boolean testContainsObject(MyList<String> myList, String testName) {
        String testCaseName = testName + " - " + getCurrentMethodName();
        String testString = TEST_STRING;
        myList.add(testString);
        if (!myList.contains(testString)) {
            printFAIL(testCaseName);
            return false;
        }
        return true;
    }

    /**
     * Tests the toString method of MyList.
     *
     * @param myList the MyList instance to test
     */
    private static void testToString(MyList<String> myList) {
        String testName = "toString(): " + myList.getClass().getSimpleName();
        StringBuilder expectedString = new StringBuilder("[");
        for (int i = 0; i < myList.size(); i++) {
            expectedString.append(myList.get(i));
            if (i != myList.size() - 1) {
                expectedString.append(", ");
            }
        }
        expectedString.append("]");
        if (myList.toString().contentEquals(expectedString)) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    /**
     * Tests the add, add(index), addFirst, addLast methods of MyList.
     *
     * @param myList the MyList instance to test
     */
    private static void testAdd(MyList<String> myList) {
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
     * Tests the addAll, addAll(index), and updateCapacity methods of MyList.
     *
     * @param myList - the MyList instance to test
     */
    private static void testAddAll(MyList<String> myList) {
        String testName = "addAll() & addAll(index) & updateCapacity(): " + myList.getClass().getSimpleName();
        ArrayList<Boolean> testResult = new ArrayList<>();
        testResult.add(testAddAllSize(myList, testName));
        testResult.add(testAddCollectionEmptyToStart(myList, testName));
        testResult.add(testAddCollectionToStart(myList, testName));
        testResult.add(testAddCollectionAtFinish(myList, testName));
        testResult.add(testAddCollectionToMiddle(myList, testName));
        testResult.add(testAddCollectionToLastElement(myList, testName));
        testResult.add(testOutOfBounds(myList, testName, myList.size() + 1));
        testResult.add(testOutOfBounds(myList, testName, -1));
        for (boolean result : testResult) {
            if (!result) {
                return;
            }
        }
        printPASS(testName);
    }

    /**
     * Tests adding a collection to MyList and verifies the size after addition.
     *
     * @param myList   - the MyList instance to test
     * @param testName - the name of the test
     * @return true if the test passed, false otherwise
     */
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

    /**
     * Tests adding an empty collection to MyList at the start index.
     *
     * @param myList   - the MyList instance to test
     * @param testName - the name of the test
     * @return true if the test passed, false otherwise
     */
    private static boolean testAddCollectionEmptyToStart(MyList<String> myList, String testName) {
        ArrayList<String> collectionToAdd = new ArrayList<>();
        String testCaseName = testName + " - " + getCurrentMethodName();
        if (myList.addAll(0, collectionToAdd)) {
            printFAIL(testCaseName);
            return false;
        }
        return true;
    }

    /**
     * Tests adding a collection to MyList at the start index.
     *
     * @param myList   - the MyList instance to test
     * @param testName - the name of the test
     * @return true if the test passed, false otherwise
     */
    private static boolean testAddCollectionToStart(MyList<String> myList, String testName) {
        ArrayList<String> collectionToAdd = createTestCollection();
        String testCaseName = testName + " - " + getCurrentMethodName();
        int indexToInput = 0;
        return testAddCollection(myList, collectionToAdd, testCaseName, indexToInput);
    }

    /**
     * Tests adding a collection to MyList at the end index.
     *
     * @param myList   - the MyList instance to test
     * @param testName - the name of the test
     * @return true if the test passed, false otherwise
     */
    private static boolean testAddCollectionAtFinish(MyList<String> myList, String testName) {
        String testCaseName = testName + " - " + getCurrentMethodName();
        int indexToInput = myList.size();
        ArrayList<String> collectionToAdd = createTestCollection();
        return testAddCollection(myList, collectionToAdd, testCaseName, indexToInput);
    }

    /**
     * Tests adding a collection to MyList at the middle index.
     *
     * @param myList   - the MyList instance to test
     * @param testName - the name of the test
     * @return true if the test passed, false otherwise
     */
    private static boolean testAddCollectionToMiddle(MyList<String> myList, String testName) {
        ArrayList<String> collectionToAdd = createTestCollection();
        int indexToInput = DEFAULT_SIZE / 2;
        String testCaseName = testName + " - " + getCurrentMethodName();
        return testAddCollection(myList, collectionToAdd, testCaseName, indexToInput);
    }

    /**
     * Tests adding a collection to MyList at the last element index.
     *
     * @param myList   - the MyList instance to test
     * @param testName - the name of the test
     * @return true if the test passed, false otherwise
     */
    private static boolean testAddCollectionToLastElement(MyList<String> myList, String testName) {
        ArrayList<String> collectionToAdd = createTestCollection();
        String testCaseName = testName + " - " + getCurrentMethodName();
        int indexToInput = myList.size() - 1;
        return testAddCollection(myList, collectionToAdd, testCaseName, indexToInput);
    }

    /**
     * Tests adding a collection to MyList at an out-of-bounds index.
     * Returns true if IndexOutOfBoundsException is thrown.
     *
     * @param myList   - the MyList instance to test
     * @param testName - the name of the test
     * @param index    - the out-of-bounds index
     * @return true if the test passed, false otherwise
     */
    private static boolean testOutOfBounds(MyList<String> myList, String testName, int index) {
        ArrayList<String> collectionToAdd = createTestCollection();
        boolean exceptionThrown = false;
        try {
            myList.addAll(index, collectionToAdd);
        } catch (IndexOutOfBoundsException e) {
            exceptionThrown = true;
        }
        if (!exceptionThrown) {
            printFAIL(testName + ": test IndexOutOfBoundsException failed");
        }
        return exceptionThrown;
    }

    /**
     * Tests adding a collection to MyList at a specified index.
     *
     * @param myList          - the MyList instance to test
     * @param collectionToAdd - the collection to be added
     * @param testName        - the name of the test
     * @param indexToInput    - the index at which to add the collection
     * @return true if the test passed, false otherwise
     */
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
        boolean isIndexAtEnd = indexToInput == myList.size();
        myList.addAll(indexToInput, collectionToAdd);
        boolean isSizeCorrect = (myList.size() == requiredNewSize);
        boolean isFirstCorrect = myList.getFirst().equals(expectedFirstElement);
        boolean isLastCorrect = myList.get(myList.size() - 1).equals(expectedLastElement);
        boolean isElementAtIndexCorrect = myList.get(indexToInput).equals(expectedElementAtIndex);
        boolean isElementAfterIndexCorrect = isIndexAtEnd ||
                myList.get(indexToInput + collectionToAdd.size()).equals(expectedElementAfterAddedCollection);
        return checkTestConditions(testName,
                isFirstCorrect,
                isLastCorrect,
                isElementAtIndexCorrect,
                isElementAfterIndexCorrect,
                isSizeCorrect);
    }

    /**
     * Checks the test conditions and prints failure messages if any condition is not met.
     *
     * @param testName   the name of the test
     * @param conditions the conditions to check
     * @return true if all conditions are met, false otherwise
     */
    private static boolean checkTestConditions(String testName, boolean... conditions) {
        String[] conditionsNames = {
                "First Element",
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

    /**
     * Creates a test collection of strings representing integers from MAXIMUM_SIZE down to 1.
     *
     * @return the created test collection
     */
    private static ArrayList<String> createTestCollection() {
        ArrayList<String> testCollection = new ArrayList<>();
        for (int i = MAXIMUM_SIZE; i > 0; i--) {
            testCollection.add(String.valueOf(i));
        }
        return testCollection;
    }

    /**
     * Tests the get and set methods of MyList.
     *
     * @param myList - the MyList instance to test
     */
    private static void testGetSet(MyList<String> myList) {
        String testName = "get() & set(): " + myList.getClass().getSimpleName();
        int testIndex = MAXIMUM_SIZE / 2;
        String newValueToSet = TEST_STRING;
        String expectedOldValue = myList.get(testIndex);
        String actualReturnedValue = myList.set(testIndex, newValueToSet);
        String valueAfterSet = myList.get(testIndex);
        if (newValueToSet.equals(valueAfterSet) && actualReturnedValue.equals(expectedOldValue)) {
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
        String firstElement = String.valueOf(0);
        String lastElement = String.valueOf(myList.size() - 1);
        if (myList.getLast().equals(lastElement) &&
                myList.getFirst().equals(firstElement)) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    /**
     * Tests the removeLast, removeFirst, remove(index), remove(Object).
     *
     * @param myList - the MyList instance to test
     */
    private static void testRemove(MyList<String> myList) {
        String testName = "test Remove " + myList.getClass().getSimpleName();

        String[] expectedStrings = new String[myList.size()];
        for (int i = 0; i < myList.size(); i++) {
            expectedStrings[i] = myList.get(i);
        }
        ArrayList<Boolean> testResult = new ArrayList<>();
        testResult.add(testRemoveLast(myList, expectedStrings, testName));
        testResult.add(testRemoveIndex(myList, expectedStrings, testName));
        testResult.add(testRemoveFirst(myList, expectedStrings, testName));
        testResult.add(testRemoveObject(myList, expectedStrings, testName));
        for (boolean result : testResult) {
            if (!result) {
                return;
            }
        }
        printPASS(testName);
    }

    /**
     * Tests the remove(object) method of MyList.
     *
     * @param myList          - the MyList instance to test
     * @param expectedStrings - the expected strings in the list
     * @param testName        - the name of the test
     * @return true if the test passed, false otherwise
     */
    private static boolean testRemoveObject(MyList<String> myList, String[] expectedStrings, String testName) {
        String testCaseName = testName + " - " + getCurrentMethodName() + " incorrect.";
        int middleIndex = myList.size() / 2;
        String element = expectedStrings[middleIndex];
        myList.remove(element);
        if (myList.contains(element)) {
            printFAIL(testCaseName);
            return false;
        }
        return true;
    }

    /**
     * Tests the remove(index) method of MyList.
     *
     * @param myList          - the MyList instance to test
     * @param expectedStrings - the expected strings in the list
     * @param testName        - the name of the test
     * @return true if the test passed, false otherwise
     */
    private static boolean testRemoveIndex(MyList<String> myList, String[] expectedStrings, String testName) {
        String testCaseName = testName + " - " + getCurrentMethodName() + " incorrect.";
        int middleIndex = myList.size() / 2;
        if (!myList.remove(middleIndex).equals(expectedStrings[middleIndex])) {
            printFAIL(testCaseName);
            return false;
        }
        return true;
    }

    /**
     * Tests the removeFirst method of MyList.
     *
     * @param myList          - the MyList instance to test
     * @param expectedStrings - the expected strings in the list
     * @param testName        - the name of the test
     * @return true if the test passed, false otherwise
     */
    private static boolean testRemoveFirst(MyList<String> myList, String[] expectedStrings, String testName) {
        String testCaseName = testName + " - " + getCurrentMethodName() + " incorrect.";
        if (!myList.removeFirst().equals(expectedStrings[0])) {
            printFAIL(testCaseName);
            return false;
        }
        return true;
    }

    /**
     * Tests the removeLast method of MyList.
     *
     * @param myList          - the MyList instance to test
     * @param expectedStrings - the expected strings in the list
     * @param testName        - the name of the test
     * @return true if the test passed, false otherwise
     */
    private static boolean testRemoveLast(MyList<String> myList, String[] expectedStrings, String testName) {
        String testCaseName = testName + " - " + getCurrentMethodName() + " incorrect.";
        if (!myList.removeLast().equals(expectedStrings[expectedStrings.length - 1])) {
            printFAIL(testCaseName);
            return false;
        }
        return true;
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
        MyLinkedList<String> myList = initLinkedList(MAXIMUM_SIZE);
        boolean isTestPassed = testDescendingOrder(myList, testName);
        if (myList.isEmpty() && isTestPassed) {
            printPASS(testName);
        } else {
            printFAIL(testName);
        }
    }

    /**
     * Tests the descending order of elements in MyLinkedList using descendingIterator.
     *
     * @param myList   - the MyLinkedList instance to test
     * @param testName - the name of the test
     * @return true if the order is correct, false otherwise
     */
    private static boolean testDescendingOrder(MyLinkedList<String> myList, String testName) {
        int lastIndex = myList.size() - 1;
        String expectedValue = String.valueOf(lastIndex);
        Iterator<String> myIterator = myList.descendingIterator();
        while (myIterator.hasNext()) {
            if (!expectedValue.equals(myIterator.next())) {
                printFAIL(testName + " order is incorrect.");
                return false;
            }
            lastIndex--;
            expectedValue = String.valueOf(lastIndex);
            myIterator.remove();
        }
        return true;
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