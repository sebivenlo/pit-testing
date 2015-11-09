/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import org.junit.Assert;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

/**
 *
 * @author hom
 */
public abstract class ListTestBase {

    abstract SimpleList<String> newInstance();
    SimpleList<String> list;

    @Before
    public void setUp() {
        list = newInstance();
    }

    @Test
    public void added_elements_should_be_contained() {
        String s = "A";
        list.add( s );
        assertTrue( list.contains( s ) );
    }

//    @Ignore
    @Test
    public void contains_finds_element() {
        String[] s = {"F", "D", "H", "E", "A", "C", "B", "J", "G"};
        for ( String sx : s ) {
            list.add( sx );
        }
        assertTrue( "F is found", list.contains( "F" ) );
        // ensure search does not cause errors.
        assertFalse( "X is found", list.contains( "X" ) );
    }

    //@Ignore
    @Test
    public void linklist_is_fifo() {
        String[] s = {"A", "B", "C"};
        String[] actual = new String[s.length];
        for ( String sx : s ) {
            list.add( sx );
        }
        int idx = 0;
        while ( !list.isEmpty() ) {
            actual[idx++] = list.take();
        }
        Assert.assertArrayEquals( s, actual );
    }

    @Test
    public void never_index_added_returns_null() {
        assertNull( list.get( 4 ) );
        assertNull( list.get() );
    }

    @Test
    public void new_list_is_empty() {
        assertEquals( "new list is empty", 0, list.size() );
        assertTrue( "new list is empty", list.isEmpty() );
        list.add( "Hi" );
        assertEquals( "added one", 1, list.size() );
        assertFalse( "not empty anymore", list.isEmpty() );
    }

    @Test( expected = IllegalArgumentException.class )
    public void null_throws_exception() {
        list.add( null );
    }

    @Test
    public void sort_works_unique_elements() {
        String[] in = {"F", "D", "H", "E", "A", "C", "B", "J", "G"};
        String[] expected = {"A", "B", "C", "D", "E", "F", "G", "H", "J"};
        sort_works_sort_of( in, expected );
    }

    @Test
    public void sort_works_duplicates() {
        String[] in = {"F", "D", "H", "E", "A", "C", "E", "B", "J", "G"};
        String[] expected = {"A", "B", "C", "D", "E", "E", "F", "G", "H", "J"};
        sort_works_sort_of( in, expected );
    }

    public void sort_works_sort_of( String[] in, String[] expected ) {
        for ( String sx : in ) {
            list.add( sx );
        }
        Comparator<String> comp = ( String a, String b ) ->
                a.compareTo( b );
        System.out.println( "pre sort instance = " + list );
        list.sort( comp );
        System.out.println( "post sort instance = " + list );
        String[] actual = new String[list.size()];
        System.out.println( "actual.length = " + actual.length );
        int idx = 0;
        while ( idx < list.size() ) {
            actual[idx] = list.get( idx );
            //System.out.println( "actual[" + idx + "] = " + actual[ idx ] );
            idx++;
        }
        assertEquals( in.length, actual.length );
        System.out.println( "actual   " + Arrays.toString( actual ) );
        System.out.println( "expected " + Arrays.toString( expected ) );
        assertEquals( expected[3], actual[3] );
        assertArrayEquals( expected, actual );
        int sz = list.size();

        list.add( "Z" );
        assertEquals( "still works as list?", sz + 1, list.size() );
        assertTrue( "And has latest", list.contains( "Z" ) );
    }

    /**
     * See if it works with list of size 0 or 1 and does not throw excentions or
     * lose things
     */
    @Test
    public void sort_works_with_degenerate_list() {
        Comparator<String> comp = ( String a, String b ) ->
                a.compareTo( b );
        list.sort( comp );
        assertTrue( "still empty after sort", list.isEmpty() );
        list.add( "Z" );
        list.sort( comp );
        assertTrue( "the one element still in?", list.contains( "Z" ) );
        list.add( "A" );
        list.sort( comp );
        assertEquals( "A", list.get( 0 ) );
        assertEquals( "Z", list.get( 1 ) );
        assertEquals( 2, list.size() );
    }

    
    @Test
    public void take_from_start(){
        take_from_pos( 0);
    }

