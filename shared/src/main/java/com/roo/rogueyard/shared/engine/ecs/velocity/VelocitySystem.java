package com.roo.rogueyard.shared.engine.ecs.velocity;

import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.TransformComponent;

public class VelocitySystem extends ECSSystemSingle<VelocityComponent> {
        public VelocitySystem() {
            super(VelocityComponent.class);
        }

    @Override
    protected void updateSingle(Entity entity, VelocityComponent component) {
        entity.getComponent(TransformComponent.class).position.add(component.velocity);
    }
}
