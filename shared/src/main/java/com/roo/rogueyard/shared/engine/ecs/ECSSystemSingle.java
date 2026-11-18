package com.roo.rogueyard.shared.engine.ecs;

import java.util.Map;

/**
 * Handles behaviour for one type of component. T needs to be the same as T
 * in the systems respective component.
 */
public abstract class ECSSystemSingle<T extends Component> implements ECSSystem {
    private final Map<Entity, T> components;

    public ECSSystemSingle(Class<T> componentType) {
        ComponentManager.getInstance().registerComponent(componentType);
        components = ComponentManager.getInstance().getComponentsMap(componentType);

        SystemManager.getInstance().register(this);
    }

    /**
     * The action applied to every component.
     */
    protected abstract void updateSingle(Entity entity, T component);

    @Override
    public void update() {
        for (var entry : components.entrySet()) {
            updateSingle(entry.getKey(), entry.getValue());
        }
    }
}
