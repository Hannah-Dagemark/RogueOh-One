package com.roo.rogueyard.shared.game.physics;

import java.util.List;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.Component;
import com.roo.rogueyard.shared.engine.ecs.ComponentWithRequirements;
import com.roo.rogueyard.shared.engine.ecs.RequiredComponentsBuilder;

/**
 * Basic component which holds velocity for an entity as a gdx.math Vector2 :D
 */
public class VelocityComponent implements ComponentWithRequirements {
    public Vector2 velocity;
    VelocityComponent(Vector2 velocity) {
        this.velocity = velocity;
    }

    @Override
    public List<Class<? extends Component>> requiredComponents() {
        return new RequiredComponentsBuilder()
            .add(TransformComponent.class)
            .build();
    }
}
