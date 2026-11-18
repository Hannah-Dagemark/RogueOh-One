package com.roo.rogueyard.shared.game;

import com.roo.rogueyard.shared.engine.ecs.ComponentManager;
import com.roo.rogueyard.shared.engine.ecs.ECSManager;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.event.GameEventBus;

/**
 * Holds the game state.
 */
public final class GameModel {
    private final GameEventBus eventBus = new GameEventBus();

    private Entity player;

    public GameModel() {
        initComponents();
        initSystems();

        player = PlayerFactory.createPlayer();
    }

    /**
     * Sets up all ECS components that don't have a primary system that
     * initializes them.
     */
    private void initComponents() {
        ComponentManager.getInstance().registerComponent(TransformComponent.class);
    }

    /**
     * Sets up all ECS systems.
     */
    private void initSystems() {
        // Order here is the order systems are executed in.
        new HealthSystem();
        new VisualSystem(eventBus);
        new PhysicsSystem();
    }

    public GameEventBus getEventBus() {
        return eventBus;
    }

    public void update(float deltaTime) {
        ECSManager.getInstance().update(deltaTime);
        eventBus.dispatch();
    }
}
