package com.roo.rogueyard.shared.game.statemachine;

@FunctionalInterface
public interface Action<T extends StateMachineContext> {
    void execute(T ctx);
}
