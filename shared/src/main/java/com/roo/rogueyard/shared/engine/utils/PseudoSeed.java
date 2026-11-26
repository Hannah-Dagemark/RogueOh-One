package com.roo.rogueyard.shared.engine.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PseudoSeed {
    private final int seed;
    private Map<String, Random> randomGenerators;
    private Random miscRandomizer;

    public PseudoSeed(int seed) {
        this.seed = seed;
        this.randomGenerators = new HashMap<>();
        this.miscRandomizer = new Random(seed);
    }

    public Random createNewRandom(String tag) {
        randomGenerators.put(tag, new Random(this.seed));
        return randomGenerators.get(tag);
    }

    public Random createNewRandom(String tag, long seedOffsetFactor) {
        randomGenerators.put(tag, new Random(this.seed*seedOffsetFactor));
        return randomGenerators.get(tag);
    }

    public Random getRandom(String tag) {
        return randomGenerators.get(tag);
    }

    public Random getMiscRandom() {
        return this.miscRandomizer;
    }

}
