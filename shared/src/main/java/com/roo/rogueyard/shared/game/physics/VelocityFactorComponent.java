package com.roo.rogueyard.shared.game.physics;

import com.roo.rogueyard.shared.engine.ecs.Component;
import com.roo.rogueyard.shared.engine.ecs.ComponentWithRequirements;
import com.roo.rogueyard.shared.engine.ecs.RequiredComponentsBuilder;

import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

/**
 * Velocity Factor component which holds scalar factors to be applied to the velocity of an entity. Resultant factor
 * additiveVelocityFactor should only be manipulated using addition and subtraction. Remeber to add when effect begins, then remove the same amount when it ends!
 * multiplicativeVelocityFactor should only be manipulated using multiplication and division. Remember to multiply when effect begins, then remove same amount when it ends!
 * additive change: "Add +25% speed boost to player" = additiveVelocityFactor += 0.25
 * multiplicative change: "Boosts speed by 10%" = multiplicativeVelocityFactor *= 1.10
 * These are multiplied together before being added as a scalar to velocity position change.
 */
public class VelocityFactorComponent implements ComponentWithRequirements {
    public Map<String, Float> additiveFactorSources;
    public Map<String, Float> multiplicativeFactorSources;

    public VelocityFactorComponent() {
        this.additiveFactorSources = new HashMap<>();
        this.multiplicativeFactorSources = new HashMap<>();
    }

    @Override
    public List<Class<? extends Component>> requiredComponents() {
        return new RequiredComponentsBuilder()
            .add(VelocityComponent.class)
            .build();
    }
}
