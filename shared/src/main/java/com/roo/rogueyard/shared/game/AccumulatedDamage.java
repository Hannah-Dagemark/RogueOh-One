package com.roo.rogueyard.shared.game;

/**
 * Holds temporary damage data that is later applied to the health. The
 * different damage types can then be modified by resistances before they are
 * applied.
 */
public class AccumulatedDamage {
    public float basic;
    public float melee;
    public float ranged;
    public float bleed;

    public AccumulatedDamage() {
        reset();
    }

    public void reset() {
        basic = 0;
        melee = 0;
        ranged = 0;
        bleed = 0;
    }

    public float total() {
        return basic + melee + ranged + bleed;
    }
}
