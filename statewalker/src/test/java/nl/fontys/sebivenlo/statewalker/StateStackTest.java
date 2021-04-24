package nl.fontys.sebivenlo.statewalker;

import java.util.ArrayList;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import org.junit.jupiter.api.Test;

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
    public void testSize() {
        StateStack<String> s = new StateStack<>( 4 );
        assertThat( s.size() ).isEqualTo( 0 );
        s.push( "Hi" );
        assertThat( s.size() ).isEqualTo( 1 );
    }

    @Test
    public void testCapacity() {
        StateStack<String> s = new StateStack<>( 4 );
        assertThat( s.capacity() ).isEqualTo( 4 );
    }

    @Test
    public void watch_it_grow() {

        StateStack<String> stack = (StateStack<String>) createInstance();
        int ic = stack.capacity();
        System.out.println( "cap=" + stack.capacity() );
        fillStack( stack );

        System.out.println( "size=" + stack.size() );
        System.out.println( "cap=" + stack.capacity() );
        assertThat( stack.capacity() ).as( "grow by factor 2" ).isEqualTo( 2
                * ic );
    }

//    /**
//     * This the testers way of ensuring proper exception throwing. If the
//     * exception is not thrown or the wrong exception is thrown, the test will
//     * fail (red).
//     */
//    @Test
//    @Override
//    public void empty_peek_should_bark() {
//        assertThatThrownBy( () -> {
//            super.empty_peek_should_bark();
//        } ).isExactlyInstanceOf( ArrayIndexOutOfBoundsException.class );
//    }

    @Test
    public void after_pop_elements_are_nulled_out() {
        StateStack<String> stack = (StateStack<String>) createInstance();
        fillStack( stack );
        // remove all elements
        for ( int i = testData.length - 1; i >= 0; i-- ) {
            String x = testData[ i ];
            assertThat( stack.pop() ).as( "Datum off stack" ).isEqualTo( x );
            assertThat( stack.contains( x ) ).as( "and gone" ).isFalse();
        }
    }

    @Test
    public void cover_contains() {
        StateStack<String> stack = (StateStack<String>) createInstance();
        String[] testData = { "A", "B", "C", "D", "E" };
        for ( String s : testData ) {
            stack.push( s );
        }
        assertThat( stack.contains( "Goner" ) ).as( "Not found" ).isFalse();
        assertThat( stack.contains( testData[ 2 ] ) ).as( "In Stack" ).isTrue();

    }

    @Test
    public void testHas() {

        StateStack<String> stack = (StateStack<String>) createInstance();
        fillStack( stack );
        assertThat( stack.has( "A" ) ).isTrue();
        assertThat( stack.has( "C" ) ).isTrue();
        assertThat( stack.has( "G" ) ).isFalse();
    }

    @Test
    public void testPeekDown() {

        StateStack<String> stack = (StateStack<String>) createInstance();
        fillStack( stack );
        //assertEquals("found", "C", stack.peekDownFrom("D", 1));
        assertThat( stack.peekDownFrom( "D", 1 ) ).isEqualTo( "C" );

    }

    @Test
    public void testPeekDownFirst() {

        StateStack<String> stack = (StateStack<String>) createInstance();
        fillStack( stack );
        assertThat( stack.peekDownFrom( "E", 2 ) ).isEqualTo( "C" );

    }

    @Test
    public void testPeekDownBottom() {

        StateStack<String> stack = (StateStack<String>) createInstance();
        fillStack( stack );
        assertThat( stack.peekDownFrom( "B", 2 ) ).isNull();

    }

    @Test
    public void testPeekDownNotFound() {

        StateStack<String> stack = (StateStack<String>) createInstance();
        fillStack( stack );
        assertThat( stack.peekDownFrom( "Z", 1 ) ).as( "Not found" ).isNull();
    }

    @Test
    public void testPeekUp() {
        StateStack<String> stack = (StateStack<String>) createInstance();
        fillStack( stack );
        assertThat( stack.peekDownFrom( "D", -1 ) ).isEqualTo( "E" );

    }

    @Test
    public void testPeekUpNotFound() {
        StateStack<String> stack = (StateStack<String>) createInstance();
        fillStack( stack );
        assertThat( stack.peekDownFrom( "D", -2 ) ).isNull();
    }

    @Test
    public void testPeekUpOverTop() {
        StateStack<String> stack = new StateStack<String>( 5 );
        fillStack( stack );
        assertThat( stack.peekDownFrom( "E", -1 ) ).isNull();
    }

    static final String[] testData = { "A", "B", "C", "D", "E" };

    private void fillStack( StateStack<String> stack ) {
        for ( String s : testData ) {
            stack.push( s );
        }
    }

    @Test
    public void testAbove() {
        StateStack<String> stack = new StateStack<String>( 5 );
        fillStack( stack );
        ArrayList<String> store = new ArrayList<>();
        assertThat( stack.above( "E", store ) ).isEmpty();
        assertThat( stack.above( "Z", store ) ).isEmpty();
    }

    @Test
    public void testAboveMore() {
        StateStack<String> stack = new StateStack<String>( 5 );
        fillStack( stack );
        ArrayList<String> store = new ArrayList<>();
        assertThat( stack.above( "A", store ) ).hasSize( 4 );

    }
}
