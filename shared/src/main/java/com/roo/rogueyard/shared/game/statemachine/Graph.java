package com.roo.rogueyard.shared.game.statemachine;

import java.util.List;

/**
 * Represents a set of states and transitions between them.
 */
class Graph<T extends StateMachineContext> {
    private final List<StateDefinition<T>> stateDefinitions;
    private final List<Transition<T>> fromAnyTransitions;
    private final State initialState;

    Graph(
            List<StateDefinition<T>> stateDefinitions,
            List<Transition<T>> fromAnyTransitions,
            State initialState) {
        this.stateDefinitions = stateDefinitions;
        this.fromAnyTransitions = fromAnyTransitions;
        this.initialState = initialState;
    }

    /** Returns the state definition corresponding to the given state. */
    StateDefinition<T> get(State state) {
        return stateDefinitions.get(state.id());
    }

    /** Returns all state definitions on this graph. */
    List<StateDefinition<T>> stateDefinitions() {
        return stateDefinitions;
    }

    /** Transitions that can be triggered when in any state. */
    List<Transition<T>> fromAnyTransitions() {
        return fromAnyTransitions;
    }

    /** The initial state a you start as when entering this graph. */
    State initialState() {
        return initialState;
    }

    /** The next transition valid for the given context. */
    Transition<T> nextTransition(T ctx) {
        for (Transition<T> transition : fromAnyTransitions()) {
            if (transition.matches(ctx)) {
                return transition;
            }
        }

        for (Transition<T> transition : get(ctx.state()).transitions()) {
            if (transition.matches(ctx)) {
                return transition;
            }
        }

        return null;
    }
}
