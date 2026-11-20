package com.roo.rogueyard.shared.game.npc.ecs;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.ComponentManager;
import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.engine.ecs.SystemManager;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.physics.VelocityComponent;

import java.util.Map;

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

                System.out.println("Ending hunt");
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
                    }

                    System.out.println("Adding hunt component");
                    entity.addComponent(new HuntBehaviourComponent(targetEntity, 0.1f));
                }
            }
        }
    }
}
