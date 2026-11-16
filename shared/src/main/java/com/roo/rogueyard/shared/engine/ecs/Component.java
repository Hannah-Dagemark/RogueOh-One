package com.roo.rogueyard.shared.engine.ecs;

import java.util.HashMap;

/**
 * Represents data that an entity can be assigned to.
 */
public abstract class Component<T> {
    public Component() {
        ComponentManager.getInstance().register(this);
    }

    public HashMap<Integer, T> entities;
}
