package com.roo.rogueyard.shared.engine.ecs;

abstract non-sealed class SystemBase implements System {
    public SystemBase() {
        SystemManager.getInstance().registerSystem(this);
    }
}
