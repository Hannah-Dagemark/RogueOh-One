package com.roo.rogueyard.shared.engine.ecs;

public sealed interface System permits SystemBase {
    void update();

    void registerToComponent();

    void unregisterFromComponent();
}
