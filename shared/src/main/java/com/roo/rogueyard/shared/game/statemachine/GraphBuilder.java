package com.roo.rogueyard.shared.game.statemachine;

import java.util.ArrayList;
import java.util.List;

public final class GraphBuilder<T extends StateMachineContext> {
    private final List<StateDefinitionBuilder<T>> stateDefinitions = new ArrayList<>();
    private final List<Transition<T>> fromAnyTransitions = new ArrayList<>();
    private State initialState;

    public State newState() {
        State state = new State(stateDefinitions.size());
        stateDefinitions.add(new StateDefinitionBuilder<>());
        return state;
    }

    public StateDefinitionBuilder<T> define(State state) {
        return stateDefinitions.get(state.id());
    }

    public GraphBuilder<T> initial(State state) {
        if (initialState != null) {
            throw new RuntimeException("Cannot set initial state twice");
        }
        initialState = state;
        return this;
    }

    public GraphBuilder<T> transitionFromAny(
            Condition<T> condition,
            State target,
            Action<T> onTrigger) {
        fromAnyTransitions.add(new Transition<>(condition, target, onTrigger));
        return this;
    }

    public Graph<T> build() {
        if (initialState == null) {
            throw new IllegalStateException("No initial state");
        }

        List<StateDefinition<T>> builtStateDefinitions = new ArrayList<>(stateDefinitions.size());

        for (var stateDefinition : stateDefinitions) {
            StateDefinition<T> builtStateDefinition = stateDefinition.build(initialState);

            checkTransitions(builtStateDefinition.transitions());

            builtStateDefinitions.add(builtStateDefinition);
        }

        return new Graph<T>(builtStateDefinitions, fromAnyTransitions, initialState);
    }

    private void checkTransitions(List<Transition<T>> transitions) {
        for (var transition : transitions) {
            if (transition.target().id() >= stateDefinitions.size()) {
                throw new IllegalStateException(
                        "Invald target id %d.".formatted(
                                transition.target().id()));
            }
        }
    }
}
