package com.roo.rogueyard.shared.game.world;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.EntityManager;
import com.roo.rogueyard.shared.game.VisualComponent;
import com.roo.rogueyard.shared.game.VisualType;
import com.roo.rogueyard.shared.game.physics.TransformComponent;

public final class DescendPointFactory {
    private DescendPointFactory() {
    }

    public static DescendPointEntity createDescendPoint(Vector2 position, LayeredMap map) {
        DescendPointEntity descendPoint = new DescendPointEntity(EntityManager.getInstance().claimEntityId());
        descendPoint.addComponent(new TransformComponent(position));
        descendPoint.addComponent(new VisualComponent(VisualType.DESCENDPOINT));
        descendPoint.getComponent(VisualComponent.class).visible = false;
        descendPoint.map = map;
        return descendPoint;
    }
}
