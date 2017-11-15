package nl.fontys.sebivenlo.statewalkertest;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * For coverage.
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class ContextTest {
    
    public ContextTest() {
    }

    @Test
    public void testGetDevice() {
        Context ctx= new Context(S.class, new Dev());
        assertNotNull( "has device", ctx.getDevice());
    }
    
}
