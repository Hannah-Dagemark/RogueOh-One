package com.roo.rogueyard.shared.engine.ecs;

import java.util.Map;

/**
 * Handles behaviour for one type of component. T needs to be the same as T
 * in the systems respective component.
 */
public abstract class ECSSystem<T extends Component> {
    private final Map<Integer, T> components;

    public ECSSystem(Class<T> componentType) {
        ComponentManager.getInstance().registerComponent(componentType);
        components = ComponentManager.getInstance().getComponentsMap(componentType);

        SystemManager.getInstance().register(this);
    }

    /**
     * The action applied to every component.
     */
    protected abstract void updateSingle(Integer entityId, T component);

    /**
     * Apply behaviour for all entities registered to the component this
     * system handles.
     */
    public void update() {
        for (var entry : components.entrySet()) {
            updateSingle(entry.getKey(), entry.getValue());
        }
    }
}
