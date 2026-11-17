package com.roo.rogueyard.shared.game;

import com.roo.rogueyard.shared.engine.ecs.ECSSystem;

public class HealthSystem extends ECSSystem<HealthComponent> {
    public HealthSystem() {
        super(HealthComponent.class);
    }

    @Override
    protected void updateSingle(Integer entityId, HealthComponent component) {
        component.health -= 1;

        if (component.health <= 0) {
            System.out.println("Hi!");
        }
    }
}
