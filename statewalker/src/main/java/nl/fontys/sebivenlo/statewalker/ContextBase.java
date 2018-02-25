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
    private D device;

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
                S is = ( (S) aEnum ).getInitialState();
                List<S> iss = new ArrayList<>();
                iss.add( is );
                this.deepHistoryMap.add( iss );
            }
            nullState = ( (S) enums[ 0 ] ).getNullState();
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
        return (C) this;
    }

    /**
     * Check for deep history activation.
     */
    private void doDeepHistory() {
        S topState = stack.peek();
        List<S> hist = deepHistoryMap.get( topState.ordinal() );
        for ( S s : hist ) {
            if ( s == null ) {
                break;
            }
            addStateInternal( s );
        }
    }

    /**
     * Enter the given state and check if history should be activated.
     *
     * @param state to enter
     */
    public final void enterState( S state ) {
        addStateInternal( state );

        doDeepHistory();
    }

    /**
     * Enter the given states and check if history should be activated.
     *
     * @param s1 to enter
     * @param s2 to enter
     */
    public final void enterState( S s1, S s2 ) {
        addStateInternal( s1, s2 );

        doDeepHistory();
    }

    /**
     * Enter the given states and check if history should be activated.
     *
     * @param s1 state to enter
     * @param s2 state to enter
     * @param s3 state to enter
     */
    public final void enterState( S s1, S s2, S s3 ) {
        addStateInternal( s1, s2, s3 );
        doDeepHistory();
    }

    /**
     * Enter the given states and check if history should be activated.
     *
     * @param s1 state to enter
     * @param s2 state to enter
     * @param s3 state to enter
     * @param s4 state to enter
     */
    public final void enterState( S s1, S s2, S s3, S s4 ) {
        addStateInternal( s1,s2,s3,s4 );
        doDeepHistory();
    }

    /**
     * Enter the given states and check if history should be activated.
     *
     * @param s1 state to enter
     * @param s2 state to enter
     * @param s3 state to enter
     * @param s4 state to enter
     * @param s5 state to enter
     */
    public final void enterState( S s1, S s2, S s3, S s4, S s5 ) {
        addStateInternal( s1 ,s2,s3,s4,s5);
        doDeepHistory();
    }

    /**
     * Enter a specific state hierarchy. enterState adds all states listed to
     * the current state stack and invokes the {@code enter()} method for each
     * state.
     *
     * If the top most state has a history stored, that history is resumed in
     * the same way as the states were added as per previous description.
     *
     * @param s1 end state
     * @param s2 end state
     * @param s3 end state
     * @param s4 end state
     * @param s5 end state
     * @param extrastates inner/top most end state
     */
    @SafeVarargs
    @SuppressWarnings( "unchecked" )
    public final void enterState( S s1, S s2, S s3, S s4, S s5, S... extrastates ) {
        addStateInternal( s1,s2,s3,s4,s5, extrastates );
        doDeepHistory();
    }

    /**
     * Add and enter state.
     *
     * @param childState to add and enter.
     */
    final void addStateInternal( S childState ) {
        stack.push( childState );
        getTopState().enter( (C) this );
    }

    /**
     * Add and enter state.
     *
     * @param childState to add and enter.
     * @param s2 end state
     */
    final void addStateInternal( S childState, S s2 ) {
        stack.push( childState );
        getTopState().enter( (C) this );
        stack.push( s2 );
        getTopState().enter( (C) this );
    }

    /**
     * Add and enter state.
     *
     * @param childState to add and enter.
     * @param s2 end state
     */
    final void addStateInternal( S childState, S s2, S s3 ) {
        stack.push( childState );
        getTopState().enter( (C) this );
        stack.push( s2 );
        getTopState().enter( (C) this );
        stack.push( s3 );
        getTopState().enter( (C) this );
    }

    /**
     * Add and enter state.
     *
     * @param childState to add and enter.
     * @param s2 end state
     */
    final void addStateInternal( S childState, S s2, S s3, S s4 ) {
        stack.push( childState );
        getTopState().enter( (C) this );
        stack.push( s2 );
        getTopState().enter( (C) this );
        stack.push( s3 );
        getTopState().enter( (C) this );
        stack.push( s4 );
        getTopState().enter( (C) this );
    }

    /**
     * Add and enter state.
     *
     * @param childState to add and enter.
     * @param s2 end state
     */
    final void addStateInternal( S childState, S s2, S s3, S s4, S s5 ) {
        stack.push( childState );
        getTopState().enter( (C) this );
        stack.push( s2 );
        getTopState().enter( (C) this );
        stack.push( s3 );
        getTopState().enter( (C) this );
        stack.push( s4 );
        getTopState().enter( (C) this );
        stack.push( s5 );
        getTopState().enter( (C) this );
    }

    /**
     * Add and enter state.
     *
     * @param childState to add and enter.
     * @param s2 end state
     */
    final void addStateInternal( S childState, S s2, S s3, S s4, S s5,
            S... extras ) {
        stack.push( childState );
        getTopState().enter( (C) this );
        stack.push( s2 );
        getTopState().enter( (C) this );
        stack.push( s3 );
        getTopState().enter( (C) this );
        stack.push( s4 );
        getTopState().enter( (C) this );
        stack.push( s5 );
        getTopState().enter( (C) this );
        for ( S extra : extras ) {
            stack.push( extra );
            getTopState().enter( (C) this );

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
                    + "' because it is not active\n"
                    + " current state is " + logicalState() );
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
        stack.peek()
                .exit( (C) this );
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
     * @param event name for the transition
     * @param start state to leave
     * @param es states to enter in order given.
     */
    public final void changeFromToState( String event, S start, S es ) {
        String ls = preLog();
        leaveState( start );
        enterState( es );
        postLog( ls, event );
    }

    /**
     * Do a full transition from a current state to a new state with 2
     * sub-states. For the start state the leave method is invoked, for each
     * state in endState the enter state is invoked.
     *
     * @param event name for the transition
     * @param start state to leave
     * @param es1 end state
     * @param es2 end state
     */
    //@SafeVarargs
    public final void changeFromToState( String event, S start, S es1, S es2 ) {
        String ls = preLog();
        leaveState( start );
        addStateInternal( es1 );
        enterState( es2 );
        postLog( ls, event );
    }

    /**
     * Do a full transition from a current state to a new state 2 sub-states.
     * For the start state the leave method is invoked, for each state in
     * endState the enter state is invoked.
     *
     * @param event name for the transition
     * @param start state to leave
     * @param es1 end state
     * @param es2 end state
     * @param es3 end state
     */
    //@SafeVarargs
    public final void changeFromToState( String event, S start, S es1, S es2,
            S es3 ) {
        String ls = preLog();
        leaveState( start );
        addStateInternal( es1, es2 );
        enterState( es3 );
        postLog( ls, event );
    }

    /**
     * Do a full transition from a current state to a new state with optional
     * sub-states. For the start state the leave method is invoked, for each
     * state in endState the enter state is invoked.
     *
     * @param event name for the transition
     * @param start state to leave
     * @param es1 end state
     * @param es2 end state
     * @param es3 end state
     * @param es4 end state
     */
    //@SafeVarargs
    public final void changeFromToState( String event, S start, S es1, S es2,
            S es3, S es4 ) {
        String ls = preLog();
        leaveState( start );
        addStateInternal( es1, es2, es3 );
        enterState( es4 );
        postLog( ls, event );
    }

    /**
     * Do a full transition from a current state to a new state with 4
     * sub-states. For the start state the leave method is invoked, for each
     * state in endState the enter state is invoked.
     *
     * @param event name for the transition
     * @param start state to leave
     * @param es1 end state
     * @param es2 end state
     * @param es3 end state
     * @param es4 end state
     * @param es5 end state
     */
    public final void changeFromToState( String event, S start, S es1, S es2,
            S es3, S es4, S es5 ) {
        String ls = preLog();
        leaveState( start );
        addStateInternal( es1, es2, es3, es4 );
        enterState( es5 );
        postLog( ls, event );
    }

    /**
     * Do a full transition from a current state to a sequence of 5 new states
     * with optional sub-states. For the start state the leave method is
     * invoked, for each state in endState the enter state is invoked.
     *
     * @param event name for the transition
     * @param start state to leave
     * @param es1 end state
     * @param es2 end state
     * @param es3 end state
     * @param es4 end state
     * @param es5 end state
     * @param extraStates end states
     */
    @SafeVarargs
    public final void changeFromToState( String event, S start, S es1, S es2,
            S es3, S es4, S es5, S... extraStates ) {
        String ls = preLog();
        leaveState( start );
        enterState( es1, es2, es3, es4, es5, extraStates );
        postLog( ls, event );
    }

    private void postLog( String ls, String event ) {
        if ( LOGGER.isLoggable( Level.FINE ) ) {
            LOGGER.log( Level.FINE, "from {0}, event [{1}] to {2}",
                    new Object[]{ ls, event, logicalState() } );
        }
    }

    private String preLog() {
        String ls = "";
        if ( LOGGER.isLoggable( Level.FINE ) ) {
            ls = logicalState();
        }
        return ls;
    }

    /**
     * Do a transition with out leaving this state. The sub states of state are
     * left, then the endStates are entered in the order given.
     *
     * @param event name for the transition
     * @param start state that is NOT left
     * @param endState new inner state.
     */
    public final void innerTransition( String event, S start, S endState ) {
        String ls = preLog();
        leaveSubStates( start );
        enterState( endState );
        postLog( ls, event );
    }

    /**
     * Do a transition with out leaving this state. The sub states of state are
     * left, then the endStates are entered in the order given.
     *
     * @param event name for the transition
     * @param start state that is NOT left
     * @param es1 end state
     * @param es2 end state
     */
    public final void innerTransition( String event, S start, S es1, S es2 ) {
        String ls = preLog();
        leaveSubStates( start );
        enterState( es1, es2 );
        postLog( ls, event );
    }

    /**
     * Do a transition with out leaving this state. The sub states of state are
     * left, then the endStates are entered in the order given.
     *
     * @param event name for the transition
     * @param start state that is NOT left
     * @param es1 end state
     * @param es2 end state
     * @param es3 end state
     */
    public final void innerTransition( String event, S start, S es1, S es2,
            S es3 ) {
        String ls = preLog();
        leaveSubStates( start );
        enterState( es1, es2, es3 );
        postLog( ls, event );
    }

    /**
     * Do a transition with out leaving this state. The sub states of state are
     * left, then the endStates are entered in the order given.
     *
     * @param event name for the transition
     * @param start state that is NOT left
     * @param endState new inner state.
     */
    public final void innerTransition( String event, S start, S es1, S es2,
            S es3, S es4 ) {
        String ls = preLog();
        leaveSubStates( start );
        enterState( es1, es2, es3, es4 );
        postLog( ls, event );
    }

    /**
     * Do a transition with out leaving this state. The sub states of state are
     * left, then the endStates are entered in the order given.
     *
     * @param event name for the transition
     * @param start state that is NOT left
     * @param endState new inner state.
     */
    public final void innerTransition( String event, S start, S es1, S es2,
            S es3, S es4, S es5 ) {
        String ls = preLog();
        leaveSubStates( start );
        enterState( es1, es2, es3, es4, es5 );
        postLog( ls, event );
    }

    /**
     * Do a transition with out leaving this state. The sub states of state are
     * left, then the endStates are entered in the order given.
     *
     * @param event name for the transition
     * @param start state that is NOT left
     * @param endState new inner state.
     */
    @SafeVarargs
    public final void innerTransition( String event, S start, S es1, S es2,
            S es3, S es4, S es5, S... extraStates ) {
        String ls = preLog();
        leaveSubStates( start );
        enterState( es1, es2, es3, es4, es5, extraStates );
        postLog( ls, event );
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
        return (C) this;
    }

    /**
     * Set the device for this context and return this context.
     *
     * @param device to set
     * @return this context
     */
    public C setDevice( D device ) {
        this.device = device;
        return (C) this;
    }

}
