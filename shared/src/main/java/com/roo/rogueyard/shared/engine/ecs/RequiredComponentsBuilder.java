package com.roo.rogueyard.shared.engine.ecs;

import java.util.ArrayList;
import java.util.List;

/**
 * Helper builder for creating a required components list.
 */
public final class RequiredComponentsBuilder {
    private final List<Class<? extends Component>> requiredComponents = new ArrayList<>();

    /**
     * Add a required component type.
     */
    public RequiredComponentsBuilder add(Class<? extends Component> requiredComponent) {
        this.requiredComponents.add(requiredComponent);
        return this;
    }

    /**
     * Returns the list of required components added so far.
     */
    public List<Class<? extends Component>> build() {
        return requiredComponents;
    }
}
