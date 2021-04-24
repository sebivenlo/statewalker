package nl.fontys.sebivenlo.statewalker;

import java.util.logging.Level;
import java.util.logging.Logger;
import static org.assertj.core.api.Assertions.*;
import static nl.fontys.sebivenlo.statewalker.ContextBaseTest.SB.*;
import nl.fontys.sebivenlo.statewalkertest.MyHandler;
import org.junit.jupiter.api.Test;

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

        }, SB3;

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
            setDevice( d );
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
        assertThat( cb.getDevice() ).isSameAs( dev );
        assertThat( cb2.getDevice() ).isNotSameAs( dev );
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
        assertThat( ctxNoEnum ).isNotNull();
    }

    @Test
    public void testLeaveAndPopExists() {
        entered = false;
        exited = false;
        cb.initialize();
        cb.addStateInternal( SB2 );
        assertThat( entered ).as( " did not enter" ).isTrue();
        cb.leaveState( SB2 );
        assertThat( exited ).isTrue();
        assertThat( exited ).as( " did not exit" ).isTrue();
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
        cb.changeFromToState( "try", SB1, SB2, SB3 );
        assertThat( mh.lastMessage )
                .as( "wrong message " )
                .contains( "from" );
        assertThat( mh.lastMessage )
                .as( "wrong message " )
                .contains( "event" );

        assertThat(  mh.lastParams).as("wrong state ").contains( "SB1" );
        assertThat(  mh.lastParams).as("wrong state ").contains( "SB2.SB3" );
        mh.flush();
        cb.changeFromToState( "try", SB2, SB1 );

        assertThat( mh.lastParams).contains( "SB2.SB3" );
        assertThat( mh.lastParams).contains( "SB1" );
    }
}
