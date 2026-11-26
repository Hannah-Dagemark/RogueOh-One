package com.roo.rogueyard.shared.game.statemachine;

/**
 * Represents an action that can be executed given a StateMachineContext.
 */
@FunctionalInterface
public interface Action<T extends StateMachineContext> {
    void execute(T ctx);
}
