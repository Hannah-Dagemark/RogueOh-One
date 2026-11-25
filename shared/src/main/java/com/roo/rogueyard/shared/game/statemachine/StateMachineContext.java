package com.roo.rogueyard.shared.game.statemachine;

public interface StateMachineContext {
    State state();
    void setState(State state);
    GraphId graphId();
    void setGraphId(GraphId graphId);
    float timeInState();
    void setTimeInState(float timeInState);
}
