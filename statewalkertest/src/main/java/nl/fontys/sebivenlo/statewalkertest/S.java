package nl.fontys.sebivenlo.statewalkertest;

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
            ctx.changeFromToState( "e2", this, S2 );
        }

        @Override
        public void e8( Context ctx ) {
            ctx.changeFromToState( "e8", this, S3,S32 );
        }
    }, S1 {
        @Override
        public State getInitialState() {
            return S11;
        }

        @Override
        public void e3( Context ctx ) {
            ctx.changeFromToState( "e3", this, SI );
        }

        @Override
        public void e4( Context ctx ) {
            ctx.innerTransition( "e4", this, S12 );
        }

        @Override
        public void e9( Context ctx ) {
            ctx.changeFromToState( "e9", this, S3 );
        }
    }, S2 {
        @Override
        public void e5( Context ctx ) {
            ctx.changeFromToState( "e5", this, S1 );
        }

    }, S22 {
        @Override
        public void e6( Context ctx ) {
            ctx.changeFromToState( "e6", this, S21 );
        }
    }, S11, S12, S21 {
        @Override
        public void e6( Context ctx ) {
            ctx.changeFromToState( "e6", this, S22 );
        }
    }, S221 {
        @Override
        public void e7( Context ctx ) {
            ctx.changeFromToState( "e7", this, S222 );
        }
    }, S222 {
        @Override
        public void e13( Context ctx ) {
            ctx.changeFromToState( "e13", S2, S1 );
        }
    },
    S3{
    
        @Override
        public void e12( Context ctx ) {
            ctx.changeFromToState( "e12", this, S1 );
        }
    }, S31 {
        @Override
        public void e10( Context ctx ) {
            ctx.changeFromToState( "e10", this, S32 );
        }
    }, S32{
        @Override
        public void e10( Context ctx ) {
            ctx.changeFromToState( "e10", this, S33 );
        }
    }, S33{
        @Override
        public void e10( Context ctx ) {
            ctx.changeFromToState( "e10", this, S31 );
        }
    
    }, S331{
        @Override
        public void e11( Context ctx ) {
            ctx.changeFromToState( "e11", this, S332 );
        }
    }, S332{
        @Override
        public void e11( Context ctx ) {
            ctx.changeFromToState( "e11", this, S331);
        }
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

    /**
     * All instances give the same answer.
     *
     * @return
     */
    static S[] s1Subtates = { S11, S12 };

    @Override
    public State getNullState() {
        return NULL;
    }

    private static final EnumMap<S, S> initialMap = new EnumMap<>( S.class );

    static {
        initialMap.put( NULL, SI );
        initialMap.put( S1, S11 );
        initialMap.put( S2, S21 );
        initialMap.put( S22, S221 );
        initialMap.put( S3, S31 );
        initialMap.put( S33, S331 );
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

    private static final EnumSet<S> isDeepHist = EnumSet.<S>of( S3 );

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
