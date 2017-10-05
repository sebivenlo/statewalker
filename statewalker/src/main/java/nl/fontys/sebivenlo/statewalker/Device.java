package nl.fontys.sebivenlo.statewalker;

/**
 * This empty interface should be extended or implemented to provide the type
 * that is used by the context and or state to operate the device. The 'device' should be understood
 * as any (sub)system that is operated under the control of the state machine implemented by the use
 * of this library.
 *
 * @param <C> Context for this state machine. This
 * @param <D> Device for all operations
 * @param <S> State to maintain.
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public interface Device<C extends ContextBase<C, D, S>, D extends Device<C, D, S>, S extends StateBase<C, D, S>> {

}
