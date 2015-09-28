package org.arquillian.example.twostacksmaven;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author hom
 */
public class ArrayStackTest extends StackTestBase {

    @Override
    Stack<String> createInstance() {
        return new ArrayStack<>();
    }

    @Test
    public void watch_it_grow() {

        ArrayStack<String> stack = (ArrayStack<String>) createInstance();
        int ic = stack.capacity();
        assertEquals( "initial size", 4, ic );
        System.out.println( "cap=" + stack.capacity() );
        String[] testData = {"A", "B", "C", "D", "E"};
        for ( String s : testData ) {
            stack.push( s );
        }

        System.out.println( "size=" + stack.size() );
        System.out.println( "cap=" + stack.capacity() );
        assertEquals( "grow by factor 2", 2 * ic, stack.capacity() );
    }

    @Test( expected = ArrayIndexOutOfBoundsException.class )
    @Override
    public void empty_peek_should_bark() {
        super.empty_peek_should_bark();
    }

    @Test
    public void after_pop_elements_are_nulled_out() {
        ArrayStack<String> stack = (ArrayStack<String>) createInstance();
        String[] testData = {"A", "B", "C", "D", "E"};
        for ( String s : testData ) {
            stack.push( s );
        }
        // remove all elements
        for ( int i = testData.length - 1; i >= 0; i-- ) {
            String x = testData[i];
            assertEquals( "Datum off stack", x, stack.pop() );
            assertFalse( "and gone", stack.contains( x ) );
        }
    }

    @Test public void contains_after_push() {
        ArrayStack<String> stack = (ArrayStack<String>) createInstance();
        String s = "A";
        stack.push( s );
        assertTrue( stack.contains( s ) );

    }

}
