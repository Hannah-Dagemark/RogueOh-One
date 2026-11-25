package com.roo.rogueyard.shared.game.npc.ecs;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.ComponentManager;
import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.physics.VelocityComponent;
import com.roo.rogueyard.shared.game.player.PlayerEntity;

import java.util.Map;

/**
 * System which scans for nearby enemies to hunt, tracking them if they're within distance and keeping a check on if
 * they are within tracking distance.
 */
/*
 * TODO: Make the tracking behaviour system more dynamic, able to trigger different responses beyond a simple hunt if a targetable enttiy is located
 */
public class TrackingBehaviourSystem extends ECSSystemSingle<TrackingBehaviourComponent> {
    public TrackingBehaviourSystem() {
        super(TrackingBehaviourComponent.class);
    }

    @Override
    protected void updateSingle(Entity entity, TrackingBehaviourComponent trackingBehaviour) {
        // TODO: Change this from looking at all 'positionable' to all
        // 'aligned for combat' / combat eligible. And stop the very expensive
        // creations of eligible targets EVERY ITERATION >:C
        var transform = entity.getComponent(TransformComponent.class);
        var velocity = entity.getComponent(VelocityComponent.class);
        var huntBehaviour = entity.getComponent(HuntBehaviourComponent.class);

        if (huntBehaviour != null) {
            var huntedTransform = huntBehaviour.huntTarget.getComponent(TransformComponent.class);
            var delta = new Vector2(transform.position).sub(huntedTransform.position);

            if (delta.len() > trackingBehaviour.trackingDistance) {
                entity.removeComponent(HuntBehaviourComponent.class);
                velocity.velocity.set(new Vector2());
            }
        } else {
            Map<Entity, TransformComponent> eligibleTargets = ComponentManager.getInstance()
                    .getComponentsMap(TransformComponent.class);

            for (var target : eligibleTargets.entrySet()) {
                var targetEntity = target.getKey();
                var targetTransform = target.getValue();
                Vector2 delta = new Vector2(transform.position).sub(targetTransform.position);
                if (delta.len() <= trackingBehaviour.trackingDistance) {
                    /*
                     * First, check if the target has a TrackingBehaviourComponent. Then, if it
                     * does, check allegiance. TODO: refactor allegiance from hunting component to
                     * combat-ish component change here when that's done
                     */
                    if (targetEntity.getComponent(TrackingBehaviourComponent.class) != null) {
                        var targetTrackingBehaviour = targetEntity.getComponent(TrackingBehaviourComponent.class);
                        if (targetTrackingBehaviour.allegiance == trackingBehaviour.allegiance) {
                            continue;
                        }
                    } else if (targetEntity.getClass() != PlayerEntity.class) {
                        continue;
                    }
                    entity.addComponent(new HuntBehaviourComponent(targetEntity, 1f));
                    break;
                }
            }
        }
    }
}
