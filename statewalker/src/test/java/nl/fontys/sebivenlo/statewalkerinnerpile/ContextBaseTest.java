package nl.fontys.sebivenlo.statewalkerinnerpile;

import java.util.function.Consumer;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class ContextBaseTest {

    Context ctx;

    @ParameterizedTest
    @MethodSource( "tData" )
    public void testMethod( Consumer<Context> action, String expectedState ) {
        ctx = new Context( SIP.class )
                .initialize()
                .setDebug( true );
        action.accept( ctx );
        assertThat( ctx.logicalState() ).isEqualTo( expectedState );
    }

    static Stream<Arguments> tData() {

        return Stream.of(
                p( ctx -> ctx.e1(), "SI.S2" ),
                p( ctx -> ctx.e2(), "SI.S3.S31" ),
                p( ctx -> ctx.e3(), "SI.S4.S41.S411" ),
                p( ctx -> ctx.e4(), "SI.S5.S51.S511.S5111" ),
                p( ctx -> ctx.e5(), "SI.S6.S61.S611.S6111.S61111" ),
                p( ctx -> ctx.e6(), "SI.S7.S71.S711.S7111.S71111.S711111" )
        );
    }

    static class P implements Arguments {

        final Consumer<Context> r;
        final String expectedState;

        public P( Consumer<Context> r, String expectedState ) {
            this.r = r;
            this.expectedState = expectedState;
        }

        @Override
        public Object[] get() {
            return new Object[]{ r, expectedState };
        }

    }

    /**
     * shorthand
     * @param r input 
     * @param expectedState sic
     * @return arguments
     */
    static P p( Consumer<Context> r, String expectedState ) {
        return new P( r, expectedState );
    }
}
