package nl.fontys.sebivenlo.statewalkertest;

import java.io.PrintStream;
import static nl.fontys.sebivenlo.statewalkertest.S.NULL;
import static nl.fontys.sebivenlo.statewalkertest.S.S33;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.anyBoolean;
import static org.mockito.Matchers.anyString;
import org.mockito.Mock;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Get coverage green.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
@RunWith( MockitoJUnitRunner.class )
public class STest {

    PrintStream out = mock( PrintStream.class );

    Dev d;

    public STest() {

        d = spy( new Dev( out ) );
    }

    @Test
    public void nullTest() {
        Context ctx = new Context( S.class, d ).initialize();
        NULL.e1( ctx );
        NULL.e2( ctx );
        NULL.e3( ctx );
        NULL.e4( ctx );
        NULL.e5( ctx );
        NULL.e6( ctx );
        NULL.e7( ctx );
        NULL.e8( ctx );
        NULL.e9( ctx );
        NULL.e10( ctx );
        NULL.e11( ctx );
        NULL.e12( ctx );
        NULL.e13( ctx );
        NULL.enter( ctx );
        NULL.exit( ctx );
        assertTrue( "reached this", true );
        verify( d, never() ).heater( anyBoolean() );

    }

    @Test
    public void testS33DeviceOutput() {
        Context ctx = new Context( S.class, d ).initialize();
        assertNotNull( out );
        assertNotNull( ctx );
        assertNotNull( S33 );
        assertNotNull( d );
        ctx.enterState( S33 );
        verify( d, times( 1 ) ).heater( true );

        verify( out, times( 1 ) ).println( anyString() );
    }

    @Test
    public void testSIIgnores() {
        Context ctx = new Context( S.class, d ).initialize();
        int before = S.getIgnoredEventCounter();
        ctx.e3();
        ctx.e4();
        ctx.e5();
        ctx.e6();
        ctx.e7();
        ctx.e9();
        ctx.e10();
        ctx.e11();
        ctx.e12();
        ctx.e13();
        assertEquals( before + 10, S.getIgnoredEventCounter() );
        assertEquals( "wrong State", "SI", ctx.logicalState() );
        ctx.e1();
        assertEquals( "wrong State", "S1.S11", ctx.logicalState() );
        before = S.getIgnoredEventCounter(); 
        ctx.e1();
        ctx.e2();
        ctx.e8();
        assertEquals( before+3, S.getIgnoredEventCounter() );
    }
}
