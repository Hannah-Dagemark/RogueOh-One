package com.roo.rogueyard.shared.game.world;

import com.roo.rogueyard.shared.engine.utils.seed.PseudoSeed;

/**
 * The Manager which handles the playable world. This is the main interface to use when referencing the current
 * world state, including current layer and enemies.
 */
public final class WorldManager {
    private static final WorldManager instance = new WorldManager();
    private WorldManager() {}

    private PseudoSeed seed;
    private LayeredMap currentMap;
    private int currentMapSize = 30;

    /**
     * Get the singleton WorldManager instance
     * @return Singleton WorldManager instance
     */
    public static WorldManager getInstance() {
        return instance;
    }

    /**
     * Set a new seed instance for the World Manager to use when generating the world and entities within it
     * @param newSeed PseudoSeed instance to use for Randomisers.
     */
    public void setSeed(PseudoSeed newSeed) {
        seed = newSeed;
    }

    /**
     * Generate a new map based on the current seed
     */
    public void generateNewMap() {
        currentMap = new LayeredMap(currentMapSize, seed);
    }

    /**
     * Initialise the currently generated map
     */
    public void initializeMap() {
        currentMap.getCurrentLayer().activateLayer();
    }

    /**
     * Clear current map, and generate a new one with increased layer amount
     */
    public void newGamePlus() {
        currentMapSize += 20;
        currentMap.getCurrentLayer().clearLayer();
        generateNewMap();
        initializeMap();
    }

    /**
     * Get the current layer the player is on
     * @return current active layer
     */
    public WalkableLayer currentLayer() {
        return currentMap.getCurrentLayer();
    }
}
