package com.roo.rogueyard.shared.game;

/**
 * Holds one value, between 0 and 1, for every damage type, giving the facotr
 * resistance to that damage type.
 */
public class DamageResistance extends DamageTypes {
    /**
     * A damage resistance without any resistances.
     */
    public DamageResistance() {
        super(1, 1, 1, 1, 1);
    }

    /**
     * A damage resistance with the specified resistances.
     */
    public DamageResistance(
            float basic,
            float melee,
            float ranged,
            float bleed,
            float poison) {
        super(
                basic,
                melee,
                ranged,
                bleed,
                poison);
    }

    /**
     * Applies the resistances to the given accumulated damage.
     */
    public void apply(AccumulatedDamage damage) {
        damage.basic *= this.basic;
        damage.melee *= this.melee;
        damage.ranged *= this.ranged;
        damage.bleed *= this.bleed;
        damage.poison *= this.poison;
    }
}
