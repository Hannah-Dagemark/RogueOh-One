package com.roo.rogueyard.shared.engine.ecs.velocity;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.Component;

public class VelocityComponent implements Component {
    public Vector2 velocity;
    VelocityComponent(Vector2 velocity) {
        this.velocity = velocity;
    }
}
