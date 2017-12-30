package nl.fontys.sebivenlo.statewalker;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import static org.junit.Assert.*;
import static nl.fontys.sebivenlo.statewalker.ContextBaseTest.SB.*;
import nl.fontys.sebivenlo.statewalkertest.MyHandler;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class ContextBaseTest {

    interface State extends StateBase<CTX, Dev, State> {
    };
    static boolean exited = false;
    static boolean entered = false;

    enum SB implements State {
        SB, SB1, SB2 {
            @Override
            public void enter( CTX ctx ) {
                ContextBaseTest.entered = true;
            }

            @Override
            public void exit( CTX ctx ) {
                ContextBaseTest.exited = true;
            }

        },SB3;

        @Override
        public State getNullState() {
            return SB;
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
    @SuppressWarnings( { "unchecked", "rawtypes" } )
    class CTXNoEnum extends ContextBase {

        public CTXNoEnum() {
            super( Object.class );
        }
    }

    // Just for coverage
    @Test
    public void testRawContext() {
        CTXNoEnum ctxNoEnum = new CTXNoEnum();
        assertNotNull( ctxNoEnum );
    }

    @Test
    public void testLeaveAndPopExists() {
        entered = false;
        exited = false;
        cb.initialize();
        cb.addStateInternal( SB2 );
        assertTrue( " did not enter", entered );
        cb.leaveState( SB2 );
        assertTrue( exited );
        assertTrue( " did not exit", exited );
    }

    @Test
    public void testLogger() {
        Logger ctxLogger = Logger.getLogger( ContextBase.class.getName() );
        MyHandler mh = new MyHandler();
        ctxLogger.addHandler( mh );
        ctxLogger.setLevel( Level.FINE );
        cb.initialize();
        mh.flush();
        cb.addStateInternal( SB1 );
        mh.flush();
        cb.changeFromToState( "try", SB1, SB2,SB3 );
        assertTrue( "wrong message " + mh.lastMessage, mh.lastMessage.contains( 
                "from" ) );
        assertTrue( "wrong message " + mh.lastMessage, mh.lastMessage.contains( 
                "event" ) );

        assertTrue( "wrong state ", mh.lastParams.contains( "SB1" ) );
        assertTrue( "wrong state ", mh.lastParams.contains( "SB2.SB3" ) );
        mh.flush();
        cb.changeFromToState( "try", SB2, SB1 );

        assertTrue( "wrong state ", mh.lastParams.contains( "SB2.SB3" ) );
        assertTrue( "wrong state ", mh.lastParams.contains( "SB1" ) );
    }
}
