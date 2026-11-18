package com.roo.rogueyard.shared.engine.ecs.velocity;

import com.roo.rogueyard.shared.engine.ecs.ECSSystem;
import com.roo.rogueyard.shared.engine.ecs.entities.Entity;
import com.roo.rogueyard.shared.engine.ecs.EntityManager;
import com.roo.rogueyard.shared.engine.positionable.Positionable;

public class VelocitySystem extends ECSSystem<VelocityComponent> {
    @Override
    protected void updateSingle(Integer id, VelocityComponent data) {
        Entity updateEntity = EntityManager.getInstance().getEntity(id);
        if (updateEntity instanceof Positionable posEntity) {
            posEntity.updatePosition(data.velocity);
        }
    }
}
