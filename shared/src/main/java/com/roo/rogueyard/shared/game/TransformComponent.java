package com.roo.rogueyard.shared.game;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.Component;

public class TransformComponent implements Component {
    public Vector2 position;
    public Vector2 scale;
    public float rotation;

    public TransformComponent(Vector2 position, Vector2 scale, float rotation) {
        this.position = position;
        this.scale = scale;
        this.rotation = rotation;
    }
}
