package com.roo.rogueyard.shared.game.statemachine;

record Transition<T extends StateMachineContext>(
        Condition<T> condition,
        State target,
        Action<T> action) {
    boolean matches(T ctx) {
        return condition.test(ctx);
    }
}
