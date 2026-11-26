package com.roo.rogueyard.shared.game.world;

import com.roo.rogueyard.shared.engine.utils.PseudoSeed;

public final class WorldManager {
    private static final WorldManager instance = new WorldManager();

    private PseudoSeed seed;

    private LayeredMap currentMap;

    private int currentMapSize = 30;

    private WorldManager() {}

    public static WorldManager getInstance() {
        return instance;
    }

    public void setSeed(int seedNumber) {
        seed = new PseudoSeed(seedNumber);
    }

    public void generateNewMap() {
        currentMap = new LayeredMap(currentMapSize, seed);
    }

    public void initializeMap() {
        currentMap.getCurrentLayer().activateLayer();
    }

    public void newGamePlus() {
        currentMapSize += 20;
        currentMap.getCurrentLayer().clearLayer();
        generateNewMap();
        initializeMap();
    }

    public WalkableLayer currentLayer() {
        return currentMap.getCurrentLayer();
    }
}
