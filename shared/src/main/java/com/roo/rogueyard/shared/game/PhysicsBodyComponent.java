package com.roo.rogueyard.shared.game;

import java.util.List;

import com.badlogic.gdx.physics.box2d.Body;
import com.roo.rogueyard.shared.engine.ecs.Component;
import com.roo.rogueyard.shared.engine.ecs.ComponentWithRequirements;
import com.roo.rogueyard.shared.engine.ecs.RequiredComponentsBuilder;

/**
 * Holds a Box2D body simulated by the PhysicsSystem every update.
 */
public class PhysicsBodyComponent implements ComponentWithRequirements {
    public Body body;

    public PhysicsBodyComponent(Body body) {
        this.body = body;
    }

    @Override
    public List<Class<? extends Component>> requiredComponents() {
        return new RequiredComponentsBuilder()
                .add(TransformComponent.class)
                .build();
    }
}
