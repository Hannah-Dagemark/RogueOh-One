package com.roo.rogueyard.shared.game.statemachine;

import java.util.List;

/**
 * Represents an action that can be executed given a StateMachineContext.
 */
@FunctionalInterface
public interface Action<T extends StateMachineContext> {
    void execute(T ctx);

    /** Execute all given actions. */
    static <T extends StateMachineContext> void executeAll(List<Action<T>> actions, T ctx) {
        for (var action : actions) {
            action.execute(ctx);
        }
    }
}
