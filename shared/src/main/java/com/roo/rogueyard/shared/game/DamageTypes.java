package com.roo.rogueyard.shared.game;

/**
 * Holds one value for every damage type.
 */
public class DamageTypes {
    public float basic;
    public float melee;
    public float ranged;
    public float bleed;
    public float poison;

    public DamageTypes(
            float basic,
            float melee,
            float ranged,
            float bleed,
            float poison) {
        this.basic = basic;
        this.melee = melee;
        this.ranged = ranged;
        this.bleed = bleed;
        this.poison = poison;
    }
}
