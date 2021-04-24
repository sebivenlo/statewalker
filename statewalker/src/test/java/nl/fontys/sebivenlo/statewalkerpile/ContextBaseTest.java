package nl.fontys.sebivenlo.statewalkerpile;

import java.util.function.Consumer;
import java.util.stream.Stream;
import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.*;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class ContextBaseTest {

    Context ctx;

    @ParameterizedTest
    @MethodSource( "tData" )
    public void testMethod( Consumer<Context> event, String expectedState ) {
        ctx = new Context( S.class )
                .initialize()
                .setDebug( true );
        event.accept( ctx );

        assertThat( ctx.logicalState() ).isEqualTo( expectedState );

    }

    static Stream<Arguments> tData() {
        return Stream.of(
                p( ( ctx ) -> ctx.e1(), "S1.S11" ),
                p( ( ctx ) -> ctx.e2(), "S2.S21" ),
                p( ( ctx ) -> ctx.e3(), "S3.S31.S311" ),
                p( ( ctx ) -> ctx.e4(), "S4.S41.S411.S4111" ),
                p( ( ctx ) -> ctx.e5(), "S5.S51.S511.S5111.S51111" ),
                p( ( ctx ) -> ctx.e6(), "S6.S61.S611.S6111.S61111.S611111" )
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

    static P p( Consumer<Context> r, String expectedState ) {
        return new P( r, expectedState );
    }
}
