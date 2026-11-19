package com.roo.rogueyard.shared.game.npc.ecs;

import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.HealthComponent;

public class BehaviourSystem extends ECSSystemSingle<BehaviourComponent> {
    public BehaviourSystem() {
        super(BehaviourComponent.class);
    }

    @Override
    protected void updateSingle(Entity entity, BehaviourComponent component) {

    }
}
