package util;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Linked list with nodes named Link. This implementation uses a dummy head and
 * tail node. Both nodes are concrete notes. Initially head.next == tail
 * Invariant (4 parts):
 * <ol>
 * <li>head.next refers to first element in the list (if any) or to the 
 * tail node otherwise (the list then is empty).</li>
 * <li>runner refers to "the last referenced element".</li>
 * <li>if there are real nodes, the next of last real node refers to tail </li>
 * <li>size holds the number of elements in the list.</li>
 * <li>index is index of the node which runner point to, index == -1 if and only
 * if runner == head</li>
 * </ol>
 *
 * Consequence is that empty list and list with elements have at least a
 * head and a tail node 
 *
 * @author hom, dos
 * @param <E> element type in list
 */
public class SimpleLinkedList3<E> implements SimpleList<E> {

    private Link<E> tail = new Link<>(null,null); 
    private Link<E> head = new Link<>(null, tail);
    private Link<E> runner = head;
    private int index = -1;

    private static class Link<T> {

        private T payLoad;
        private Link<T> next;

        public Link( T t, Link<T> next ) {
            this.payLoad = t;
            this.next = next;
        }

    }

    /**
     * Exchange the datum element of two nodes.
     *
     * @param <T> TYpe of datum in nodes
     * @param a node in swap
     * @param b node in swap
     */
    private static <T> void swapLoad( Link<T> a, Link<T> b ) {
        T temp = a.payLoad;
        a.payLoad = b.payLoad;
        b.payLoad = temp;
    }

    private int size = 0;

    @Override
    public int size() {
        return size;
    }

    /**
     * Should maintain the invariant.
     *
     * @param e element to added after the runner node
     */
    @Override
    public void add( E e ) {
        if ( null == e ) {
            throw new IllegalArgumentException( "nulls not allowed" );
        }
        Link<E> newNode = new Link<E>(e, runner.next);
        runner.next = newNode;
        runner = newNode;
        index++;
        size++;
    }

    /**
     * Set runner to the node at position i. 
     * Following the class invariant, the payload of position 0 is 
     * head.next.payload.
     * Pre: 0 <= n < size
     *
     *
     * @param n the position in the list
     * @return runner points to the node at position n
     */
    private void setRunner(int n) {
        int index = -1;
        runner = head;
        // loop invariant: runner points to element (index)
        // termination condition. c == n
        while ( index != n ) {
            runner = runner.next;
            index++;
        }
    }
    
    /**
     * Get the element at position i. Following the class invariant, we payload
     * of position 0 is head.next.payload.
     *
     *
     *
     * @param n the position in the list
     * @return the value at position n
     */
    @Override
    public E get( int n ) {
        // sanitize n, checking if n is within bounds of the list.
        if ( n < 0 || n >= size ) {
            return null;
        }
        setRunner(n);
        return runner.payLoad;
    }

    @Override
    public E get() {
        return get( 0 );
    }

    @Override
    public E take( int n ) {
        // sanitize n, checking if n is within bounds of the list.
        if ( n < 0 || n >= size ) {
            return null;
        }
        E result;
        setRunner(n-1); //runner points to the node before node n
        result = runner.next.payLoad;
        runner.next = runner.next.next;
        size--;
        return result;
    }

    @Override
    public E take() {
        return take( 0 );
    }

    @Override
    public boolean contains( E e ) {
        // use fact that head.next is first real element.
        runner = head;
        index = -1;
        while ( runner.next != tail ) {
            if ( runner.next.payLoad.equals( e ) ) {
                return true;
            }
            runner = runner.next;
            index++;
        }
        return false;
    }

    /**
     * Note that the implementation does not use size. It only advances a link
     * "sorted" between head and tail. Everything "between" head and sorted
     * (Left hand side of sorted) is in sorted order. sorted.next is first of
     * the unsorted part.
     *
     * @param c comparator to use in sort algorithm.
     */
    @Override
    public void sort( Comparator<? super E> c ) {
        // outer loop invariant:
        // all elements between head and "sorted" (sorted excluded) are in 
        // sorted order.
        // termination: sorted == tail a.k.a. end of list
        Link<E> sorted = head.next;
        while ( sorted != tail ) {
            Link<E> nextMin = minLink( sorted, c );
            // candidateMin is min, swap it with first of unsorted
            swapLoad( nextMin, sorted );
            sorted = sorted.next;
            //unSorted = sorted.next;
        }
    }

    /**
     * Find the link between starting link and end of list.
     *
     * @param start non-null start position
     * @param c comparator to use
     * @return the minimum candidate.
     * @throws NullPointerException if either parameter is null.
     */
    private Link<E> minLink( Link<E> start, Comparator<? super E> c ) {
        Link<E> nextMinPos = start; // position reserved for minimum of tail
        Link<E> r = nextMinPos;
        // inner loop: find minimum in unsorted tail
        // invariant nextMin has minimum "between" sorted.next and r.
        // termination: r == tail
        while ( r != tail ) {
            if ( c.compare( nextMinPos.payLoad, r.payLoad ) > 0 ) {
                nextMinPos = r;
            }
            r = r.next;
        }
        return nextMinPos;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder( "[" );
        final String comma = ", ";
        final String empty = "";
        Link<E> l = head.next;
        String separator = empty;
        while ( l != tail ) {
            sb.append( separator ).append( l.payLoad.toString() );
            separator = comma;
            l = l.next;
        }
        sb.append( "] size:" ).append( size );
        return sb.toString();
    }

    /**
     * Create an iterator to traverse this list.
     *
     * This iterator directly follows from the invariant of the outer class.
     * There "cursor" or position is initialised with head. We are at the end of
     * the list if cursor.next == null. The value or payload at the cursor is
     * {@code >cursor.next.payload}.
     *
     * @return the iterator
     */
    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            Link<E> cursor = head.next;

            @Override
            public boolean hasNext() {
                return cursor != tail;
            }

            @Override
            public E next() {
                E result = cursor.payLoad;
                cursor = cursor.next;
                return result;
            }
        };
    }

    @Override
    public boolean isEmpty() {
        return head.next == tail;
    }

}
