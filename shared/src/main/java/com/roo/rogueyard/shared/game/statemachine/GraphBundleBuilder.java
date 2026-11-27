package com.roo.rogueyard.shared.game.statemachine;

import java.util.ArrayList;
import java.util.List;

import com.roo.rogueyard.shared.game.statemachine.Target.Type;

/**
 * Used to define a graph bundle.
 */
public final class GraphBundleBuilder<T extends StateMachineContext<T>> {
    private final List<GraphBuilder<T>> graphs = new ArrayList<>();
    private final List<Transition<T>> fromAnyTransitions = new ArrayList<>();

    private GraphRef initialGraph;

    /** Create a new graph bundle builder. */
    public GraphBundleBuilder() {
    }

    /** Creates a new graph and returns a builder used to define it. */
    public GraphBuilder<T> newGraph() {
        GraphRef graphRef = new GraphRef(graphs.size());
        var builder = new GraphBuilder<T>(graphRef);
        graphs.add(builder);
        return builder;
    }

    /**
     * Adds a transition that can trigger from any graph in this graph bundle.
     *
     * @param condition The condition the transition will trigger upon.
     * @param target    The graph the transition is going to.
     * @param onTrigger The action exectued when the transition is triggered.
     */
    public GraphBundleBuilder<T> transitionFromAny(
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
     * Adds a transition that can trigger from any graph in this graph bundle.
     *
     * @param condition The condition the transition will trigger upon.
     * @param target    The graph the transition is going to.
     */
    public GraphBundleBuilder<T> transitionFromAny(
            Condition<T> condition,
            GraphBuilder<T> target) {
        fromAnyTransitions.add(new Transition<>(
                condition,
                new Target(target.graphRef()),
                null));
        return this;
    }

    /** Sets the initial graph for the graph bundle. */
    public GraphBundleBuilder<T> initial(GraphBuilder<T> graph) {
        if (initialGraph != null) {
            throw new IllegalStateException("Cannot set initial graph twice.");
        }
        initialGraph = graph.graphRef();
        return this;
    }

    /**
     * Creates and registers a new graph bundle built from this builder and
     * returns a reference to it. The ctxType is the type of the context
     * this builder is parameterized as.
     */
    public GraphBundleRef<T> build() {
        if (initialGraph == null) {
            throw new IllegalStateException("Initial graph not set.");
        }

        validateFromAnyTransitions();

        List<Graph<T>> builtGraphs = new ArrayList<>(graphs.size());

        for (var graph : graphs) {
            Graph<T> builtGraph = graph.build();

            validateGraphTransitions(builtGraph);

            builtGraphs.add(builtGraph);
        }

        var graphBundle = new GraphBundle<>(builtGraphs, fromAnyTransitions, initialGraph);

        return GraphBundleManager.getInstance().register(graphBundle);
    }

    /** Validates all from any transitions. */
    private void validateFromAnyTransitions() {
        for (var transition : fromAnyTransitions) {
            if (transition.target().type() != Type.GRAPH) {
                throw new IllegalStateException(
                        "From any transitions in a graph bundle must target a graph."
                                .formatted(transition.target().id()));
            }
        }
    }

    /** Validates all transitions in the given graph with graph targets. */
    private void validateGraphTransitions(Graph<T> graph) {
        for (var stateDefinition : graph.stateDefinitions()) {
            validateTransitions(stateDefinition.transitions());
        }
    }

    /** Validates all given transitions with graph targets. */
    private void validateTransitions(List<Transition<T>> transitions) {
        for (var transition : transitions) {
            if (transition.target().type() == Type.GRAPH &&
                    transition.target().id() >= graphs.size()) {
                throw new IllegalStateException(
                        "Invald target graph id %d.".formatted(
                                transition.target().id()));
            }
        }
    }
}
