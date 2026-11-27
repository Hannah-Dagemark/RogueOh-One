package com.roo.rogueyard.shared.game.world.entities;

import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.world.LayeredMap;

/**
 * Entity representing a DescendPoint in a map, which can be interacted with to move down vertically in the map.
 */
public class DescendPointEntity extends Entity {
    public LayeredMap map;

    public DescendPointEntity(int id) {
        super(id);
    }
}
