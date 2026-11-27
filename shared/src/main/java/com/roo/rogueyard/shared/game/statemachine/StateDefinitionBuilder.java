package com.roo.rogueyard.shared.game.statemachine;

import java.util.ArrayList;
import java.util.List;

/**
 * Used to define states in a Graph.
 */
public final class StateDefinitionBuilder<T extends StateMachineContext<T>> {
    private final List<Action<T>> onEnters = new ArrayList<>();
    private final List<Action<T>> onExits = new ArrayList<>();
    private final List<Transition<T>> transitions = new ArrayList<>();

    StateDefinitionBuilder() {
    }

    /** Adds all given actions that should be executed when entering this state. */
    @SafeVarargs
    public final StateDefinitionBuilder<T> onEnter(Action<T>... a) {
        onEnters.addAll(List.of(a));
        return this;
    }

    /** Adds all given actions that should executed when exiting this state. */
    @SafeVarargs
    public final StateDefinitionBuilder<T> onExit(Action<T>... a) {
        onExits.addAll(List.of(a));
        return this;
    }

    /**
     * Define a new transition. When condition is true changes the state to
     * target and executes onTrigger.
     */
    public final StateDefinitionBuilder<T> transition(
            Condition<T> condition,
            State target,
            Action<T> onTrigger) {
        transitions.add(
                new Transition<>(condition, new Target(target), onTrigger));
        return this;
    }

    /**
     * Define a new transition. When condition is true changes the graph to
     * target and executes onTrigger.
     */
    public final StateDefinitionBuilder<T> transition(
            Condition<T> condition,
            GraphRef target,
            Action<T> onTrigger) {
        transitions.add(
                new Transition<>(condition, new Target(target), onTrigger));
        return this;
    }

    /**
     * Define a new transition. When condition is true changes the state to
     * target.
     */
    public final StateDefinitionBuilder<T> transition(
            Condition<T> condition,
            State target) {
        transitions.add(new Transition<>(condition, new Target(target), null));
        return this;
    }

    /**
     * Define a new transition. When condition is true changes the graph to
     * target.
     */
    public final StateDefinitionBuilder<T> transition(
            Condition<T> condition,
            GraphRef target) {
        transitions.add(new Transition<>(condition, new Target(target), null));
        return this;
    }

    /** Builds the StateDefinition from this builder. */
    final StateDefinition<T> build() {
        return new StateDefinition<T>(onEnters, onExits, transitions);
    }
}
