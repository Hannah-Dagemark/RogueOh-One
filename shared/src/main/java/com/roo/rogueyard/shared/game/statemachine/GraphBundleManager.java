package com.roo.rogueyard.shared.game.statemachine;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds all graph bundles.
 */
class GraphBundleManager {
    private static final GraphBundleManager instance = new GraphBundleManager();

    private final List<GraphBundle<?>> graphBundles = new ArrayList<>();

    private GraphBundleManager() {
    }

    public static GraphBundleManager getInstance() {
        return instance;
    }

    /**
     * Adds a new graph bundle to this manager and returns a reference to it.
     */
    <T extends StateMachineContext> GraphBundleRef register(GraphBundle<T> graphBundle, Class<T> ctxType) {
        var graphBundleRef = new GraphBundleRef(graphBundles.size(), ctxType);
        graphBundles.add(graphBundle);
        return graphBundleRef;
    }

    /** Returns the graph bundle corresponding to the given reference. */
    GraphBundle<?> get(GraphBundleRef graphBundleRef) {
        return graphBundles.get(graphBundleRef.id());
    }
}
