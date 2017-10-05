
package nl.fontys.sebivenlo.statewalkertest;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * For coverage. Some methods are never called in the transition tests.
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class StateTest {
    
    public StateTest() {
    }

    @Test
    public void testDefaultMethods() {
        State x = StateEnum.A;
        Context ctx = new Context(StateEnum.class).initialize();
        ctx.addState( x);
        x.e1( ctx );
        x.e2( ctx );
        x.e3( ctx );
        x.e4( ctx );
        x.e5( ctx );
        x.e6( ctx );
        x.e7( ctx );
        x.e8( ctx );
        x.e9( ctx );
        x.e10( ctx );
        x.e11( ctx );
        x.e12( ctx );
        x.e13( ctx );
        x.enter( ctx );
        x.exit( ctx );
    }
    
}
