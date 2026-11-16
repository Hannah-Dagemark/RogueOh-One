package com.roo.rogueyard.shared.engine.ecs;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles actions that affect all components.
 */
public final class ComponentManager {
    private static ComponentManager instance = new ComponentManager();

    private final List<Component<?>> components = new ArrayList<>();

    private ComponentManager() {
    }

    public static ComponentManager getInstance() {
        return instance;
    }

    /**
     * Registers a component to this manager.
     */
    public void register(Component<?> component) {
        components.add(component);
    }

    /**
     * Unassigns an entity from all components.
     */
    public void unassignFromAll(Entity entity) {
        for (var component : components) {
            component.entities.remove(entity.id());
        }
    }
}
