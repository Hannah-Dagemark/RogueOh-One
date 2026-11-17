package com.roo.rogueyard.shared.engine.ecs;

import java.util.HashMap;
import java.util.Map;

public final class ComponentManager {
    private static final ComponentManager instance = new ComponentManager();

    private final Map<Class<? extends Component>, Map<Integer, Component>> componentMaps = new HashMap<>();

    private ComponentManager() {
    }

    public static ComponentManager getInstance() {
        return instance;
    }

    /**
     * Registers a component to be accessible through this manager. If it is
     * not registered then systems have no access to it.
     */
    public <T extends Component> void registerComponent(Class<T> componentType) {
        componentMaps.putIfAbsent(componentType, new HashMap<>());
    }

    /**
     * Returns the map from entity to component for the given component type.
     */
    @SuppressWarnings("unchecked")
    public <T extends Component> Map<Integer, T> getComponentsMap(Class<T> componentType) {
        var components = componentMaps.get(componentType);
        if (components == null) {
            throw new RuntimeException("componentType is not registered as a component.");
        }
        return (Map<Integer, T>) components;
    }

    /**
     * Returns the component of the specified entity and type.
     */
    public <T extends Component> T getComponent(Entity entity, Class<T> componentType) {
        return getComponentsMap(componentType).get(entity.id());
    }

    /**
     * Adds the specified component to the specified entity.
     */
    @SuppressWarnings("unchecked")
    public <T extends Component> void addComponent(Entity entity, T component) {
        ((Map<Integer, T>) getComponentsMap(component.getClass())).put(entity.id(), component);
    }

    /**
     * Removes the specified component from the specified entity.
     */
    public <T extends Component> void removeComponent(Entity entity, Class<T> type) {
        getComponentsMap(type).remove(entity.id());
    }

    /**
     * Removes all components from the specified entity.
     */
    public void removeAllComponents(Entity entity) {
        for (var components : componentMaps.values()) {
            components.remove(entity.id());
        }
    }
}
