/**
 * A enum bases state machine implementation of the <strong>GoF State</strong>
 * pattern.
 * <p>
 * The package is designed with ease of use and type safety in mind, and
 * therefor can not completely avoid the complexity that goes with it. The
 * complexity left for the user is declaring the application specific Context,
 * Device, and State as a trisome in the generic declaration. To even further
 * reduce the complexity, the user is advised to declare State as an
 * intermediate interface that adds the application specific methods.
 * <p>
 * In the example, app, the declarations of AppContext, AppDevice, and AppState
 * could then be as follows:
 * <pre>
 * {@code
 * class AppContext extends ContextBase<AppContext, AppDevice, State>
 * class AppDevice implements Device<AppContext, AppDevice, State>
 * interface State extends StateBase<AppContext, AppDevice, State>
 * enum AppState implements State
 *
 * }
 * </pre>
 * <p>
 * <p>
 * When declaring your classes in this way you will by definition have some
 * cyclic dependencies, which cannot be avoided really easily, so it might take
 * some effort to convince the compiler it is okay. It may help to temporarily
 * turn off the IDE feature to compile on save.</p>
 * <p>
 * <p>
 * The standard use is also shown in the class diagram below, where the
 * app-package is the users application package. The {@code <C,D,S,>} generic
 * tokens should be understood as alphabetic mnemonics for Context, Device and
 * State.</p>
 * <p>
 * <img src='doc-files/statewalker.svg' alt='statewalker usage class diagram'>
 * <br>
 * <p>
 * <p>
 * <h2>Implementation detail.</h2>
 * The classes have been designed to reduce
 * complexity as much as possible, in particular using the generic annotation
 * needed.
 * <p>
 * This applies more to the use of the package then to the package it self,
 * which cannot avoid some unchecked warnings in its implementation. In the
 * implementation some use of {@code @SuppresWarnings("unchecked")} cannot be
 * avoided, because we use arrays as internal storage and the varargs parameters
 * list is an array too. We consider using varargs to be applicable here. If the
 * user considers that wasteful in the sense of array object creation, he is
 * advised to declare static constants of type array of state and use these
 * values as parameter in the transition implementing methods. This will work
 * exactly as intended, and has the benefit that the array allocation is done
 * only once, at class loading time.
 * </p>
 * <p>
 * <h2>Transition types</h2>
 * State walker provides the following transition types:
 * <p>
 * <h3>simple transition</h3>
 * <img src='doc-files/_1.svg'  alt='simple transistion' >
 * <p>
 * <pre>
 * <code>
 *   A {
 *     {@literal @}Override
 *      public void e( Context ctx ) {
 *          ctx.changeFromToState( "e", this, B );
 *      }
 *   }
 * </code>
 * </pre>
 * <p>
 * <h3>Transition to sub-state</h3>
 * <img src='doc-files/_2.svg'  alt='simple transistion' >
 * <br>
 * Note how the target states are passed in super-sub order.
 * <pre>
 * <code>
 *   A {
 *     {@literal @}Override
 *      public void e( Context ctx ) {
 *          ctx.changeFromToState( "e", this, B1, B2 );
 *      }
 *   }
 * </code>
 * </pre>
 * <p>
 * <h3>Transition from sub-state</h3>
 * <img src='doc-files/_3a.svg'  alt='simple transistion' ><br>
 * Note that the super state (A1) of A2 is mentioned in the transition call, not <b>this</b>.
 * <pre>
 * <code>
 *   A2 {
 *     {@literal @}Override
 *      public void e( Context ctx ) {
 *          ctx.changeFromToState( "e", A1, B );
 *      }
 *   }
 * </code>
 * </pre>
 * <p>
 * <h3>Transition from super-state</h3>
 * <img src='doc-files/_3b.svg'  alt='simple transistion' ><br>
 * Note that this transition is implemented in super state A1, so <b>this</b> is applicable here as
 * it refers to that super state.
 * call.
 * <pre>
 * <code>
 *   A1 {
 *     {@literal @}Override
 *      public void e( Context ctx ) {
 *          ctx.changeFromToState( "e", this, B );
 *      }
 *   }
 * </code>
 * </pre>
 * <p>
 * <h3>Transition to sub-state implemented in super state</h3>
 * <img src='doc-files/_5.svg'  alt='simple transistion' ><br>
 * Note the use of the innerTranstion call, which only affects sub-states.
 * <p>
 * <pre>
 * <code>
 *   B1 {
 *      {@literal @}Override
 *      public void e( Context ctx ) {
 *          ctx.innerTransition( "e", this, B22 );
 *     }
 *   }
 * </code>
 * </pre>
 * <p>
 * <h3>Transition to sub-state implemented in super state</h3>
 * <img src='doc-files/_6.svg'  alt='simple transistion' ><br>
 * Initials states are specified and implemented with a map lookup in the state
 * enum source file. At initialisation, ContextBase calls the
 * {@code getInitialState()} once to create its own mapping. Note that the map
 * lookup is an implementation detail that avoids having to overwrite the
 * {@code getInitialState()} method in every state. The map used is an enum-map
 * which has O(1) lookup properties, like an array access.
 * <p>
 * <pre>
 * <code>
 *   A1 {
 *     {@literal @}Override
 *      public void e( Context ctx ) {
 *          ctx.changeFromToState( "e", this, B1 );
 *      }
 *   }
 *
 * private static final EnumMap{@literal <S, S>} initialMap = new EnumMap{@literal <>}( S.class );
 *
 *   static {
 *       initialMap.put( NULL, SI );
 *       initialMap.put( B1, B2 );
 *   }
 *
 *  {@literal @}Override
 *   public State getInitialState() {
 *       return initialMap.get( this );
 *   }
 * </code>
 * </pre>
 * <p>
 * <h3>Shallow and Deep History</h3>
 * Deep and shallow history are implemented with sets and two methods
 * that do a lookup in these sets. Both sets (different names of course) are implemented as
 * follows:
 * <pre>
 * <code>
 *  private static final EnumSet{@literal <S>} isHist = EnumSet.{@literal <S>}of( B1 );
 *  // with lookup
 *   {@literal @}Override
 *    public boolean isInitialStateHistory() {
 *        return isHist.contains( this );
 *    }
 * </code>
 * </pre>
 * <p>
 * With this mapping (and lookup by the ContextBase) the state implementation is
 * the same as in the examples with initial states. The initial states map is
 * also used in this case, taking the states that the history pseudo state
 * points at as value and the super state of the construct as the key.
 * <br>
 * Diagrams:<br>
 * <img src='doc-files/_7.svg'  alt='simple transistion' ><br>
 * shallow history.<br>
 * <img src='doc-files/_8.svg'  alt='simple transistion' ><br>
 * deep history.
 * <hr>
 *
 * @author Pieter van den Hombergh
 * @author Jeroen Beulen
 * @author Sander Lemans
 *
 */
package nl.fontys.sebivenlo.statewalker;
