package com.roo.rogueyard.shared.game;

import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.event.GameEventBus;

public class HealthSystem extends ECSSystemSingle<HealthComponent> {
    private GameEventBus eventBus;

    public HealthSystem(GameEventBus eventBus) {
        super(HealthComponent.class);

        this.eventBus = eventBus;
    }

    @Override
    protected void updateSingle(Entity entity, HealthComponent component) {
        System.out.println("Update happened");
        component.health -= 1;

        if (component.health <= 0) {
            System.out.println("Hi!");
        }
    }
}
