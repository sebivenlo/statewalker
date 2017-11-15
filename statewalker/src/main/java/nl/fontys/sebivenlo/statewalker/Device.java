package nl.fontys.sebivenlo.statewalker;

/**
 *
 * @param <C> Context for this state machine. This
 * @param <D> Device for all operations
 * @param <S> State to maintain.
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public interface Device<C extends ContextBase<C, D, S>, D extends Device<C, D, S>, S extends StateBase<C, D, S>> {

}
