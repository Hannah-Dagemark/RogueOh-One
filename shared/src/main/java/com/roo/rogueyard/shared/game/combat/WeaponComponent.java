package com.roo.rogueyard.shared.game.combat;

import java.util.List;

import com.roo.rogueyard.shared.engine.ecs.Component;
import com.roo.rogueyard.shared.engine.ecs.ComponentWithRequirements;
import com.roo.rogueyard.shared.engine.ecs.RequiredComponentsBuilder;
import com.roo.rogueyard.shared.game.physics.TransformComponent;

/**
 * Holds the parameters for a weapon.
 */
public class WeaponComponent implements ComponentWithRequirements {
    /** The cooldown for the weapon action in seconds. */
    public float cooldown;
    /**
     * If true the weapon continuously does its action while the input is held.
     */
    public boolean autoRepeat;
    /** The weapon action that is run when the weapon attacks. */
    public WeaponAction action;

    public WeaponComponent() {
    }

    @Override
    public List<Class<? extends Component>> requiredComponents() {
        return new RequiredComponentsBuilder()
                .add(TransformComponent.class)
                .add(BaseDamageComponent.class)
                .build();
    }
}
