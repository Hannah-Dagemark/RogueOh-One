package com.roo.rogueyard.shared.game;

/**
 * Holds temporary damage data that is later applied to the health. The
 * different damage types can then be modified by resistances before they are
 * applied.
 */
public class AccumulatedDamage extends DamageTypes {
    /**
     * An accumulated damage with initial zero damage.
     */
    public AccumulatedDamage() {
        super(0, 0, 0, 0, 0);
    }

    /**
     * The total accumulated damage.
     */
    public float total() {
        return basic + melee + ranged + bleed + poison;
    }

    /**
     * Sets all accumulated damage to zero.
     */
    public void reset() {
        basic = 0;
        melee = 0;
        ranged = 0;
        bleed = 0;
        poison = 0;
    }
}
