package com.roo.rogueyard.shared.game.statemachine;

import com.roo.rogueyard.shared.engine.ecs.Component;

/**
 * Represents an entity that is applied to a state machine.
 */
public class StateMachineComponent<T extends StateMachineContext<T>> implements Component {
    /** The state machine context. */
    public T ctx;
}
