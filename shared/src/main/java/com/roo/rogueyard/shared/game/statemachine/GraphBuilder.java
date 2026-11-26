package com.roo.rogueyard.shared.game.statemachine;

import java.util.ArrayList;
import java.util.List;

import com.roo.rogueyard.shared.game.statemachine.Target.Type;

/**
 * Used to define a graph.
 */
public final class GraphBuilder<T extends StateMachineContext> {
    private final List<StateDefinitionBuilder<T>> stateDefinitions = new ArrayList<>();
    private final List<Transition<T>> fromAnyTransitions = new ArrayList<>();
    private State initialState;

    GraphBuilder() {
    }

    /** Creates a new state definition and returns its corresponding state. */
    public State createState() {
        State state = new State(stateDefinitions.size());
        stateDefinitions.add(new StateDefinitionBuilder<>());
        return state;
    }

    /**
     * Returns a state definition builder that can be used to define the
     * given state.
     */
    public StateDefinitionBuilder<T> define(State state) {
        return stateDefinitions.get(state.id());
    }

    /**
     * Adds a transition that can trigger from any state. When the condition is
     * true the state changes to the target and the onTrigger action is executed.
     */
    public GraphBuilder<T> transitionFromAny(
            Condition<T> condition,
            Target target,
            Action<T> onTrigger) {
        fromAnyTransitions.add(new Transition<>(condition, target, onTrigger));
        return this;
    }

    /**
     * Adds a transition that can trigger from any state. When the condition is
     * true the state changes to the target.
     */
    public GraphBuilder<T> transitionFromAny(
            Condition<T> condition,
            Target target) {
        fromAnyTransitions.add(new Transition<>(condition, target, null));
        return this;
    }

    /** Sets the initial state for the graph. */
    public GraphBuilder<T> initial(State state) {
        if (initialState != null) {
            throw new RuntimeException("Cannot set initial state twice.");
        }
        initialState = state;
        return this;
    }

    /** Creates and returns a new graph built from this builder. */
    Graph<T> build() {
        if (initialState == null) {
            throw new RuntimeException("Initial state not set.");
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
