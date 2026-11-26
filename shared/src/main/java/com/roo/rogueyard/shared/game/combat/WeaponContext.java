package com.roo.rogueyard.shared.game.combat;

import com.roo.rogueyard.shared.game.statemachine.GraphBundleRef;
import com.roo.rogueyard.shared.game.statemachine.GraphRef;
import com.roo.rogueyard.shared.game.statemachine.State;
import com.roo.rogueyard.shared.game.statemachine.StateMachineContext;

public class WeaponContext implements StateMachineContext {
    private GraphBundleRef graphBundleRef;
    private GraphRef graphRef;
    private State state;

    private float timeInState = 0;

    /** The cooldown for the weapon action in seconds. */
    public float cooldown;
    /**
     * If true the weapon continuously does its action while the input is held.
     */
    public boolean autoRepeat;

    public WeaponContext(GraphBundleRef graphBundleRef, float cooldown, boolean autoRepeat) {
        this.graphBundleRef = graphBundleRef;
        this.graphRef = null;
        this.state = null;
        this.cooldown = cooldown;
        this.autoRepeat = autoRepeat;
    }

    @Override
    public GraphBundleRef graphBundleRef() {
        return graphBundleRef;
    }

    @Override
    public GraphRef graphRef() {
        return graphRef;
    }

    @Override
    public void setGraphRef(GraphRef graphRef) {
        this.graphRef = graphRef;
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
    public float timeInState() {
        return timeInState;
    }

    @Override
    public void setTimeInState(float timeInState) {
        this.timeInState = timeInState;
    }
}
