package com.roo.rogueyard.shared.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;

/**
 * System for updating the position based on the VelocityFactor.
 * If entity velocityFactor product isn't 1, it updates the position by the velocity scaled by the velocityFactor product, minus the non-scaled velocity.
 * The last part is to compensate for the velocitySystem already updating the position by the base velocity. Since these are both done before render,
 * it is not noticeable in-game.
 */
/* TODO: May want to increase security around VelocityFactorSystem position updates, there's probably some edge case where position jitter causes errors with systems reading position */
public class VelocityFactorSystem extends ECSSystemSingle<VelocityFactorComponent> {
    public VelocityFactorSystem() {super(VelocityFactorComponent.class);}

    @Override
    protected void updateSingle(Entity entity, VelocityFactorComponent component) {
        if (!(component.additiveFactorSources.isEmpty() && component.multiplicativeFactorSources.isEmpty())) {
            float additiveFactor = 1;
            float multiplicativeFactor = 1;
            for (float factor : component.additiveFactorSources.values()) {
                additiveFactor += factor;
            }
            for (float factor : component.multiplicativeFactorSources.values()) {
                multiplicativeFactor *= factor;
            }
            Vector2 entityVelocity = entity.getComponent(VelocityComponent.class).velocity;
            Vector2 factoredVelocityChange = new Vector2(new Vector2(entityVelocity).scl(additiveFactor*multiplicativeFactor)).sub(entityVelocity);
            entity.getComponent(TransformComponent.class).position.add(factoredVelocityChange);
        }
    }
}
