package com.roo.rogueyard.shared.engine.ecs;

/**
 * Represents a listener for recieving when a component has been removed from
 * an entity.
 */
public interface ComponentRemovedListener {
    void onComponentRemoved(Entity entity, Class<?> componentType, Object component);
}
