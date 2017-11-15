package nl.fontys.sebivenlo.statewalkertest;

import nl.fontys.sebivenlo.statewalker.Device;

/**
 *
 * @author Pieter van den Hombergh {@code <p.vandenhombergh@fontys.nl>}
 */
public class Dev implements Device<Context, Dev, State> {

    public void heater(boolean on) {
        System.out.println("heater on ="+on);
    }
}
