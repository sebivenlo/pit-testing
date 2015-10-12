/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author hom
 */
public class SimpleArrayListTest extends ListTestBase {

    SimpleList<String> aList = new SimpleArrayList<>( 8 );

    @Test
    public void add_should_grow_list() {
        aList.add( "A" );
        assertEquals( 1, aList.size() );
    }

    @Test
    public void auto_resize_works() {
        System.out.println( "auto_resize_works" );
        String[] s = { "F", "D", "H", "E", "A", "C", "B", "J", "G" };
        int oldCap = ( ( SimpleArrayList ) aList ).capacity();
        for ( String sx : s ) {
            aList.add( sx );
        }
        int actualCap = ( ( SimpleArrayList ) aList ).capacity();
        System.out.println( "actualCap = " + actualCap );
        for ( String sx : s ) {
            aList.add( sx );
        }
        actualCap = ( ( SimpleArrayList ) aList ).capacity();
        assertTrue( actualCap > oldCap );
        for ( String sx : s ) {
            aList.add( sx );
        }
        actualCap = ( ( SimpleArrayList ) aList ).capacity();
        // test shrink too
        while ( aList.size() > 6 ) {
            aList.take();
        }

        assertTrue( "not shrinking", actualCap
                > ( ( SimpleArrayList ) aList ).capacity() );
    }

    /**
     * This test verifies that the references in the storage are nulled out on
     * removal. This sut provides a method to produce a copy of the storage.
     * This copy can be inspected.
     */
    @Test
    public void test_no_references_remain_dangling() {
        SimpleArrayList<String> mList = new SimpleArrayList<>();
        fill(mList);
        // take out a few elements
        mList.take(1); mList.take(0);
        assertEquals("correct size ", testSet.length-2, mList.size());
        Object[] a = mList.storageArray();
        // verify that everything above size is nulled out
        for (int i=mList.size(); i < a.length; i++){
            assertNull("ëlement "+i+" not nulled", a[i]);
        }
    }

    @Override
    SimpleList<String> newInstance() {
        return new SimpleArrayList<>();
    }
}
