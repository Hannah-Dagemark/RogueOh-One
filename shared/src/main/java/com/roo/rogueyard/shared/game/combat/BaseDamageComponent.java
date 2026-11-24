package com.roo.rogueyard.shared.game.combat;

import com.roo.rogueyard.shared.engine.ecs.Component;
import com.roo.rogueyard.shared.game.DamageTypes;

/**
 * Represents the base damage of an entity.
 */
public class BaseDamageComponent implements Component {
    public final DamageTypes baseDamage;

    public BaseDamageComponent(DamageTypes baseDamage) {
        this.baseDamage = baseDamage;
    }
}
