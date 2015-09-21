package util;

/**
 * General purpose Stack interface. An implementing class may throw an
 * unspecified (and unchecked) RuntimeException when the non-emptiness
 * precondition of the {@code peek()} or {@code pop} operation is not observed.
 *
 * <p>
 * An implementing class should either provide auto-growth of the stack or
 * provide a constructor parameter to set the size once and for all (of the
 * instance). In the latter case, a unspecified (and unchecked) RuntimeException
 * may be thrown on overflow.</p>
 *
 * <p>
 * An implementing class does not have to shrink it's storage capacity after it
 * has grown. A typical stack will find its size during use and may keep that
 * obviously required capacity.</p>
 *
 * @author hom
 * @param <E> element type to store.
 */
public interface Stack<E> {

    /**
     * Push an element onto the top of this stack.
     *
     * @param e element to be pushed
     */
    void push( E e );

    /**
     * Get and and remove the top element.
     *
     * @return the top element reference
     * @throws RuntimeException unspecified runtime exception when a pop is
     * attempted on an empty stack.
     */
    E pop();

    /**
     * Peek what is on top.
     *
     * @return reference to top element
     * @throws RuntimeException unspecified runtime exception when a peek is
     * attempted on an empty stack.
     */
    E peek();

    /**
     * Test if this stack is empty.
     *
     * @return true if empty, false otherwise.
     */
    boolean isEmpty();
}
