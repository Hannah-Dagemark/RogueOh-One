package com.roo.rogueyard.shared.game.statemachine;

/**
 * Represents a state within a graph.
 */
public class State {
    private final int id;

    State(int id) {
        this.id = id;
    }

    /** The id of the state within its graph. */
    int id() {
        return id;
    }
}
