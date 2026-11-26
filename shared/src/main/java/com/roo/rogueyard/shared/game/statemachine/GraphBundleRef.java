package com.roo.rogueyard.shared.game.statemachine;

/**
 * Represents a reference to a graph bundle.
 */
public class GraphBundleRef {
    private final int id;
    private final Class<? extends StateMachineContext> ctxType;

    /**
     * Creates a graph bundle reference with the given id and context type.
     * The context type should be the same as for GraphBundle this is
     * referencing.
     */
    GraphBundleRef(int id, Class<? extends StateMachineContext> ctxType) {
        this.id = id;
        this.ctxType = ctxType;
    }

    /** The id of the graph bundle. */
    int id() {
        return id;
    }

    /** The context type of the graph bundle. */
    public Class<? extends StateMachineContext> ctxType() {
        return ctxType;
    }
}
