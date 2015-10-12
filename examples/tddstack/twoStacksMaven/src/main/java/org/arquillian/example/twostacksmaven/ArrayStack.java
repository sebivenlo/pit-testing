package org.arquillian.example.twostacksmaven;

import java.util.Arrays;

/**
 * Array based implementation of Stack. This implementation grows by doubling
 * its capacity when a {@code Stack#push(E e)} is performed when the current
 * capacity is completely used.
 *
 * @author hom
 * @param <E> type of elements in stack.
 */
public class ArrayStack<E> implements Stack<E> {

    private static final int DEFAULT_CAPACITY = 4;
    private int top = -1;
    private E[] storage;

    /**
     * Create a stack with given initial capacity.
     *
     * @param cap the initial capacity.
     */
    @SuppressWarnings( "unchecked" )
    ArrayStack( int cap ) {
        this.storage = ( E[] ) new Object[ cap ];
    }

    /**
     * Default constructor producing stack of default capacity.
     */
    public ArrayStack() {
        this( DEFAULT_CAPACITY );
    }

    @Override
    public boolean isEmpty() {
        return 0 == size();
    }

    @Override
    public void push( E e ) {
        ensureCapacity();
        storage[ ++top ] = e;
    }

    /**
     * Get and remove element
     *
     * @return the top element
     * @throws RuntimeException if peek in the same state of this stack.
     * @see #peek()
     */
    @Override
    public E pop() {
        E result = peek();
        storage[ top-- ] = null;
        return result;

    }

    /**
     * Get the top element.
     *
     * @return the top element
     * @throws ArrayIndexOutOfBoundsException on empty stack.
     */
    @Override
    public E peek() {
        return storage[ top ];
    }

    /**
     * Make sure the stack has sufficient capacity to take the next push.
     */
    private void ensureCapacity() {
        if ( top + 1 < storage.length ) {
            return;
        }
        storage = Arrays.copyOf( storage, storage.length * 2 );
    }

    /**
     * Get current size.
     *
     * @return size
     */
    int size() {
        return top + 1;
    }

    /**
     * Get the capacity.
     *
     * @return capacity
     */
    int capacity() {
        return storage.length;
    }

    boolean contains( Object e ) {
        for ( Object o : storage ) {
            if ( e == o ) {
                return true;
            }
        }
        return false;
    }
}
