package org.arquillian.example.twostacksmaven;

/**
 * Linked Node stack. This implementation uses an internal Link container
 * forming a linked stack.<br>
 *
 * <a href='doc-files/linkedstack.jpg'>
 * <img width='480' src='doc-files/linkedstack.jpg' alt='Link stack design'></a>
 *
 * <p>
 * The stack always "hangs" by it's top, which is where the {@code push()} an
 * {@code pop()} operations take place.</p>
 * <p>
 * This implementation has no constraint on its size, so it can take all
 * elements you throw at it until all the JVM's memory is consumed. There is no
 * (integer) indexing, which limits a array implementation to the
 * Integer.MAX_INT (2147483647) elements in a stack. It is unsure if this is a
 * deciding feature.</p>
 *
 *
 * @author hom
 * @param <E> type on this Stack.
 */
public class LinkedStack<E> implements Stack<E> {

    /**
     * A link with a payload of type E. The link holds one element and an
     * optional (non null) link to a next link. The next is the next lower
     * storage in the stack. The link class and members are package private for
     * easy access.
     *
     * @param <K> payload type.
     */
    static class Link<K> {

        /**
         * Set once payload.
         */
        final K element;
        /**
         * This makes the "chain".
         */
        final Link<K> next;

        Link( K element, Link<K> next ) {
            this.element = element;
            this.next = next;
        }
    }

    /**
     * The
     */
    private Link<E> top;

    @Override
    public boolean isEmpty() {
        return null == top;
    }

    @Override
    public void push( E e ) {
        top = new Link( e, top );
    }

    @Override
    public E peek() {
        return top.element;
    }

    @Override
    public E pop() {
        E result = top.element;
        top = top.next;
        return result;
    }

}
