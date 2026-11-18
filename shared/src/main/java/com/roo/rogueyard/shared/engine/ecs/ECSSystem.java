package com.roo.rogueyard.shared.engine.ecs;

import com.roo.rogueyard.shared.engine.ecs.entities.Entity;

import java.util.HashMap;

/**
 * Handles behaviour for one type of component. T needs to be the same as T
 * in the systems respective component.
 */
public abstract class ECSSystem<T> {
    public ECSSystem() {
        SystemManager.getInstance().register(this);
    }

    public HashMap<Integer, T> components;

    /**
     * The action applied to every component.
     */
    protected abstract void updateSingle(Integer id, T data);

    /**
     * Apply behaviour for all entities registered to the component this
     * system handles.
     */
    public void update() {
        for (var entry : components.entrySet()) {
            updateSingle(entry.getKey(), entry.getValue());
        }
    }

    /**
     * Assign the given entity to the component this system handles.
     */
    public void assignToComponent(Entity entity, T data) {
        components.put(entity.id(), data);
    }

    /**
     * Unassign the given entity from the component this system handles.
     */
    public void unassignFromComponent(Entity entity) {
        components.remove(entity.id());
    }
}
