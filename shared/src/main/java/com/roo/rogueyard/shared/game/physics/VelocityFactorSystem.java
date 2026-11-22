package com.roo.rogueyard.shared.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;

public class VelocityFactorSystem extends ECSSystemSingle<VelocityFactorComponent> {
    public VelocityFactorSystem() {super(VelocityFactorComponent.class);}

    @Override
    protected void updateSingle(Entity entity, VelocityFactorComponent component) {
        if (component.additiveVelocityFactor*component.multiplicativeVelocityFactor != 1) {
            Vector2 entityVelocity = entity.getComponent(VelocityComponent.class).velocity;
            Vector2 factoredVelocityChange = new Vector2(new Vector2(entityVelocity).scl(component.additiveVelocityFactor * component.multiplicativeVelocityFactor)).sub(entityVelocity);
            entity.getComponent(TransformComponent.class).position.add(factoredVelocityChange);
        }
    }
}
