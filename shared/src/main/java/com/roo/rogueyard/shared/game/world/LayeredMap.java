package com.roo.rogueyard.shared.game.world;

import com.roo.rogueyard.shared.engine.ecs.EntityManager;
import com.roo.rogueyard.shared.engine.utils.seed.PseudoSeed;
import com.roo.rogueyard.shared.engine.utils.seed.RandomRef;

/**
 * Holds the information for a playable map, consisting of layers.
 */
public class LayeredMap {
    private final WalkableLayer[] layers;
    private int currentLayer;
    private final int finalPosition;
    private final RandomRef enemyRandomizer;
    private final RandomRef generationRandomizer;
    private final RandomRef difficultyRandomizer;


    /**
     * Constructor args
     * @param size The desired size of the map, where size is the amount of layers
     * @param seed The seed instance which should be used to generate the map, layers, and data therein
     */
    public LayeredMap(int size, PseudoSeed seed) {
        this.enemyRandomizer = seed.createNewRandom();
        this.generationRandomizer = seed.createNewRandom();
        this.difficultyRandomizer = seed.createNewRandom();
        this.layers = generateLayers(size, seed);
        this.finalPosition = size-1;
        this.currentLayer = 0;
    }

    /**
     * Move down a layer in the map, clearing the current layer and loading the next one. If the current layer is the
     * last layer, it runs the newGamePlus() function in the WorldManager
     */
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

    /**
     * Get the current active layer
     * @return the current active WalkableLayer instance
     */
    public WalkableLayer getCurrentLayer() {
        return this.layers[currentLayer];
    }

    /**
     * Private layer generator function
     * @param size the desired amount of layers to be generated
     * @param seed the seed to be used for generating the layers and in their subsequent randomiser-dependent functionalities
     * @return a fixed array of generated layer instances
     */
    private WalkableLayer[] generateLayers(int size, PseudoSeed seed) {
        WalkableLayer[] layerArray = new WalkableLayer[size];

        for (int i = 0; i < size; i++) {
            int difficultySpread = seed.getRandom(this.difficultyRandomizer).nextInt(-1,1)*Math.ceilDiv(i, 3);
            layerArray[i] = new WalkableLayer(this, seed, difficultySpread+i, this.generationRandomizer, this.enemyRandomizer);
        }

        return layerArray;
    }
}
