package com.roo.rogueyard.shared.game;

import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;

/**
 * Applies the resistances in the damage component to the accumulated damage
 * in the health component.
 */
public class ResistanceSystem extends ECSSystemSingle<ResistanceComponent> {
    public ResistanceSystem() {
        super(ResistanceComponent.class);
    }

    @Override
    protected void updateSingle(Entity entity, ResistanceComponent component) {
        var health = entity.getComponent(HealthComponent.class);
        component.resistance.apply(health.accumulatedDamage);
    }
}
