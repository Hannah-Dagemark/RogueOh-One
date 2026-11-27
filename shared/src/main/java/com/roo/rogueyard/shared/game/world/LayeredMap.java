package com.roo.rogueyard.shared.game.world;

import com.roo.rogueyard.shared.engine.ecs.EntityManager;
import com.roo.rogueyard.shared.engine.utils.seed.PseudoSeed;
import com.roo.rogueyard.shared.engine.utils.seed.RandomRef;

public class LayeredMap {
    private final WalkableLayer[] layers;
    private int currentLayer;
    private final int finalPosition;
    private final RandomRef enemyRandomizer;
    private final RandomRef generationRandomizer;
    private final RandomRef difficultyRandomizer;



    public LayeredMap(int size, PseudoSeed seed) {
        this.enemyRandomizer = seed.createNewRandom();
        this.generationRandomizer = seed.createNewRandom();
        this.difficultyRandomizer = seed.createNewRandom();
        this.layers = generateLayers(size, seed);
        this.finalPosition = size-1;
        this.currentLayer = 0;
    }

    public void descendLayer() {
        if (!(this.currentLayer == this.finalPosition)) {
            getCurrentLayer().clearLayer();
            EntityManager.getInstance().cleanUpEntities();
            this.currentLayer++;
            getCurrentLayer().activateLayer();
        } else {
            WorldManager.getInstance().newGamePlus();
        }
    }

    public WalkableLayer getCurrentLayer() {
        return this.layers[currentLayer];
    }

    private WalkableLayer[] generateLayers(int size, PseudoSeed seed) {
        WalkableLayer[] layerArray = new WalkableLayer[size];

        for (int i = 0; i < size; i++) {
            int difficultySpread = seed.getRandom(this.difficultyRandomizer).nextInt(-1,1)*Math.ceilDiv(i, 3);
            layerArray[i] = new WalkableLayer(this, seed, difficultySpread+i, this.generationRandomizer, this.enemyRandomizer);
        }

        return layerArray;
    }
}
