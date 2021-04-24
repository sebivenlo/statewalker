package nl.fontys.sebivenlo.statewalkertest;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;


/**
 * For coverage.
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class ContextTest {
    
    public ContextTest() {
    }

    @Test
    public void testGetDevice() {
        Context ctx= new Context(S.class);
        assertThat(ctx.getDevice()).as("has device").isNotNull();
    }
    
}
