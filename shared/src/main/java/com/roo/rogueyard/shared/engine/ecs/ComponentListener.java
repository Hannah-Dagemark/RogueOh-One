package com.roo.rogueyard.shared.engine.ecs;

public interface ComponentListener {
    void onComponentAdded(Entity entity, Class<?> componentType, Object component);
}
