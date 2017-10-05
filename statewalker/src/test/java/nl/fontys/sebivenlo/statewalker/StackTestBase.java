package nl.fontys.sebivenlo.statewalker;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 * Base class for stack tests. The extending classes should provide the factory
 * method createInstance.
 *
 * @author hom
 */
public abstract class StackTestBase {

    abstract Stack<String> createInstance();
    static String[] testData = { "A", "B", "C", "D", "E", "F" };

    @Test
    public void an_new_stack_is_empty() {
        Stack<String> s = createInstance();
        assertTrue( "Fresh stack is empty", s
                .isEmpty() );
    }

    @Test
    public void stack_with_one_element_is_not_empty() {
        Stack<String> s = createInstance();
        s.push( "Hi" );
        assertFalse( "Hi on stack", s.isEmpty() );
    }

    @Test
    public void peek_returns_top() {
        Stack<String> s = createInstance();
        String h = "Hi";
        s.push( h );
        assertSame( "Hi on stack, same", h, s
                .peek() );
    }

    @Test
    public void pop_returns_and_shrinks() {
        Stack<String> s = createInstance();
        String h = "Hi";
        s.push( h );
        assertSame( "Hi on stack, same", h, s
                .pop() );
        assertTrue( "empty?", s.isEmpty() );

    }

    @Test
    public void is_it_a_real_stack() {
        System.out.println( "testData = " + Arrays
                .deepToString( testData ) );
        Stack<String> s = createInstance();
        System.out.println( "stack impl class = " + s.getClass().getTypeName() );
        //  add test data
        for ( String e : testData ) {
            s.push( e );
        }

        String[] received = new String[ testData.length ];
        int i = 0;
        while ( !s.isEmpty() && i < testData.length ) {
            received[ i++ ] = s.pop();
        }
        System.out.println( "received = " + Arrays
                .deepToString( received ) );
        List<String> l = Arrays.asList( received );
        Collections.reverse( l );
        received = l.toArray( received );
        System.out.println( "received = " + Arrays
                .deepToString( received ) );
        assertArrayEquals( "order check ", testData,
                received );
    }

    /**
     * Look into the LinkedStacTest.java source file to see how you should test
     * exception throwing in the programmers way, using try catch with two catch
     * clauses.
     * <p>
     * Quiz: explain how the actual throwing in this method is used in the
     * subclasses and how it can work.
     */
    @Test( expected = RuntimeException.class )
    public void empty_peek_should_bark() {
        Stack<String> s = createInstance();
        s.peek(); // should throw exception
    }
}
