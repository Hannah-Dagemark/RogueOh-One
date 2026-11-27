package com.roo.rogueyard.shared.game.statemachine;

import java.util.List;

/**
 * Represents an action that can be executed given a StateMachineContext.
 */
@FunctionalInterface
public interface Action<T extends StateMachineContext<T>> {
    /** Run this action. */
    void execute(T ctx);

    /** Executes all the given actions. */
    static <T extends StateMachineContext<T>> void executeAll(List<Action<T>> actions, T ctx) {
        for (var action : actions) {
            action.execute(ctx);
        }
    }
}
