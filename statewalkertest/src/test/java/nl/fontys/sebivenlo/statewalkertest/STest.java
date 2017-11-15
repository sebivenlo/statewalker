package nl.fontys.sebivenlo.statewalkertest;

import static nl.fontys.sebivenlo.statewalkertest.S.NULL;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.runner.RunWith;
import static org.mockito.Matchers.anyBoolean;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import org.mockito.runners.MockitoJUnitRunner;

/**
 * Get coverage green.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
@RunWith( MockitoJUnitRunner.class )
public class STest {

    @Mock
    Dev d;

    public STest() {
        d = new Dev();
    }

    @Test
    public void nullTest() {
        Context ctx = new Context( S.class, d );
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

}
