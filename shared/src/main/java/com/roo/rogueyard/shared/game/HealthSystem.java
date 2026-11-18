package com.roo.rogueyard.shared.game;

import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;

public class HealthSystem extends ECSSystemSingle<HealthComponent> {
    public HealthSystem() {
        super(HealthComponent.class);
    }

    @Override
    protected void updateSingle(Entity entity, HealthComponent component) {
        component.health = Math.max(0, component.health - 1);
    }
}
