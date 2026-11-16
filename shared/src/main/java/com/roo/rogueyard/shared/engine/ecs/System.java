package com.roo.rogueyard.shared.engine.ecs;

/**
 * Handles behaviour for one type of `Component`.
 */
public abstract class System {
    public System() {
        SystemManager.getInstance().register(this);
    }

    /**
     * Apply behaviour for all entities registered to the component this
     * system handles.
     */
    abstract void update();

    /**
     * Assign the given entity to the component this system handles.
     */
    abstract void assignToComponent(Entity entity);

    /**
     * Unassign the given entity from the component this system handles.
     */
    abstract void unassignFromComponent(Entity entity);
}
