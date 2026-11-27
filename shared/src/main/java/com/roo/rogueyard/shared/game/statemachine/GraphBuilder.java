package com.roo.rogueyard.shared.game.statemachine;

import java.util.ArrayList;
import java.util.List;

import com.roo.rogueyard.shared.game.statemachine.Target.Type;

/**
 * Used to define a graph.
 */
public final class GraphBuilder<T extends StateMachineContext<T>> {
    private final List<StateDefinitionBuilder<T>> stateDefinitions = new ArrayList<>();
    private final List<Transition<T>> fromAnyTransitions = new ArrayList<>();
    private final GraphRef graphRef;

    private State initialState;

    /**
     * Create a new graph builder.
     *
     * @param graphRef The graph this builder defines.
     */
    GraphBuilder(GraphRef graphRef) {
        this.graphRef = graphRef;
    }

    /** The graph this builder is defining. */
    GraphRef graphRef() {
        return graphRef;
    }

    /** Creates a new state and returns a builder used to define it. */
    public StateDefinitionBuilder<T> newState() {
        State state = new State(stateDefinitions.size());
        var builder = new StateDefinitionBuilder<T>(state);
        stateDefinitions.add(builder);
        return builder;
    }

    /**
     * Adds a transition that can trigger from any state in this graph.
     *
     * @param condition The condition the transition will trigger upon.
     * @param target    The state the transition is going to.
     * @param onTrigger The action exectued when the transition is triggered.
     */
    public GraphBuilder<T> transitionFromAny(
            Condition<T> condition,
            StateDefinitionBuilder<T> target,
            Action<T> onTrigger) {
        fromAnyTransitions.add(
                new Transition<>(condition, new Target(target.state()), onTrigger));
        return this;
    }

    /**
     * Adds a transition that can trigger from any state in this graph.
     *
     * @param condition The condition the transition will trigger upon.
     * @param target    The state the transition is going to.
     */
    public GraphBuilder<T> transitionFromAny(
            Condition<T> condition,
            StateDefinitionBuilder<T> target) {
        fromAnyTransitions.add(new Transition<>(
                condition,
                new Target(target.state()),
                null));
        return this;
    }

    /**
     * Adds a transition that can trigger from any state in this graph.
     *
     * @param condition The condition the transition will trigger upon.
     * @param target    The graph the transition is going to.
     * @param onTrigger The action exectued when the transition is triggered.
     */
    public GraphBuilder<T> transitionFromAny(
            Condition<T> condition,
            GraphBuilder<T> target,
            Action<T> onTrigger) {
        fromAnyTransitions.add(new Transition<>(
                condition,
                new Target(target.graphRef()),
                onTrigger));
        return this;
    }

    /**
     * Adds a transition that can trigger from any state in this graph.
     *
     * @param condition The condition the transition will trigger upon.
     * @param target    The graph the transition is going to.
     */
    public GraphBuilder<T> transitionFromAny(
            Condition<T> condition,
            GraphBuilder<T> target) {
        fromAnyTransitions.add(new Transition<>(
                condition,
                new Target(target.graphRef()),
                null));
        return this;
    }

    /** Sets the initial state for the graph. */
    public GraphBuilder<T> initial(StateDefinitionBuilder<T> state) {
        if (initialState != null) {
            throw new IllegalStateException("Cannot set initial state twice.");
        }
        initialState = state.state();
        return this;
    }

    /** Creates and returns a new graph built from this builder. */
    Graph<T> build() {
        if (initialState == null) {
            throw new IllegalStateException("Initial state not set.");
        }

        validateTransitions(fromAnyTransitions);

        List<StateDefinition<T>> builtStateDefinitions = new ArrayList<>(stateDefinitions.size());

        for (var stateDefinition : stateDefinitions) {
            StateDefinition<T> builtStateDefinition = stateDefinition.build();

            validateTransitions(builtStateDefinition.transitions());

            builtStateDefinitions.add(builtStateDefinition);
        }

        return new Graph<T>(builtStateDefinitions, fromAnyTransitions, initialState);
    }

    /** Validates all transitions with a state target. */
    private void validateTransitions(List<Transition<T>> transitions) {
        for (var transition : transitions) {
            if (transition.target().type() == Type.STATE &&
                    transition.target().id() >= stateDefinitions.size()) {
                throw new IllegalStateException(
                        "Invald target state id %d.".formatted(
                                transition.target().id()));
            }
        }
    }
}
