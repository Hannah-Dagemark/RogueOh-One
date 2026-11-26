package com.roo.rogueyard.shared.game.statemachine;

public interface StateMachineContext {
    GraphBundleRef graphBundleRef();
    GraphRef graphRef();
    void setGraphRef(GraphRef graphRef);
    State state();
    void setState(State state);
    float timeInState();
    void setTimeInState(float timeInState);
}
