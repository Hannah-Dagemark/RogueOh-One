package com.roo.rogueyard.shared.engine.ecs;

/**
 * Handles behaviour for components.
 */
public interface ECSSystem {
    /**
     * Apply behaviour for all entities registered to the component this
     * system handles.
     */
    public void update();
}
