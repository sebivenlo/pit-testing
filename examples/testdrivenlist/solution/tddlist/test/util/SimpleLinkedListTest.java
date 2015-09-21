package util;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import org.junit.Test;

/**
 *
 * @author hom
 */
public class SimpleLinkedListTest extends ListTestBase {

    @Override
    SimpleList<String> newInstance() {
        return new SimpleLinkedList<>();

    }

}
