package com.roo.rogueyard.shared.game.statemachine;

/**
 * Represents a condition.
 */
@FunctionalInterface
public interface Condition<T extends StateMachineContext<T>> {
    /** If the condition is true. */
    boolean test(T ctx);

    /**
     * A condition that is true when this condition and o is true.
     *
     * @param o The condition to combine with.
     * @return The combined condition.
     */
    default Condition<T> and(Condition<T> o) {
        return c -> test(c) && o.test(c);
    }

    /**
     * A condition that is true when this condition or o is true.
     *
     * @param o The condition to combine with.
     * @return The combined condition.
     */
    default Condition<T> or(Condition<T> o) {
        return c -> test(c) || o.test(c);
    }

    /**
     * A condition that is true when this condition is false.
     *
     * @return The negated condition.
     */
    default Condition<T> negate() {
        return c -> !test(c);
    }
}
