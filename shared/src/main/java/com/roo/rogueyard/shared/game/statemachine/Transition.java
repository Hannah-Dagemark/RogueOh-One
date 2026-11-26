package com.roo.rogueyard.shared.game.statemachine;

/**
 * Represents a transition to a target state within a graph or to a target
 * graph within a graph bundle.
 */
public class Transition<T extends StateMachineContext> {
    private final Condition<T> condition;
    private final Target target;
    private final Action<T> action;

    Transition(Condition<T> condition, Target target, Action<T> action) {
        this.condition = condition;
        this.target = target;
        this.action = action;
    }

    /** If the condition for the transition is true. */
    boolean matches(T ctx) {
        return condition.test(ctx);
    }

    /** The target state or graph. */
    Target target() {
        return target;
    }

    /** The action that should be applied when this transition is triggered. */
    Action<T> action() {
        return action;
    }
}
