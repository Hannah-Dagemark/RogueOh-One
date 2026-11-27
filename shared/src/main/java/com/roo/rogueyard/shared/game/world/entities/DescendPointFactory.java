package com.roo.rogueyard.shared.game.world.entities;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.EntityManager;
import com.roo.rogueyard.shared.game.VisualComponent;
import com.roo.rogueyard.shared.game.VisualType;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.world.LayeredMap;

/**
 * Factory to create new descend point entities
 */
public final class DescendPointFactory {
    private DescendPointFactory() {
    }

    /**
     * Generator function for creating a new descend point
     * @param position position of the new descend point
     * @param map the map the descend point should belong to
     * @return the created descend point entity
     */
    public static DescendPointEntity createDescendPoint(Vector2 position, LayeredMap map) {
        DescendPointEntity descendPoint = new DescendPointEntity(EntityManager.getInstance().claimEntityId());
        descendPoint.addComponent(new TransformComponent(position));
        descendPoint.addComponent(new VisualComponent(VisualType.DESCENDPOINT));
        descendPoint.getComponent(VisualComponent.class).visible = false;
        descendPoint.map = map;
        return descendPoint;
    }
}
