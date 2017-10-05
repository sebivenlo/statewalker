package nl.fontys.sebivenlo.statewalker;

/**
 * Methods that should be available in all states to make the state walker api
 * work.
 *
 * Because (the current version of ) statewalker heavily relies the use of java
 * {@code enum} types, we mandate the aspects of enum, namely {@code ordinal} to
 * be implemented. This is automatic if the user also uses enum.
 *
 * Same for the "NULL" state, which is used as the catch all sate, put at the
 * top (bottom in the stack) of the state hierarchy, to catch (as in implement)
 * all event methods. All other methods provide a default implementation (Java 8
 * needed) and need no override.
 *
 * The implementor of the implementing class is advised to add another level of
 * inheritance with an interface extending this interface and give default
 * implementations of enter and leave, then application specific. These
 * implementations can be empty too, but should use the application specific
 * context.
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 * @param <C> Context
 * @param <D> Device
 * @param <S> StateBase
 */
public interface StateBase<C extends ContextBase<C, D, S>, D extends Device<C, D, S>, S extends StateBase<C, D, S>> {

    /**
     * This method is called whenever a state is entered.
     *
     * @param ctx the context for all operations.
     */
    default void enter( C ctx ) {
    }

    /**
     * This method is called whenever a state is left.
     *
     * @param ctx the context for all operations.
     */
    default void exit( C ctx ) {
    }

    /**
     * This method is used to ask all states for their initial sub state, so
     * that history can be implemented inside the framework.
     *
     * @return the initial sub-state of this state.
     */
    default S getInitialState() {
        return null;
    }

    /**
     * Get the ordinal number (the identity) of the state.
     *
     * @return the id of the state in an enum declaration.
     */
    int ordinal();

    /**
     * Is the initial state actually a history state?. If an initial state has
     * the role of history state, the child states of a state are remembered, so
     * that when the state machine returns to this state, the remembered sub
     * state is also resumed.
     *
     * @return isHistory
     */
    default boolean isInitialStateHistory() {
        return false;
    }

    /**
     * Is the initial state actually a deep history state?. If an initial state has
     * the role of history state, the child states and all of the child's
     * sub states, recursively, are remembered, so that when the state machine
     * returns to this state, the remembered sub states are also resumed.
     * 
     * 
     * @return isHistory
     */
    default boolean isInitialStateDeepHistory() {
        return false;
    }

    /**
     * Get the default state that is to be on the bottom of the stack and
     * 'implements' all event methods with empty bodies, or at least methods
     * that do not all super.method nor reach down in the stack.
     *
     * @return the null state associated with this implementation.
     */
    S getNullState();
}
