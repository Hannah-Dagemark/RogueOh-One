package com.roo.rogueyard.shared.engine.utils.seed;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Pseudo random seed holder, which can be used to get deterministic random generators from a set seed or factors of a set seed.
 * This allows sets of functionality to become wholly deterministic, given that a set Random is only used for a specified purpose,
 * and as such there is no chance of Random calls happening out-of-order from one run to the other.
 */
public class PseudoSeed {
    private final int seed;
    private int currentReferenceId;
    private final Map<RandomRef, Random> randomGenerators;
    private final Random miscRandomizer;

    public PseudoSeed(int seed) {
        this.seed = seed;
        this.randomGenerators = new HashMap<>();
        this.miscRandomizer = new Random(seed);
        this.currentReferenceId = 0;
    }

    /**
     * Create new Random instance based on the given seed, and register it with the PseudoSeed instance.
     * @return a reference object used to get said generated Random with the getRandom() function.
     */
    public RandomRef createNewRandom() {
        RandomRef newReference = new RandomRef(currentReferenceId);
        currentReferenceId++;
        randomGenerators.put(newReference, new Random(this.seed));
        return newReference;
    }

    /**
     * Create new Random instance based on the given seed, and register it with the PseudoSeed instance.
     * @param seedOffsetFactor factor with which the base seed is multiplied before being used to seed the new Random.
     *                         This is useful if you want multiple randoms for the same set of functionality, without
     *                         running the risk of getting the same outcome on both parallel Random call histories.
     * @return a reference object used to get said generated Random with the getRandom() function
     */
    public RandomRef createNewRandom(long seedOffsetFactor) {
        RandomRef newReference = new RandomRef(currentReferenceId);
        currentReferenceId++;
        randomGenerators.put(newReference, new Random(this.seed*seedOffsetFactor));
        return newReference;
    }

    /**
     * Get a Random instance created and registered with the createNewRandom() function for RNG functionality, from a Random Reference object.
     * @param referenceInstance Random reference object used to retrieve a specific Random instance.
     * @return That Random instance object, used for RNG.
     */
    public Random getRandom(RandomRef referenceInstance) {
        return randomGenerators.get(referenceInstance);
    }

    /**
     * Get a generic Random which is used for smaller non-deterministic RNG still to be decided by the seed. Only use this
     * when you specifically DO NOT want deterministic outcomes, otherwise use createNewRandom() and getRandom()!
     * @return the misc Random instance
     */
    public Random getMiscRandom() {
        return this.miscRandomizer;
    }

}
