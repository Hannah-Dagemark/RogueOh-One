package com.roo.rogueyard.shared.game;

import com.badlogic.gdx.physics.box2d.Body;
import com.roo.rogueyard.shared.engine.ecs.Component;

public class PhysicsBodyComponent implements Component {
    public Body body;

    public PhysicsBodyComponent(Body body) {
        this.body = body;
    }
}
