package com.roo.rogueyard.shared.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.Component;

/**
 * Basic component which holds velocity for an entity as a gdx.math Vector2 :D
 */
public class VelocityComponent implements Component {
    public Vector2 velocity;
    public VelocityComponent(Vector2 velocity) {
        this.velocity = velocity;
    }
}
