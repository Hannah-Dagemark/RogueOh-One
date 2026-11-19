package com.roo.rogueyard.shared.game.player;
import com.roo.rogueyard.shared.engine.ecs.Entity;

/**
 * Core Player variant of the Entity class. This class holds the functionality unique to player characters, currently being controlled by users.
 * It is a subclass of the Entity class, making it able to interface with the ECS system. This class should however only hold non-ECS functionality,
 * as ECS functions should be left to systems and components, which are attached in runtime using a factory / wrapper.
 */
public class PlayerEntity extends Entity {

    public PlayerEntity(int id) {
        super(id);
    }
}
