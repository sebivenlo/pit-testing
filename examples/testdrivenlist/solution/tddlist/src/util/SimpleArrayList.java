package util;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Array backed implementation of SimpleList.
 *
 * @author hom
 * @param <E> element type
 */
public class SimpleArrayList<E> implements SimpleList<E> {

    private E[] storage;
    public static final int INITIAL_CAPACITY = 10;
    private int size = 0; //last insert.

    /**
     * Create a list with given initial capacity
     *
     * @param startCap start capacity
     */
    @SuppressWarnings( "unchecked" )
    public SimpleArrayList( int startCap ) {
        this.storage = ( E[] ) new Object[ startCap ];
    }

    /**
     * Create a list with default capacity. The list will have a capacity of 10.
     */
    public SimpleArrayList() {
        this( INITIAL_CAPACITY );
    }

    @Override
    public void add( E e ) {
        if ( null == e ) {
            throw new IllegalArgumentException( "nulls not allowed" );
        }
        ensureCapacity();
        storage[ size++ ] = e;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public E get() {
        return get( 0 );
    }

    @Override
    public E get( int idx ) {
        if ( idx >= size || idx < 0 ) {
            return null;
        } else {
            return storage[ idx ]; //To change body of generated methods, choose Tools | Templates.
        }
    }

    @Override
    public E take( int i ) {
        E result = get( i );
        if ( result == null ) {
            return null;
        }
        System.arraycopy( storage, i + 1, storage, i + 0,
                          size - 1 );
        // null out element
        storage[ i + size - 1 ] = null;
        size--;
        shrinkToSize();
        return result;
    }

    @Override
    public E take() {
        return take( 0 );
    }

    @Override
    public boolean contains( E e ) {
        boolean result = false;
        for ( int i = 0; i < size; i++ ) {
            if ( storage[ i ].equals( e ) ) {
                return true;
            }
        }
        return result;
    }

    /**
     * (Variant of) selection sort.
     *
     * @param c comparator to use in sort.
     */
    //@Override
    @Override
    public void sort( Comparator<? super E> c ) {
        // ensure the precondition of the sort loop.
        if ( size < 2 ) {
            return;
        }
        //  pre  : a[] has the n integer values, n > 0.
        //  post : a[] is sorted and contains
        //         all the original values.
        int k = 0;
        int n = size;
        // invariant I1: a[0 .. k-1] is sorted and
        // (A j: k <= j < n: max(a[0 .. k-1]) <= a[j])
        // Termination: k = n-1
        while ( k != n - 1 ) {
            int i = k + 1, j = k;
            // invariant I2: a[j] = min(a[h]) for k <= h < i)
            // Termination: i ==  n
            while ( i != n ) {
                if ( c.compare( storage[ i ], storage[ j ] ) < 0 ) {
                    j = i;
                }; // j = current
                i++;
            }
            swap( storage, k, j ); // assumes swap is given
            k++;
        }
    }

    private static void swap( Object[] a, int k, int j ) {
        Object temp = a[ k ];
        a[ k ] = a[ j ];
        a[ j ] = temp;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder( "[" );
        final String comma = ", ";
        final String empty = "";
        String separator = empty;
        for ( int i = 0; i < size; i++ ) {
            sb.append( separator ).append( storage[ i ].toString() );
            separator = comma;
        }
        sb.append( "]" );
        return sb.toString();
    }

    private void ensureCapacity() {
        if ( size + 1 < storage.length ) {
            return;
        }
        // add 20% , but ensure cap becomes at least INITIAL_CAPACITY %
        int newLength = Math.max( INITIAL_CAPACITY, ( 6 * storage.length ) / 5 );
        storage = Arrays.copyOf( storage, newLength );
    }

    int capacity() {
        return storage.length;
    }

    /**
     * if size &lt; capacity &amp;&amp; capacity &gt; 10, reduce size to size +
     * 20%.
     */
    private void shrinkToSize() {
        if ( size * 2 < storage.length && storage.length > 10 ) {
            int newLength = Math.max( INITIAL_CAPACITY, ( size * 6 ) / 5 );
            storage = Arrays.copyOf( storage, newLength );
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int size = SimpleArrayList.this.size;
            int position = 0;

            @Override
            public boolean hasNext() {
                return position < size;
            }

            @Override
            public E next() {
                return storage[ position++ ];
            }
        };
    }

    E[] storageArray() {
        return Arrays.copyOf( storage, storage.length );
    }

}
