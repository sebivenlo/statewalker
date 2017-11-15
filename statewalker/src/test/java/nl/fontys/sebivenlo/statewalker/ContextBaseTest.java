package nl.fontys.sebivenlo.statewalker;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class ContextBaseTest {

    interface State extends StateBase<CTX, Dev, State> {
    };

    enum SB implements State {
        SB;

        @Override
        public State getNullState() {
            return this;
        }
    };

    class Dev implements Device<CTX, Dev, State> {
    }

    class Dev1 extends Dev {
    }

    class Dev2 extends Dev {
    }
    Dev dev = new Dev1();
    Dev dev2 = new Dev2();

    class CTX extends ContextBase<CTX, Dev, State> {

        public CTX( Dev d ) {
            super( SB.class );
            super.device = d;
        }

    }

    CTX cb = new CTX( dev );

    CTX cb2 = new CTX( dev2 );

    /**
     * For coverage.
     */
    @Test
    //@SuppressWarnings( "unchecked" )
    public void testSomeMethod() {
        cb.initialize();
        cb2.initialize();
        assertSame( dev, cb.getDevice() );
        assertNotSame( dev, cb2.getDevice() );
    }

    // Just for coverage
    @SuppressWarnings( {"unchecked","rawtypes"} )
    class CTXNoEnum extends ContextBase {

        public CTXNoEnum() {
            super( Object.class );
        }
    }

    // Just for coverage
    @Test
    public void testRawContext() {
        CTXNoEnum ctxNoEnum = new CTXNoEnum();
    }
}
