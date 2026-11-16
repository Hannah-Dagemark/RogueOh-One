package com.roo.rogueyard.shared.engine.ecs;

import java.util.HashMap;

abstract class Component<T> {
    public Component() {
        EntityManager.getInstance().RegisterComponent(this);
    }

    public HashMap<Integer, T> entities;
}
