package com.roo.rogueyard.shared.engine.ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Handles actions upon components.
 */
public final class ComponentManager {
    private static final ComponentManager instance = new ComponentManager();

    private final Map<Class<? extends Component>, Map<Entity, Component>> componentMaps = new HashMap<>();
    private final List<ComponentAddedListener> componentAddedListeners = new ArrayList<>();
    private final List<ComponentRemovedListener> componentRemovedListeners = new ArrayList<>();

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
     * Adds the given listener to the ones that are notified whenever a
     * component has been added to an entity.
     */
    public void addComponentAddedListener(ComponentAddedListener componentListener) {
        componentAddedListeners.add(componentListener);
    }

    /**
     * Adds the given listener to the ones that are notified whenever a
     * component has been removed from an entity.
     */
    public void addComponentRemovedListener(ComponentRemovedListener componentListener) {
        componentRemovedListeners.add(componentListener);
    }

    /**
     * Returns the map from entity to component for the given component type.
     */
    @SuppressWarnings("unchecked")
    public <T extends Component> Map<Entity, T> getComponentsMap(Class<T> componentType) {
        var components = componentMaps.get(componentType);
        if (components == null) {
            throw new RuntimeException("componentType is not registered as a component.");
        }
        return (Map<Entity, T>) components;
    }

    /**
     * Returns the component of the given type if it exists on the given entity,
     * otherwise null.
     */
    public <T extends Component> T getComponent(Entity entity, Class<T> componentType) {
        return getComponentsMap(componentType).get(entity);
    }

    /**
     * Returns all components attached to the given entity.
     */
    public List<? extends Component> getAllComponents(Entity entity) {
        var result = new ArrayList<Component>();
        for (var components : componentMaps.values()) {
            result.add(components.get(entity));
        }
        return result;
    }

    /**
     * Adds the specified component to the specified entity.
     */
    @SuppressWarnings("unchecked")
    public <T extends Component> void addComponent(Entity entity, T component) {
        if (entity.getComponent(component.getClass()) != null) {
            throw new RuntimeException(
                    "Added component %s to entity but it already exists."
                            .formatted(component.getClass().getName()));
        }

        if (component instanceof ComponentWithRequirements componentWithRequirements) {
            var checkResult = hasRequiredComponents(entity, componentWithRequirements);
            if (!checkResult.hasAllRequired()) {
                throw new RuntimeException(
                        "Added component %s missing required component %s.".formatted(
                                component.getClass().getName(),
                                checkResult.conflictingComponent().getName()));
            }
        }

        ((Map<Entity, T>) getComponentsMap(component.getClass())).put(entity, component);

        for (var componentListener : componentAddedListeners) {
            componentListener.onComponentAdded(entity, component.getClass(), component);
        }
    }

    /**
     * Removes the specified component from the specified entity.
     */
    public <T extends Component> void removeComponent(Entity entity, Class<T> componentType) {
        var checkResult = isComponentRequired(entity, componentType);
        if (checkResult.isRequired()) {
            throw new RuntimeException(
                    "Removed component %s is required by component %s".formatted(
                            componentType.getName(),
                            checkResult.requiredComponent().getClass().getName()));
        }

        T component = getComponent(entity, componentType);

        getComponentsMap(componentType).remove(entity);

        for (var componentListener : componentRemovedListeners) {
            componentListener.onComponentRemoved(entity, componentType, component);
        }
    }

    /**
     * Removes all components from the specified entity.
     */
    public void removeAllComponents(Entity entity) {
        for (var components : componentMaps.values()) {
            components.remove(entity);
        }
    }

    /**
     * Returns if the given entity has all components that the given
     * component requires.
     */
    private static HasRequiredComponentsResult hasRequiredComponents(Entity entity,
            ComponentWithRequirements component) {
        for (var requiredComponent : component.requiredComponents()) {
            if (entity.getComponent(requiredComponent) == null) {
                return new HasRequiredComponentsResult(false, requiredComponent);
            }
        }
        return new HasRequiredComponentsResult(true, null);
    }

    /**
     * Returns if the given component is required by any of the other
     * components on the given entity.
     */
    private static <T extends Component> IsComponentRequiredResult isComponentRequired(Entity entity,
            Class<T> componentType) {
        for (var otherComponent : entity.getAllComponents()) {
            if (otherComponent instanceof ComponentWithRequirements componentWithRequirements &&
                    componentWithRequirements.requiredComponents().contains(componentType)) {
                return new IsComponentRequiredResult(true, componentWithRequirements);
            }
        }
        return new IsComponentRequiredResult(false, null);
    }

    /**
     * Holds the result of hasRequiredComponents, conflictingComponent is null
     * if hasAllRequired is true.
     */
    private record HasRequiredComponentsResult(boolean hasAllRequired,
            Class<? extends Component> conflictingComponent) {
    };

    /**
     * Holds the result of isComponentRequired, requiredComponent is null if
     * ifRequired is false.
     */
    private record IsComponentRequiredResult(boolean isRequired, ComponentWithRequirements requiredComponent) {
    };
}
