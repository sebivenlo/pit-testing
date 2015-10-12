package util;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Simple list interface. The implementor may choose to support nulls in the
 * list or not. In case nulls are <b>not</b> wanted, the implementation should
 * throw an IllegalArgumentException.
 * 
 * This list interface also specifies FIFO or queue-like insertion order.
 * 
 * @author hom
 * @param <E> contained type
 */
public interface SimpleList<E> extends Iterable<E> {

    /**
     * Report the number of elements in the list.
     *
     * @return the number of elements contained.
     */
    int size();

    /**
     * Test if the list is empty.
     *
     * @return empty status of this list.
     */
    default boolean isEmpty() {
        return 0 == size();
    }

    /**
     * Add (append) element to end of this list.
     *
     * @param e element to a
     */
    void add( E e );

    /**
     * Get element from index in this list.
     *
     * @param idx index of element to get
     * @return the element at idx or null if none exists
     */
    E get( int idx );

    /**
     * Get element at index 0 from this list.
     *
     * @return the first index 0 or null if none exists
     */
    E get();

    /**
     * Take the first element from this list and remove it from this list.
     *
     * @return the first element or null if no element is available
     */
    E take();

    /**
     * Take the i-th element from this list and remove it from this list.
     *
     * @param i index
     * @return the element or 0 if none is available at this location.
     */
    E take( int i );

    /**
     * Check for presence of the element in the list. The method returns true if
     * there is an element x in the list for which x.equals(e) is true.
     *
     * @param e to check for/with
     * @return true if elements is equal to an element in the list, false if
     * not.
     */
    boolean contains( E e );

    /**
     * Sort this list by the order dictated by the given Comparator.
     *
     * @param c comparator to use for element order.
     */
    void sort( Comparator<? super E> c );

    /**
     * Create an Iterator for this list.
     *
     * @return an iterator.
     */
    @Override
    public Iterator<E> iterator();

}
