package com.roo.rogueyard.shared.game.statemachine;

@FunctionalInterface
public interface Condition<T extends StateMachineContext> {
    boolean test(T ctx);

    default Condition<T> and(Condition<T> o) {
        return c -> test(c) && o.test(c);
    }

    default Condition<T> or(Condition<T> o) {
        return c -> test(c) || o.test(c);
    }

    default Condition<T> negate() {
        return c -> !test(c);
    }
}
