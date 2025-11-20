package MyCollections;

import java.util.Collection;
import java.util.Iterator;

/**
    * A custom list interface defining common list operations.
    *
    * @param <E> the type of elements in this list
 */
public interface MyList<E> {
    /**
     * Adds an element to the end of the list.
     * @param element the element to be added
     * @return true if the element was added successfully
     */
    boolean add(E element);

    /**
     * Adds an element at the specified index in the list.
     * @param index the index at which the element should be added
     * @param element the element to be added
     */
    void add(int index, E element);

    /**
     * Adds an element to the beginning of the list.
     * @param element the element to be added
     */
    void addFirst(E element);

    /**
     * Adds an element to the end of the list.
     * @param element the element to be added
     */
    void addLast(E element);

    /**
     * Adds all elements from the specified collection to the list.
     * @param c the collection containing elements to be added
     * @return true if the list changed as a result of the call
     */
    boolean addAll(Collection<? extends E> c);

    /**
     * Adds all elements from the specified collection starting at the specified index in the list.
     * @param index the index at which to insert the first element from the specified collection
     * @param c the collection containing elements to be added
     * @return true if the list changed as a result of the call
     */
    boolean addAll(int index, Collection<? extends E> c);

    /**
     * Replaces the element at the specified position in the list with the specified element.
     * @param index the index of the element to replace
     * @param element the element to be stored at the specified position
     * @return the element previously at the specified position
     */
    E set(int index, E element);

    /**
     * Retrieves the element at the specified index in the list.
     * @param index the index of the element to retrieve
     * @return the element at the specified index
     */
    E get(int index);

    /**
     * Retrieves the first element in the list.
     * @return the first element in the list
     */
    E getFirst();

    /**
     * Retrieves the last element in the list.
     * @return the last element in the list
     */
    E getLast();

    /**
     * Removes the element at the specified index in the list.
     * @param index the index of the element to be removed
     * @return the element that was removed
     */
    E remove(int index);

    /**
     * Removes the first occurrence of the specified element from the list, if it is present.
     * @param element the element to be removed
     * @return true if the element was present and removed, false otherwise
     */
    boolean remove(Object element);

    /**
     * Removes and returns the first element from the list.
     * @return the first element that was removed
     */
    E removeFirst();

    /**
     * Removes and returns the last element from the list.
     * @return the last element that was removed
     */
    E removeLast();

    /**
     * Removes all elements from the list.
     */
    void clear();

    /**
     * Checks if the list contains the specified element.
     * @param o the element to check for
     * @return true if the list contains the element, false otherwise
     */
    boolean contains(Object o);

    /**
     * Returns the number of elements in the list.
     * @return the size of the list
     */
    int size();

    /**
     * Checks if the list is empty.
     * @return true if the list is empty, false otherwise
     */
    boolean isEmpty();

    /**
     * Returns an iterator over the elements in the list.
     * @return an iterator over the elements in the list
     */
    Iterator<E> iterator();
}