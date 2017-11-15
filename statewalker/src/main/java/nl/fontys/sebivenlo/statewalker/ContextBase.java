package nl.fontys.sebivenlo.statewalker;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Base class for all statewalker state machine contexts.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 * @param <C> Context for this state machine. This
 * @param <D> Device for all operations
 * @param <S> State to maintain.
 */
public abstract class ContextBase<C extends ContextBase<C, D, S>, D extends Device<C, D, S>, S extends StateBase<C, D, S>> {

    private final StateStack<S> stack = new StateStack<>( 6 );
    private final S nullState;
    private boolean debug = false;
    private static final Logger LOGGER = Logger.getLogger( ContextBase.class.
            getName() );
    private final List<List<S>> deepHistoryMap;
    protected D device;

    /**
     * Create a context for a state machine with states of type stateClass.
     *
     * @param stateClass the class of all states.
     */
    @SuppressWarnings( "unchecked" )
    public ContextBase( Class<?> stateClass ) {
        if ( stateClass.isEnum() ) {
            Object[] enums = stateClass.getEnumConstants();
            //this.initialMap = new ArrayList<>( enums.length );
            this.deepHistoryMap = new ArrayList<>( enums.length );
            for ( Object aEnum : enums ) {
                S is = ( ( S ) aEnum ).getInitialState();
                List<S> iss = new ArrayList<>();
                iss.add( is );
                this.deepHistoryMap.add( iss );
            }
            nullState = ( ( S ) enums[ 0 ] ).getNullState();
        } else {
            nullState = null;
            deepHistoryMap = null;
        }
    }

    /**
     * Initialise this context. After setup, some arrangements may have to take
     * place before this state machine can commence.
     *
     * @return this context
     */
    @SuppressWarnings( "unchecked" )
    public C initialize() {
        if ( null != nullState ) {
            this.stack.push( nullState );
            final S initialState = nullState.getInitialState();
            if ( initialState != null ) {
                this.enterState( initialState );
            }
        }
        return ( C ) this;
    }

    /**
     * Enter a specific state hierarchy. enterState adds all states listed to
     * the current state stack and invokes the {@code enter()} method for each
     * state.
     *
     * If the top most state has a history stored, that history is resumed in
     * the same way as the states were added as per previous description.
     *
     * @param state states to enter.
     */
    @SafeVarargs
    @SuppressWarnings( "unchecked" )
    public final void enterState( S... state ) {
        for ( S s : state ) {
            addState( s );
        }

        S topState = stack.peek();

        List<S> hist = deepHistoryMap.get( topState.ordinal() );
        for ( S s : hist ) {
            if ( s == null ) {
                break;
            }
            addState( s );
        }
    }

    /**
     * Add and invoke {@code enter()} on each state in the list. At the end of
     * this method, the state hierarchy has "grown" with the states in the list
     * state.
     *
     * @param state to add.
     */
    @SafeVarargs
    @SuppressWarnings( "unchecked" )
    public final void addState( S... state ) {
        for ( S childState : state ) {
            stack.push( childState );
            childState.enter( ( C ) this );
        }
    }

    /**
     * Top state (child-most) state is the place where to enter the events.
     *
     * @return the top most (inner most/sub state most) state.
     */
    protected final S getTopState() {
        return stack.peek();
    }

    /**
     * Leave sub states of state, but not state itself.
     *
     * @param state for which the current sub-states should be left.
     */
    @SuppressWarnings( "unchecked" )
    public final void leaveSubStates( S state ) {
        if ( !stack.has( state ) ) {
            throw new IllegalArgumentException( "Cannot leave state '" + state
                    + "'because it is not active" );
        }
        S topState = stack.peek();
        while ( topState != state ) {
            leaveAndPop();
            topState = stack.peek();
        }
    }

    /**
     * Leave a state and all its sub-states in natural order.
     *
     * @param state to leave.
     */
    @SuppressWarnings( "unchecked" )
    public final void leaveState( S state ) {
        if ( state.isInitialStateHistory() ) {
            List<S> childState = getFirstChild( state );
            this.deepHistoryMap.set( state.ordinal(), childState );
        } else if ( state.isInitialStateDeepHistory() ) {
            deepHistoryMap.set( state.ordinal(), getChildren( state ) );
        }
        leaveSubStates( state );
        leaveAndPop();
    }

    @SuppressWarnings( "unchecked" )
    private void leaveAndPop() {
        stack.peek().exit( ( C ) this );
        stack.pop();
    }

    /**
     * Get the device for all operations.
     *
     * @return the device
     */
    public D getDevice() {
        return device;
    }

    /**
     * Get the super state of a state.
     *
     * @param state for which the super state should be retrieved.
     *
     * @return the super (parent) state of state.
     */
    public final S superState( S state ) {
        return stack.peekDownFrom( state, 1 );
    }

    /**
     * Do a full transition from a current state to a new state with optional
     * sub-states. For the start state the leave method is invoked, for each
     * state in endState the enter state is invoked.
     *
     * @param event    name for the transition
     * @param start    state to leave
     * @param endState states to enter in order given.
     */
    @SafeVarargs
    public final void changeFromToState( String event, S start, S... endState ) {
        String ls = "";
        if ( LOGGER.isLoggable( Level.FINE ) ) {
            ls = logicalState();
        }
        leaveState( start );
        enterState( endState );
        if ( LOGGER.isLoggable( Level.FINE ) ) {
            LOGGER.log( Level.FINE, "from {0}, event [{1}] to {2}",
                    new Object[]{ ls, event, logicalState() } );
        }
    }

    /**
     * Do a transition with out leaving this state. The sub states of state are
     * left, then the endStates are entered in the order given.
     *
     * @param event    name for the transition
     * @param start    state that is NOT left
     * @param endState new inner state.
     */
    @SafeVarargs
    public final void innerTransition( String event, S start, S... endState ) {
        String ls = "";
        if ( LOGGER.isLoggable( Level.FINE ) ) {
            ls = logicalState();
        }
        leaveSubStates( start );
        enterState( endState );
        if ( LOGGER.isLoggable( Level.FINE ) ) {
            LOGGER.log( Level.FINE, "from {0}, event [{1}] to {2}",
                    new Object[]{ ls, event, logicalState() } );
        }
    }

    /**
     * Produce a string to identify the sequence or nesting of states. The NULL
     * state is left out.
     *
     * @return a string describing the full state value of this context.
     */
    public final String logicalState() {
        return stack.logicalState();
    }

    /**
     * Get the first active sub state of this parent or super state.
     *
     * @param parent for which the child must be produced.
     *
     * @return The child, if any.
     */
    List<S> getFirstChild( S parent ) {
        List<S> result = new ArrayList<>();
        result.add( stack.peekDownFrom( parent, -1 ) );
        return result;
    }

    /**
     * Get the (current) child states of a parent state.
     *
     * @param parent of the children
     *
     * @return the children in a list.
     */
    List<S> getChildren( S parent ) {
        return stack.above( parent, deepHistoryMap.get( parent.ordinal() ) );
    }

    /**
     * Is debug set.
     *
     * @return true is debugging.
     */
    public boolean isDebug() {
        return debug;
    }

    /**
     * Set the debug flag for more debugging output.
     *
     * @param d flag value
     *
     * @return this context.
     */
    @SuppressWarnings( "unchecked" )
    public C setDebug( boolean d ) {
        debug = d;
        return ( C ) this;
    }
}
