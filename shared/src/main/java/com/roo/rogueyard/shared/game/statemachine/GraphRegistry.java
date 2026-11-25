package com.roo.rogueyard.shared.game.statemachine;

import java.util.ArrayList;
import java.util.List;

public class GraphRegistry<T extends StateMachineContext> {
    private final List<Graph<T>> graphs = new ArrayList<>();

    public GraphRegistry() {
    }

    public GraphId register(Graph<T> graph) {
        graphs.add(graph);
        return new GraphId(graphs.size() - 1);
    }

    public Graph<T> get(GraphId graphId) {
        return graphs.get(graphId.id());
    }
}
