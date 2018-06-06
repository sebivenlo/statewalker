package nl.fontys.sebivenlo.statewalkerinnerpile;

import java.util.function.Consumer;
import org.junit.After;
import static org.junit.Assert.assertEquals;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
@RunWith( Parameterized.class )
public class ContextBaseTest {

    Context ctx;
    final P p;

    public ContextBaseTest( P p ) {
        this.p = p;
    }
    @Test
    public void testMethod() {
        p.r.accept( ctx );
        assertEquals(p.expectedState, ctx.logicalState());
                
    }
    @Before
    public void setup() {
        ctx = new Context( SIP.class ).initialize()
                .setDebug( true );
    }

    @After
    public void tearDown() {
        ctx = null;
    }

    @Parameters
    public static Object[][] data() {
        return new Object[][]{
            { p( (ctx) -> ctx.e1(), "SI.S2" ) },
            { p( (ctx) -> ctx.e2(), "SI.S3.S31" ) },
            { p( (ctx) -> ctx.e3(), "SI.S4.S41.S411" ) },
            { p( (ctx) -> ctx.e4(), "SI.S5.S51.S511.S5111" ) },
            { p( (ctx) -> ctx.e5(), "SI.S6.S61.S611.S6111.S61111" ) },
            { p( (ctx) -> ctx.e6(), "SI.S7.S71.S711.S7111.S71111.S711111" ) },
        };
    }

    static class P {

        final Consumer<Context> r;
        final String expectedState;

        public P( Consumer<Context> r, String expectedState ) {
            this.r = r;
            this.expectedState = expectedState;
        }
    }

    static P p( Consumer<Context> r, String expectedState ) {
        return new P( r, expectedState );
    }
}
