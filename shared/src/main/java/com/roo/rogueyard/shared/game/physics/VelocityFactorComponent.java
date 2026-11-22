package com.roo.rogueyard.shared.game.physics;

import com.roo.rogueyard.shared.engine.ecs.Component;
import com.roo.rogueyard.shared.engine.ecs.ComponentWithRequirements;
import com.roo.rogueyard.shared.engine.ecs.RequiredComponentsBuilder;

import java.util.List;

public class VelocityFactorComponent implements ComponentWithRequirements {
    public float additiveVelocityFactor;
    public float multiplicativeVelocityFactor;

    public VelocityFactorComponent() {
        this.additiveVelocityFactor = 1;
        this.multiplicativeVelocityFactor = 1;
    }

    @Override
    public List<Class<? extends Component>> requiredComponents() {
        return new RequiredComponentsBuilder()
            .add(VelocityComponent.class)
            .build();
    }
}
