package com.roo.rogueyard.shared.game.combat;

import java.util.List;

import com.roo.rogueyard.shared.engine.ecs.Component;
import com.roo.rogueyard.shared.engine.ecs.ComponentWithRequirements;
import com.roo.rogueyard.shared.engine.ecs.RequiredComponentsBuilder;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.statemachine.StateMachineComponent;

/**
 * Holds the parameters for a weapon.
 */
public class WeaponComponent extends StateMachineComponent<WeaponContext> implements ComponentWithRequirements {
    public WeaponComponent(WeaponContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public List<Class<? extends Component>> requiredComponents() {
        return new RequiredComponentsBuilder()
                .add(TransformComponent.class)
                .add(BaseDamageComponent.class)
                .build();
    }
}
