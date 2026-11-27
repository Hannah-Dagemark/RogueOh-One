package com.roo.rogueyard.shared.game.statemachine;

/**
 * Represents a reference to a graph bundle.
 */
public class GraphBundleRef<T extends StateMachineContext<T>> {
    private final int id;

    /** Creates a graph bundle reference with the given id. */
    GraphBundleRef(int id) {
        this.id = id;
    }

    /** The id of the graph bundle. */
    int id() {
        return id;
    }
}
