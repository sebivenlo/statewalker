package nl.fontys.sebivenlo.statewalkertest;

import nl.fontys.sebivenlo.statewalker.StateBase;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public interface State extends StateBase<Context, Dev, State> {
    // <editor-fold formatter-rule="keep-off">
    default void  e1( Context ctx ) { ctx.superState( this ).e1( ctx ); }
    default void  e2( Context ctx ) { ctx.superState( this ).e2( ctx ); }
    default void  e3( Context ctx ) { ctx.superState( this ).e3( ctx ); }
    default void  e4( Context ctx ) { ctx.superState( this ).e4( ctx ); }
    default void  e5( Context ctx ) { ctx.superState( this ).e5( ctx ); }
    default void  e6( Context ctx ) { ctx.superState( this ).e6( ctx ); }
    default void  e7( Context ctx ) { ctx.superState( this ).e7( ctx ); }
    default void  e8( Context ctx ) { ctx.superState( this ).e8( ctx ); }
    default void  e9( Context ctx ) { ctx.superState( this ).e9( ctx ); }
    default void e10( Context ctx ) { ctx.superState( this ).e10( ctx ); }
    default void e11( Context ctx ) { ctx.superState( this ).e11( ctx ); }
    default void e12( Context ctx ) { ctx.superState( this ).e12( ctx ); }
    default void e13( Context ctx ) { ctx.superState( this ).e13( ctx ); }

    @Override
    default void exit( Context ctx ) {}

    @Override
    default void enter( Context ctx ) {}
    // </editor-fold> 
}
