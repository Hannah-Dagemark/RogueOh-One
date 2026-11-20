package com.roo.rogueyard.shared.game.npc.ecs;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.physics.VelocityComponent;

/**
 * System which sets target entity velocity to be a vector of size ( magnitude ) equal to the hunt speed in
 * a direction from the entity to its target
 */
public class HuntBehaviourSystem extends ECSSystemSingle<HuntBehaviourComponent> {
    public HuntBehaviourSystem() {
        super(HuntBehaviourComponent.class);
    }

    @Override
    protected void updateSingle(Entity entity, HuntBehaviourComponent huntBehaviour) {
        var transform = entity.getComponent(TransformComponent.class);
        var velocity = entity.getComponent(VelocityComponent.class);
        var targetTransform = huntBehaviour.huntTarget.getComponent(TransformComponent.class);

        Vector2 velocityToTarget = new Vector2(targetTransform.position).sub(transform.position).limit(huntBehaviour.huntSpeed);
        /*TODO: When friction is added, make this an 'add' velocity or something of the style*/
        velocity.velocity.set(velocityToTarget);
    }
}
