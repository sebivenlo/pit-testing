package util;

import java.util.Comparator;
import java.util.Iterator;

/**
 * Linked list with nodes named Link. This implementation uses a dummy head and
 * a dummy tail. Invariant (4 parts):
 * <ol>
 * <li>head.next refers to first element in the list (if any).</li>
 * <li>tail refers to "the last added element" and therefor to the place to add
 * the next element.</li>
 * <li> tail.next == null </li>
 * <li> size holds the number of elements in the list.</li>
 * </ol>
 *
 * @author hom
 * @param <E> element type in list
 */
public class SimpleLinkedList<E> implements SimpleList<E> {

    private final Link<E> head = new Link<>( null, null ); // points to first element
    private Link<E> tail = head; // points link where to add new element

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
     * @param e element to add
     */
    @Override
    public void add( E e ) {
        if ( null == e ) {
            throw new IllegalArgumentException( "nulls not allowed" );
        }
        // append at tail.
        tail.next = new Link<>( e, null );
        // move to new last
        tail = tail.next;
        size++;
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
        int c = 0;
        Link<E> l = head;
        // loop invariant: l points to element (c)
        // termination condition. c == n
        while ( c != n ) {
            l = l.next;
            c++;
        }
        return l.next.payLoad;
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
        int i = 0;
        Link<E> p = head; // previous
        while ( i < n ) {
            p = p.next;
            i++;
        }
        E result = p.next.payLoad;
        p.next = p.next.next;
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
        Link<E> x = head;
        while ( x.next != null ) {
            if ( x.next.payLoad.equals( e ) ) {
                return true;
            }
            x = x.next;
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
        // all elements between head and "sorted" are in sorted order.
        // termination: sorted.next == null a.k.a. end of list
        Link<E> sorted = head;
        while ( sorted.next != null ) {
            Link<E> nextMin = minLink( sorted.next, c );
            // candidateMin is min, swap it with first of unsorted
            swapLoad( nextMin, sorted.next );
            sorted = sorted.next;
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
        // termination: r == null
        while ( r != null ) {
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
        while ( l != null ) {
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
            Link<E> cursor = head;

            @Override
            public boolean hasNext() {
                return cursor.next != null;
            }

            @Override
            public E next() {
                E result = cursor.next.payLoad;
                cursor = cursor.next;
                return result;
            }
        };
    }

    @Override
    public boolean isEmpty() {
        return head.next == null;
    }

}
