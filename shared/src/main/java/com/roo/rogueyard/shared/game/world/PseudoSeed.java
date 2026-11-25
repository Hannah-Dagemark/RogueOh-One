package com.roo.rogueyard.shared.game.world;

import java.util.Random;

public class PseudoSeed {
    private final int seed;
    public Random layerGenerationRandomizer;
    public Random enemySpawnRandomizer;
    public Random difficultySpreadRandomizer;

    public PseudoSeed(int seed) {
        this.seed = seed;
        this.layerGenerationRandomizer = new Random(seed);
        this.enemySpawnRandomizer = new Random(seed);
        this.difficultySpreadRandomizer = new Random(seed);
    }

    public int getSeed() {
        return seed;
    }
}
