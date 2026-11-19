package com.roo.rogueyard.shared.engine.ecs;

/**
 * Represents a listener for recieving when a component has been added to an
 * entity.
 */
public interface ComponentListener {
    void onComponentAdded(Entity entity, Class<?> componentType, Object component);
}
