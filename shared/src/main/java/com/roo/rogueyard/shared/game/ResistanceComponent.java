package com.roo.rogueyard.shared.game;

import java.util.List;

import com.roo.rogueyard.shared.engine.ecs.Component;
import com.roo.rogueyard.shared.engine.ecs.ComponentWithRequirements;
import com.roo.rogueyard.shared.engine.ecs.RequiredComponentsBuilder;

/**
 * Holds factor resistances to the various types of damage.
 */
public class ResistanceComponent implements ComponentWithRequirements {
    public DamageResistance resistance;

    /**
     * A resistance component without any resistance.
     */
    public ResistanceComponent() {
        this.resistance = new DamageResistance();
    }

    /**
     * A resistance component with the given resistances.
     */
    public ResistanceComponent(DamageResistance resistance) {
        this.resistance = resistance;
    }

    @Override
    public List<Class<? extends Component>> requiredComponents() {
        return new RequiredComponentsBuilder()
                .add(HealthComponent.class)
                .build();
    }
}
