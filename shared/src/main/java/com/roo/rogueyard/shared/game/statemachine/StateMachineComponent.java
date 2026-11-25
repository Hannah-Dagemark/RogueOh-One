package com.roo.rogueyard.shared.game.statemachine;

import com.roo.rogueyard.shared.engine.ecs.Component;

public abstract class StateMachineComponent<T extends StateMachineContext> implements Component {
    /** The state machine context. */
    public T ctx;
}
