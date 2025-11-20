package MyCollections;

/**
 * A simple implementation of a queue data structure using MyLinkedList.
 *
 * @param <E> the type of elements in this queue
 */
public class MyQueue<E> {

    /**
     * The underlying linked list to store queue elements.
     */
    private final MyLinkedList<E> linkedList;

    /**
     * Constructs an empty queue.
     */
    public MyQueue() {
        this.linkedList = new MyLinkedList<>();
    }

    /**
     * Inserts the specified element into the queue.
     *
     * @param e the element to be added to the queue
     * @return true if the element was added successfully
     */
    public boolean offer(E e) {
        return linkedList.add(e);
    }

    /**
     * Removes and returns the head of the queue, or returns null if the queue is empty.
     *
     * @return the head of the queue, or null if the queue is empty
     */
    public E poll() {
        if (linkedList.isEmpty()) {
            return null;
        }
        return linkedList.removeFirst();
    }

    /**
     * Retrieves, but does not remove, the head of the queue, or returns null if the queue is empty.
     *
     * @return the head of the queue, or null if the queue is empty
     */
    public E peek() {
        if (linkedList.isEmpty()) {
            return null;
        }
        return linkedList.getFirst();
    }

    /**
     * Checks if the queue is empty.
     *
     * @return true if the queue is empty, false otherwise
     */
    public boolean empty() {
        return linkedList.isEmpty();
    }

    /**
     * Returns the number of elements in the queue.
     * @return - the size of the queue.
     */
    public int size(){
        return linkedList.size();
    }

    /**
     * Returns a string representation of the queue.
     *
     * @return a string representation of the queue.
     */
    @Override
    public String toString() {
        return linkedList.toString();
    }
}