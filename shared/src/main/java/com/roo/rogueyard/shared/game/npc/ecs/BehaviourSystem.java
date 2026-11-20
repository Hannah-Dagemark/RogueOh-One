package com.roo.rogueyard.shared.game.npc.ecs;

import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.HealthComponent;

/**
 * Abstract representation of a behaviour system. For now, this is mainly a template for creating new behaviour systems.
 * In the future it might be used for things like inheritance which could allow for behaviour centralization and system overlap.
 */
public class BehaviourSystem extends ECSSystemSingle<BehaviourComponent> {
    public BehaviourSystem() {
        super(BehaviourComponent.class);
    }

    @Override
    protected void updateSingle(Entity entity, BehaviourComponent component) {

    }
}
