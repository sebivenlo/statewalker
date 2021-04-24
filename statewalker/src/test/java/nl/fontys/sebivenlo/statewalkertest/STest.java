package nl.fontys.sebivenlo.statewalkertest;

import static nl.fontys.sebivenlo.statewalkertest.S.NULL;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import org.junit.jupiter.api.Test;

/**
 * Get coverage green.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class STest {

    public STest() {
    }

    @Test
    public void nullTest() {
        Context ctx = new Context( S.class );
        assertThatCode( () -> {
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
        } ).doesNotThrowAnyException();
    }

}
