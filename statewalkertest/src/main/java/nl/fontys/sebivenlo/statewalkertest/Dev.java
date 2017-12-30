package nl.fontys.sebivenlo.statewalkertest;

import java.io.PrintStream;
import nl.fontys.sebivenlo.statewalker.Device;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class Dev implements Device<Context, Dev, State> {

    final PrintStream out;

    public Dev( PrintStream out ) {
        if (out==null) throw new NullPointerException("no null out");
        this.out = out;
    }

    public Dev() {
        this( System.out );
    }

    public void heater( boolean on ) {
        out.println( "heater on =" + on );
    }
}
