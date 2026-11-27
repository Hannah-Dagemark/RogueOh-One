package com.roo.rogueyard.shared.game.statemachine;

/**
 * Represents an objects current position in a state machine graph and any
 * variables that are required to know what transition to take next.
 */
public abstract class StateMachineContext<T extends StateMachineContext<T>> {
    private final GraphBundleRef<T> graphBundleRef;

    private GraphRef graphRef = null;
    private State state = null;
    private float timeInState = 0;

    /** Creates a state machine context within the given graph bundle. */
    public StateMachineContext(GraphBundleRef<T> graphBundleRef) {
        this.graphBundleRef = graphBundleRef;
    }

    /** The graph bundle this context lives within. */
    public GraphBundleRef<T> graphBundleRef() {
        return graphBundleRef;
    }

    /** The current graph. */
    public GraphRef graphRef() {
        return graphRef;
    }

    /** Set the current graph. */
    public void setGraphRef(GraphRef graphRef) {
        this.graphRef = graphRef;
    }

    /** The current state. */
    public State state() {
        return state;
    }

    /** Set the current state. */
    public void setState(State state) {
        this.state = state;
    }

    /** The elapsed time since entering the current state. */
    public float timeInState() {
        return timeInState;
    }

    /** Set the elapsed time since entering the current state. */
    public void setTimeInState(float timeInState) {
        this.timeInState = timeInState;
    }
}
