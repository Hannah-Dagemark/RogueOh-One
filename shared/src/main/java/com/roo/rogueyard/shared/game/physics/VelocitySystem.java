package com.roo.rogueyard.shared.game.physics;

import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;

/**
 * System which applies the velocity of each entity with velocity to that entities position, given that they have a Transform Component :3
 */
public class VelocitySystem extends ECSSystemSingle<VelocityComponent> {
        public VelocitySystem() {
            super(VelocityComponent.class);
        }
    /* TODO: At some point we will need to add some form of 'friction', or other way for entities with velocity
    *  to be slowed down specifically when their position is updated based on some slowdown value.
    *  For now that element has been omitted, as I think it's best to leave for when it becomes relevant.
    */
    @Override
    protected void updateSingle(Entity entity, VelocityComponent component) {
        entity.getComponent(TransformComponent.class).position.add(component.velocity);
    }
}
