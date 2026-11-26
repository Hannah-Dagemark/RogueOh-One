package com.roo.rogueyard.shared.game.statemachine;

import java.util.List;

/**
 * Represents a bundle of graphs that can have transitions between them.
 */
class GraphBundle<T extends StateMachineContext> {
    private final List<Graph<T>> graphs;
    private final List<Transition<T>> fromAnyTransitions;
    private final GraphRef initialGraph;

    GraphBundle(
            List<Graph<T>> graphs,
            List<Transition<T>> fromAnyTransitions,
            GraphRef initialGraph) {
        this.graphs = graphs;
        this.fromAnyTransitions = fromAnyTransitions;
        this.initialGraph = initialGraph;
    }

    /** Returns the graph corresponding to the given graph reference. */
    Graph<T> get(GraphRef graphRef) {
        return graphs.get(graphRef.id());
    }

    /** Transitions that can be triggered when in any graph. */
    List<Transition<T>> fromAnyTransitions() {
        return fromAnyTransitions;
    }

    /** The initial graph a you start as when entering this graph. */
    GraphRef initialGraph() {
        return initialGraph;
    }

    /**
     * Update the given context according to this graph bundles transitions.
     * The value dt is the delta time in seconds.
     */
    void update(T ctx, float dt) {
        ctx.setTimeInState(ctx.timeInState() + dt);

        if (ctx.graphRef() == null) {
            ctx.setGraphRef(initialGraph());
        }

        Graph<T> graph = get(ctx.graphRef());

        var transition = nextTransition(ctx);
        if (transition == null) {
            graph.update(ctx);
            return;
        }

        var from = graph.get(ctx.state());
        Action.executeAll(from.onExit(), ctx);

        if (transition.action() != null) {
            transition.action().execute(ctx);
        }

        ctx.setTimeInState(0);

        ctx.setGraphRef(transition.target().asGraphRef());
    }

    /**
     * The next transition valid for the given context. Only includes from any
     * transitions.
     */
    private Transition<T> nextTransition(T ctx) {
        for (Transition<T> transition : fromAnyTransitions()) {
            if (transition.matches(ctx)) {
                return transition;
            }
        }

        return null;
    }
}
