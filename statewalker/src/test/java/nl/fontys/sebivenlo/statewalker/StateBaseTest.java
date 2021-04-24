package nl.fontys.sebivenlo.statewalker;

import static org.assertj.core.api.Assertions.*;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class StateBaseTest {

    interface S extends StateBase<TestContext, Dev, S> {
    }

    enum TestState implements S {
        X;

        @Override
        public TestState getNullState() {
            return null;
        }
    }

    static class Dev implements Device<TestContext, Dev, S> {
    }

    static class TestContext extends ContextBase<TestContext, Dev, S> {

        public TestContext( Class<?> stateClass ) {
            super( stateClass );
        }

        final Dev dev = new Dev();

        @Override
        public Dev getDevice() {
            return dev;
        }

    }

    S sb = TestState.X;
    TestContext cb = new TestContext( TestState.class ).initialize();

    /**
     * Only for coverage, the methods have empty bodies.
     */
    @Test
    public void coverageComplete() {
        assertThat(sb.getInitialState() ).isNull();
        assertThat(sb.isInitialStateHistory() ).isFalse();
        assertThat(sb.isInitialStateDeepHistory() ).isFalse();
        sb.enter( cb );
        sb.exit( cb );
    }
}
