package util;

import java.util.Comparator;
import java.util.Iterator;

public interface SimpleList<E> 
    extends Iterable<E> {

    int size();

    default boolean isEmpty() {
        return 0 == size();
    }

    void add( E e );

    E get( int idx );

    E get();

    E take();

    E take( int i );

    boolean contains( E e );

    void sort( Comparator<? super E> c );

    @Override
    public Iterator<E> iterator();

}
