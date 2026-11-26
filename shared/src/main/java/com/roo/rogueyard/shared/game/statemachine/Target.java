package com.roo.rogueyard.shared.game.statemachine;

/** Represents the target of a transition. */
class Target {
    private final int id;
    private final Type type;

    /** A target pointing to the given state. */
    Target(State state) {
        this.id = state.id();
        this.type = Type.STATE;
    }

    /** A target pointing to the given graph. */
    Target(GraphRef graphRef) {
        this.id = graphRef.id();
        this.type = Type.GRAPH;
    }

    /** The id of the target state or graph. */
    int id() {
        return id;
    }

    /** The type of target. */
    Type type() {
        return type;
    }

    /** Returns this target as a state if that is this targets type. */
    State asState() {
        if (type != Type.STATE) {
            return null;
        }
        return new State(id);
    }

    /**
     * Returns this target as a graph reference if that is this targets type.
     */
    GraphRef asGraphRef() {
        if (type != Type.GRAPH) {
            return null;
        }
        return new GraphRef(id);
    }

    /** The type of a transition target. */
    enum Type {
        STATE,
        GRAPH,
    }
}
