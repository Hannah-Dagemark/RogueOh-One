package com.roo.rogueyard.shared.engine.ecs;

/**
 * Handles all other managers.
 */
public final class GameManager {
    private static GameManager instance = new GameManager();

    private GameManager() {
    }

    public static GameManager getInstance() {
        return instance;
    }

    public void update() {
        SystemManager.getInstance().update();

        EntityManager.getInstance().cleanUpEntities();
    }
}
