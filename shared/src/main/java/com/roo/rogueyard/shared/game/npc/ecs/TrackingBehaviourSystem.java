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
    protected void updateSingle(Entity entity, TrackingBehaviourComponent component) {
        /*TODO: Change this from looking at all 'positionable' to all 'aligned for combat' / combat eligible. And stop the very expensive creations of eligible targets EVERY ITERATION >:C*/
        if (!(entity.getComponent(HuntBehaviourComponent.class) == null)){
            HuntBehaviourComponent huntComponent = entity.getComponent(HuntBehaviourComponent.class);
            if ((entity.getComponent(TransformComponent.class).position.sub(huntComponent.huntTarget.getComponent(TransformComponent.class).position)).len() > (component.trackingDistance + 100)) {
                entity.removeComponent(HuntBehaviourComponent.class);
                entity.getComponent(VelocityComponent.class).velocity.set(new Vector2(0,0));
                System.out.println("Ending hunt");
            }
        } else {
            Map<Entity, TransformComponent> eligibleTargets = ComponentManager.getInstance().getComponentsMap(TransformComponent.class);

            for (var target : eligibleTargets.entrySet()) {
                if ((entity.getComponent(TransformComponent.class).position.sub(target.getValue().position).len() <= component.trackingDistance)) {
                    /*First, check if the target has a TrackingBehaviourComponent. Then, if it does, check allegiance. TODO: refactor allegiance from hunting component to combat-ish component change here when that's done*/
                    if (!(target.getKey().getComponent(TrackingBehaviourComponent.class) == null)) {
                        if (target.getKey().getComponent(TrackingBehaviourComponent.class).allegiance == component.allegiance){
                            break;
                        }
                    }
                    System.out.println("Adding hunt component");
                    entity.addComponent(new HuntBehaviourComponent(target.getKey(), 10));
                }
            }
        }
     }
}
