package com.roo.rogueyard.shared.engine.ecs;

/**
 * Handles everything to do with the ECS.
 */
public final class ECSManager {
    private static final ECSManager instance = new ECSManager();

    private float deltaTime = 0;
    private float elapsedTime = 0;

    private ECSManager() {
    }

    public static ECSManager getInstance() {
        return instance;
    }

    /**
     * Returns the deltaTime of the latest update.
     */
    public float getDeltaTime() {
        return deltaTime;
    }

    /**
     * Returns the elapsed time of the latest update.
     */
    public float getElapsedTime() {
        return elapsedTime;
    }

    public void update(float deltaTime) {
        this.deltaTime = deltaTime;
        elapsedTime += deltaTime;

        SystemManager.getInstance().update();

        EntityManager.getInstance().cleanUpEntities();
    }
}
