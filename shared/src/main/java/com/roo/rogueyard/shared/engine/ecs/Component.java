package com.roo.rogueyard.shared.engine.ecs;

import java.util.HashMap;

abstract class Component<T> {
    public Component() {
        EntityManager.getInstance().registerComponent(this);
    }

    public HashMap<Integer, T> entities;
}
