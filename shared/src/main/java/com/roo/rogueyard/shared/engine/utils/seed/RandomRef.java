package com.roo.rogueyard.shared.engine.utils.seed;

/**
 * Represents a reference to a Random instance in the PseudoSeed class.
 */
public class RandomRef {
    private final int id;

    RandomRef(int id) {
        this.id = id;
    }

    /** The id of the random. */
    public int id() {
        return id;
    }
}
