package com.roo.rogueyard.shared.game.statemachine;

/**
 * Represents a reference to a graph within its graph bundle.
 */
public class GraphRef {
    private final int id;

    GraphRef(int id) {
        this.id = id;
    }

    /** The id of the graph within its graph bundle. */
    int id() {
        return id;
    }
}
