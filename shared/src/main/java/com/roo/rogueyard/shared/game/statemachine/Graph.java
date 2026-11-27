package com.roo.rogueyard.shared.game.statemachine;

import java.util.List;

import com.roo.rogueyard.shared.game.statemachine.Target.Type;

/**
 * Represents a set of states and transitions between them.
 */
class Graph<T extends StateMachineContext<T>> {
    private final List<StateDefinition<T>> stateDefinitions;
    private final List<Transition<T>> fromAnyTransitions;
    private final State initialState;

    /**
     * Creates a graph.
     *
     * @param stateDefinitions   Definitions of all states in the graph.
     * @param fromAnyTransitions The transitions that can trigger from any
     *                           state in the graph.
     * @param initialState       The initial state a you start as when
     *                           entering this graph.
     */
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

    /** Transitions that can trigger from any state in this graph. */
    List<Transition<T>> fromAnyTransitions() {
        return fromAnyTransitions;
    }

    /** The initial state a you start as when entering this graph. */
    State initialState() {
        return initialState;
    }

    /** Update the given context according to this graphs transitions. */
    void update(T ctx) {
        var transition = nextTransition(ctx);
        if (transition == null) {
            return;
        }

        var from = get(ctx.state());
        Action.executeAll(from.onExit(), ctx);

        if (transition.action() != null) {
            transition.action().execute(ctx);
        }

        ctx.setTimeInState(0);

        if (transition.target().type() == Type.STATE) {
            var targetState = transition.target().asState();

            ctx.setState(targetState);

            Action.executeAll(get(targetState).onEnter(), ctx);
        } else {
            ctx.setGraphRef(transition.target().asGraphRef());

            var graphBundle = GraphBundleManager.getInstance()
                    .get(ctx.graphBundleRef());
            var targetGraph = graphBundle.get(ctx.graphRef());
            ctx.setState(targetGraph.initialState());
        }
    }

    /** The next transition valid for the given context. */
    private Transition<T> nextTransition(T ctx) {
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
