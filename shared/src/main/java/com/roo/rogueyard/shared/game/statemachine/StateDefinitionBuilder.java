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
    private final State state;

    /**
     * Create a new state definition builder.
     *
     * @param state The state this builder defines.
     */
    StateDefinitionBuilder(State state) {
        this.state = state;
    }

    /** The state this builder is defining. */
    State state() {
        return state;
    }

    /** Makes all given actions executed when entering this state. */
    @SafeVarargs
    public final StateDefinitionBuilder<T> onEnter(Action<T>... a) {
        onEnters.addAll(List.of(a));
        return this;
    }

    /** Makes all given actions executed when exiting this state. */
    @SafeVarargs
    public final StateDefinitionBuilder<T> onExit(Action<T>... a) {
        onExits.addAll(List.of(a));
        return this;
    }

    /**
     * Adds a transition from this state to any other in the same graph.
     *
     * @param condition The condition the transition will trigger upon.
     * @param target    The state the transition is going to.
     * @param onTrigger The action exectued when the transition is triggered.
     */
    public StateDefinitionBuilder<T> transition(
            Condition<T> condition,
            StateDefinitionBuilder<T> target,
            Action<T> onTrigger) {
        transitions.add(new Transition<>(
                condition,
                new Target(target.state()),
                onTrigger));
        return this;
    }

    /**
     * Adds a transition from this state to any other in the same graph.
     *
     * @param condition The condition the transition will trigger upon.
     * @param target    The state the transition is going to.
     */
    public StateDefinitionBuilder<T> transition(
            Condition<T> condition,
            GraphBuilder<T> target,
            Action<T> onTrigger) {
        transitions.add(new Transition<>(
                condition,
                new Target(target.graphRef()),
                onTrigger));
        return this;
    }

    /**
     * Define a new transition. When condition is true changes the state to
     * target.
     */
    public StateDefinitionBuilder<T> transition(
            Condition<T> condition,
            StateDefinitionBuilder<T> target) {
        transitions.add(new Transition<>(
                condition,
                new Target(target.state()),
                null));
        return this;
    }

    /**
     * Define a new transition. When condition is true changes the graph to
     * target.
     */
    public StateDefinitionBuilder<T> transition(
            Condition<T> condition,
            GraphBuilder<T> target) {
        transitions.add(new Transition<>(
                condition,
                new Target(target.graphRef()),
                null));
        return this;
    }

    /** Builds the StateDefinition from this builder. */
    StateDefinition<T> build() {
        return new StateDefinition<T>(onEnters, onExits, transitions);
    }
}
