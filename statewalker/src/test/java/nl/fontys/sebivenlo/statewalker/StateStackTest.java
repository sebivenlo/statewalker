package nl.fontys.sebivenlo.statewalker;

import java.util.ArrayList;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import org.junit.Test;

/**
 *
 * @author hom
 */
public class StateStackTest extends StackTestBase {

    @Override
    Stack<String> createInstance() {
        return new StateStack<>();
    }

    @Test
    public void watch_it_grow() {

        StateStack<String> stack = ( StateStack<String> ) createInstance();
        int ic = stack.capacity();
        System.out.println( "cap=" + stack.capacity() );
        String[] testData = { "A", "B", "C", "D", "E" };
        for ( String s : testData ) {
            stack.push( s );
        }

        System.out.println( "size=" + stack.size() );
        System.out.println( "cap=" + stack.capacity() );
        assertEquals( "grow by factor 2", 2 * ic, stack.capacity() );
    }

    /**
     * This the testers way of ensuring proper exception throwing. If the
     * exception is not thrown or the wrong exception is thrown, the test will
     * fail (red).
     */
    @Test( expected = ArrayIndexOutOfBoundsException.class )
    @Override
    public void empty_peek_should_bark() {
        super.empty_peek_should_bark();
    }

    @Test
    public void after_pop_elements_are_nulled_out() {
        StateStack<String> stack = ( StateStack<String> ) createInstance();
        String[] testData = { "A", "B", "C", "D", "E" };
        for ( String s : testData ) {
            stack.push( s );
        }

        // remove all elements
        for ( int i = testData.length - 1; i >= 0; i-- ) {
            String x = testData[ i ];
            assertEquals( "Datum off stack", x, stack.pop() );
            assertFalse( "and gone", stack.contains( x ) );
        }
    }

    @Test
    public void cover_contains() {
        StateStack<String> stack = ( StateStack<String> ) createInstance();
        String[] testData = { "A", "B", "C", "D", "E" };
        for ( String s : testData ) {
            stack.push( s );
        }
        assertFalse( "Not found", stack.contains( "Goner" ) );
        assertTrue( "In Stack", stack.contains( testData[ 2 ] ) );

    }

    @Test
    public void testHas() {

        StateStack<String> stack = ( StateStack<String> ) createInstance();
        String[] testData = { "A", "B", "C", "D", "E" };
        for ( String s : testData ) {
            stack.push( s );
        }
        assertFalse( "Not found", stack.has( "G" ) );
    }

    @Test
    public void testPeekDown() {

        StateStack<String> stack = ( StateStack<String> ) createInstance();
        String[] testData = { "A", "B", "C", "D", "E" };
        for ( String s : testData ) {
            stack.push( s );
        }
        assertEquals( "found", "C", stack.peekDownFrom( "D", 1 ) );
    }

    @Test
    public void testPeekDownNotFound() {

        StateStack<String> stack = ( StateStack<String> ) createInstance();
        String[] testData = { "A", "B", "C", "D", "E" };
        for ( String s : testData ) {
            stack.push( s );
        }
        assertNull( "Not found", stack.peekDownFrom( "A", 1 ) );
    }

    @Test
    public void testPeekUp() {
        StateStack<String> stack = ( StateStack<String> ) createInstance();
        String[] testData = { "A", "B", "C", "D", "E" };
        for ( String s : testData ) {
            stack.push( s );
        }
        assertEquals( "found", "E", stack.peekDownFrom( "D", -1 ) );

    }

    @Test
    public void testPeekUpNotFound() {
        StateStack<String> stack = ( StateStack<String> ) createInstance();
        String[] testData = { "A", "B", "C", "D", "E" };
        for ( String s : testData ) {
            stack.push( s );
        }
        assertNull( "Notfound", stack.peekDownFrom( "D", -2 ) );

    }

    @Test
    public void testPeekUpOverTop() {
        StateStack<String> stack = new StateStack<String>( 5 );
        String[] testData = { "A", "B", "C", "D", "E" };
        for ( String s : testData ) {
            stack.push( s );
        }
        assertNull( "Notfound", stack.peekDownFrom( "E", -1 ) );
    }
    
    @Test
    public void testAbove(){
        StateStack<String> stack = new StateStack<String>( 5 );
        String[] testData = { "A", "B", "C", "D", "E" };
        for ( String s : testData ) {
            stack.push( s );
        }
        ArrayList<String> store = new ArrayList<>();
        assertTrue(stack.above( "E", store ).isEmpty());
        assertTrue("not empty",stack.above( "Z", store ).isEmpty());
    }
}