    @Test
    public void take_from_middle(){
        take_from_pos( testSet.length/2);
    }

    @Test
    public void take_from_end(){
        take_from_pos( testSet.length-1);
    }
    
    public void take_from_pos( int pos ) {
        fill( list );
        String r = list.take( pos );
        assertEquals( "correct element?", testSet[pos], r );
        assertEquals( "reduced size?", testSet.length - 1, list.size() );
        String[] actual = new String[list.size()];
        int idx = 0;
        while ( list.size() > 0 ) {
            System.out.println( "list = " + list );
            actual[idx++] = list.take();
        }
        String[] expected = Arrays.copyOf( testSet, testSet.length );
        for ( int i = pos; i < expected.length - 1; i++ ) {
            expected[i] = expected[i + 1];
        }
        expected = Arrays.copyOf( expected, expected.length - 1 );

        assertArrayEquals( expected, actual );
    }

    @Test
    public void take_last_element() {
        fill( list );
        int pos = testSet.length - 1;
        assertEquals( "take last fails", testSet[pos], list.take( pos ) );
        assertEquals( pos, list.size() );
    }

    @Test
    public void test_get() {
        fill( list );
        for ( int i = 0; i < testSet.length; i++ ) {
            assertEquals( "test element " + i + " not found", testSet[i], list
                    .get( i ) );
        }
    }

    static String[] testSet = {"A", "B", "C", "D", "E"};

    static void fill( SimpleList<String> list ) {
        for ( String sx : testSet ) {
            list.add( sx );
        }
    }

    @Test
    public void take_reduces_size() {
        fill( list );
        int cs = list.size();
        int p = 0;
        while ( list.size() > 0 ) {
            assertEquals( "received correct string", testSet[p++], list.take() );
            assertEquals( --cs, list.size() );
        }

    }

    @Test
    public void empty_list_iterator_works_ok() {
        Iterator<String> itr = list.iterator();
        assertFalse( itr.hasNext() );
    }

    @Test
    public void iterator_in_proper_order() {
        fill( list );
        String[] result = new String[list.size()];
        int i = 0;
        for ( String s : list ) {
            result[i++] = s;
        }
        assertArrayEquals( "arrays should be same ", testSet, result );
    }

    @Test
    public void to_string_is_not_empty_or_null() {
        assertNotNull( list.toString() );
        assertFalse( list.toString().isEmpty() );
        list.add( "Hello" );
        list.add( "World" );
        assertTrue( list.toString().startsWith( "[Hello, World]" ) );
    }

    @Test
    public void take_beyond_size_produces_null() {
        assertNull( list.take( 0 ) );
        assertNull( list.take( 1 ) );
    }

    @Test
    public void test_get_negativeIndex_returns_null() {
        assertNull( list.take( -2 ) );
    }

    @Test
    public void test_index_out_of_range_produces_null() {
        assertNull("Empty list", list.get());//fixed get method from linkedlist3
        fill( list );
        assertNull( "below zero", list.get( -2 ) );
        assertNull( "size", list.get( list.size() ) );
        assertNull( "size + 1", list.get( list.size() + 1 ) );

    }

    @Test
    public void test_contains_on_empty_list_is_false() {
        assertFalse( list.contains( "X" ) );
    }

    @Test
    public void take_beyond_returns_null() {
        fill( list );
        int p = list.size();
        assertNull( list.take( p ) );
        assertNull( list.take( p + 1 ) );

    }

    @Test
    public void get_beyond_returns_null() {
        fill( list );
        int p = list.size();
        assertNull( list.get( p ) );
        assertNull( list.get( p + 1 ) );

    }
}
