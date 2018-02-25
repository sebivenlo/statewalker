package nl.fontys.sebivenlo.statewalkerinnerpile;

import java.util.EnumMap;
import java.util.EnumSet;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
enum SIP implements State {
    SI {
        @Override
        public void e1( Context ctx ) {
            ctx.innerTransition( "e1", this, S2 );
        }

        @Override
        public void e2( Context ctx ) {
            ctx.innerTransition( "e2", this, S3, S31 );
        }

        @Override
        public void e3( Context ctx ) {
            ctx.innerTransition( "e3", this, S4, S41, S411 );
        }

        @Override
        public void e4( Context ctx ) {
            ctx.innerTransition( "e4", this, S5, S51, S511, S5111 );
        }

        @Override
        public void e5( Context ctx ) {
            ctx.innerTransition( "e5", this, S6, S61, S611, S6111, S61111 );
        }

        @Override
        public void e6( Context ctx ) {
            ctx.innerTransition( "e4", this, S7, S71, S711, S7111, S71111,
                    S711111 );
        }
    }, S1 {
    }, S2 {
        @Override
        public void e5( Context ctx ) {
            ctx.changeFromToState( "e5", this, S1 );
        }

    }, S11, S12, S21 {
    }, S221 {
        @Override
        public void e7( Context ctx ) {
            ctx.changeFromToState( "e7", this, S222 );
        }
    }, S222,
    S3 {

    }, S31, S32, S33 {

    }, S331 {

    }, S332 {

    },
    S321 {

    },
    S322 {

    },
    S311 {
        @Override
        public void e10( Context ctx ) {
            ctx.changeFromToState( "e10", this, S321 );
            super.e10( ctx ); //To change body of generated methods, choose Tools | Templates.
        }
    },
    S3111 {
    },
    S4 {
    },
    S41 {
    },
    S411 {
    },
    S4111 {
    },
    S5 {
    },
    S51 {
    },
    S511 {
    },
    S5111 {
    },
    S51111 {
    },
    S6 {
    },
    S61 {
    },
    S611 {
    },
    S6111 {
    },
    S61111 {
    },
    S611111 {
    }, S7, S71, S711, S7111, S71111, S711111, S7111111,
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

    static SIP[] s1Subtates = { S11, S12 };

    /**
     * All instances give the same answer.
     *
     * @return
     */
    @Override
    public State getNullState() {
        return NULL;
    }

    private static final EnumMap<SIP, SIP> initialMap = new EnumMap<>( SIP.class );

    static {
        initialMap.put( NULL, SI );
        initialMap.put( SI, S1 );
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

    private static final EnumSet<SIP> isHist = EnumSet.<SIP>noneOf(SIP.class );

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

    private static final EnumSet<SIP> isDeepHist = EnumSet.<SIP>of( S3, S32 );

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
