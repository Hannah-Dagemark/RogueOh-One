package com.roo.rogueyard.shared.game.npc.ecs;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.HealthComponent;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.physics.VelocityComponent;

public class HuntBehaviourSystem extends ECSSystemSingle<HuntBehaviourComponent> {
    public HuntBehaviourSystem() {
        super(HuntBehaviourComponent.class);
    }

    @Override
    protected void updateSingle(Entity entity, HuntBehaviourComponent component) {
        Vector2 targetPosition = component.huntTarget.getComponent(TransformComponent.class).position;
        Vector2 velocityToTarget = entity.getComponent(TransformComponent.class).position.sub(targetPosition).limit(component.huntSpeed);
        entity.getComponent(VelocityComponent.class).velocity.set(velocityToTarget);
    }
}
