package com.roo.rogueyard.shared.game.combat;

import com.roo.rogueyard.shared.game.statemachine.GraphId;
import com.roo.rogueyard.shared.game.statemachine.State;
import com.roo.rogueyard.shared.game.statemachine.StateMachineContext;

public class WeaponContext implements StateMachineContext {
    private State state;
    private GraphId graphId;

    private float timeInState = 0;

    /** The cooldown for the weapon action in seconds. */
    public float cooldown;
    /**
     * If true the weapon continuously does its action while the input is held.
     */
    public boolean autoRepeat;

    public WeaponContext(State state, GraphId graphId, float cooldown, boolean autoRepeat) {
        this.state = state;
        this.graphId = graphId;
        this.cooldown = cooldown;
        this.autoRepeat = autoRepeat;
    }

    @Override
    public State state() {
        return state;
    }

    @Override
    public void setState(State state) {
        this.state = state;
    }

    @Override
    public GraphId graphId() {
        return graphId;
    }

    @Override
    public void setGraphId(GraphId graphId) {
        this.graphId = graphId;
    }

    @Override
    public float timeInState() {
        return timeInState;
    }

    @Override
    public void setTimeInState(float timeInState) {
        this.timeInState = timeInState;
    }
}
