package nl.fontys.sebivenlo.statewalkertest;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public enum StateEnum implements State {
    A,
    NULL{
        @Override
        public void enter( Context ctx ) {
        }

        @Override
        public void exit( Context ctx ) {
        }

        @Override
        public void e13( Context ctx ) {
        }

        @Override
        public void e12( Context ctx ) {
        }

        @Override
        public void e11( Context ctx ) {
        }

        @Override
        public void e10( Context ctx ) {
        }

        @Override
        public void e9( Context ctx ) {
        }

        @Override
        public void e8( Context ctx ) {
        }

        @Override
        public void e7( Context ctx ) {
        }

        @Override
        public void e6( Context ctx ) {
        }

        @Override
        public void e5( Context ctx ) {
        }

        @Override
        public void e4( Context ctx ) {
        }

        @Override
        public void e3( Context ctx ) {
        }

        @Override
        public void e2( Context ctx ) {
        }

        @Override
        public void e1( Context ctx ) {
        }
    };

    @Override
    public State getNullState() {
        return NULL;
    }

}
