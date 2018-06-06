package nl.fontys.sebivenlo.statewalkerpile;

import java.util.EnumMap;
import java.util.EnumSet;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
enum S implements State {
    SI {
        @Override
        public void e1( Context ctx ) {
            ctx.changeFromToState( "e1", this, S1 );
        }

        @Override
        public void e2( Context ctx ) {
            ctx.changeFromToState( "e2", this, S2, S21 );
        }

        @Override
        public void e3( Context ctx ) {
            ctx.changeFromToState( "e3", this, S3, S31, S311 );
        }

        @Override
        public void e4( Context ctx ) {
            ctx.changeFromToState( "e4", this, S4, S41, S411, S4111 );
        }

        @Override
        public void e5( Context ctx ) {
            ctx.changeFromToState( "e5", this, S5, S51, S511, S5111, S51111 );
        }

        @Override
        public void e6( Context ctx ) {
            ctx.changeFromToState( "e6", this, S6, S61, S611, S6111, S61111,
                    S611111 );
        }
    }, S1, S11, S12,
    S2, S21,S22,
    S3, S31, S311, S3111,S32,
    S4, S41, S411, S4111,
    S5, S51, S511, S5111, S51111,
    S6, S61, S611, S6111, S61111, S611111 {
    },
    /**
     * Null has trivial (empty bodies) implementations for all event methods.
     */
    NULL {
        @Override
        public void e1( Context ctx ) {
        }

        @Override
        public void e2( Context ctx ) {
        }

        @Override
        public void e3( Context ctx ) {
        }

        @Override
        public void e4( Context ctx ) {
        }

        @Override
        public void e5( Context ctx ) {
        }

        @Override
        public void e6( Context ctx ) {
        }

        @Override
        public void e7( Context ctx ) {
        }

        @Override
        public void e8( Context ctx ) {
        }

        @Override
        public void e9( Context ctx ) {
        }

        @Override
        public void e10( Context ctx ) {
        }

        @Override
        public void e11( Context ctx ) {
        }

        @Override
        public void e12( Context ctx ) {
        }

        @Override
        public void e13( Context ctx ) {
        }

        @Override
        public void exit( Context ctx ) {
        }

        @Override
        public void enter( Context ctx ) {
        }
    };

    static S[] s1Subtates = { S11, S12 };

    /**
     * All instances give the same answer.
     *
     * @return
     */
    @Override
    public State getNullState() {
        return NULL;
    }

    private static final EnumMap<S, S> initialMap = new EnumMap<>( S.class );

    static {
        initialMap.put( NULL, SI );
        initialMap.put( S1, S11 );
        initialMap.put( S2, S21 );
        initialMap.put( S3, S31 );
//        initialMap.put( S33, S331 );
//        initialMap.put( S32, S321 );
    }

    /**
     * Get the initial sub state of a state (if any)
     *
     * @return the sub state or null if none exists or is defined.
     */
    @Override
    public State getInitialState() {
        return initialMap.get( this );
    }

    private static final EnumSet<S> isHist = EnumSet.<S>of( S22 );

    /**
     * Whether or not a state maintains history of its sub state.
     *
     * @return true if the state maintains history of its direct (shallow) sub
     * state.
     */
    @Override
    public boolean isInitialStateHistory() {
        return isHist.contains( this );
    }

    private static final EnumSet<S> isDeepHist = EnumSet.<S>of( S3, S32 );

    /**
     * Whether or not a state maintains history of its sub state.
     *
     * @return true if the state maintains history of its direct (shallow) sub
     * state.
     */
    @Override
    public boolean isInitialStateDeepHistory() {
        return isDeepHist.contains( this );
    }
}
