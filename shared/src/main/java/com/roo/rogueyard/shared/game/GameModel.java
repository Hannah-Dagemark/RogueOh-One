package com.roo.rogueyard.shared.game;

import com.roo.rogueyard.shared.engine.ecs.ECSManager;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.event.GameEventBus;

/**
 * Holds the game state.
 */
public final class GameModel {
    private GameEventBus eventBus;

    private Entity player;

    public GameModel() {
        initSystems();

        player = PlayerFactory.createPlayer();
    }

    /**
     * Sets up all ECS systems.
     */
    private void initSystems() {
        // Order here is the order systems are executed in.
        new HealthSystem(eventBus);
    }

    public GameEventBus getEventBus() {
        return eventBus;
    }

    public void update(float deltaTime) {
        ECSManager.getInstance().update(deltaTime);
        eventBus.dispatch();
    }
}
