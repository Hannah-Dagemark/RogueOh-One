package com.roo.rogueyard.shared.game.world;

import com.roo.rogueyard.shared.engine.ecs.EntityManager;

public class LayeredMap {
    private final WalkableLayer[] layers;
    private int currentLayer;



    public LayeredMap(int size, PseudoSeed seed) {
        this.layers = generateLayers(size, seed);
        this.currentLayer = 0;
    }

    public void descendLayer() {
        getCurrentLayer().clearLayer();
        EntityManager.getInstance().cleanUpEntities();
        this.currentLayer++;
        getCurrentLayer().activateLayer();
    }

    public WalkableLayer getCurrentLayer() {
        return this.layers[currentLayer];
    }

    private WalkableLayer[] generateLayers(int size, PseudoSeed seed) {
        WalkableLayer[] layerArray = new WalkableLayer[size];

        for (int i = 0; i < size; i++) {
            int difficultySpread = seed.difficultySpreadRandomizer.nextInt(-1,1)*Math.ceilDiv(i, 3);
            layerArray[i] = new WalkableLayer(this, seed, difficultySpread+i);
        }

        return layerArray;
    }
}
