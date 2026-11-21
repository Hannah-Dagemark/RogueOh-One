package com.roo.rogueyard.shared.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.roo.rogueyard.shared.engine.ecs.ComponentManager;
import com.roo.rogueyard.shared.engine.ecs.ECSManager;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.event.CompositeEventBus;
import com.roo.rogueyard.shared.game.event.GameEventBus;
import com.roo.rogueyard.shared.game.npc.EnemyNpcFactory;
import com.roo.rogueyard.shared.game.npc.ecs.HuntBehaviourSystem;
import com.roo.rogueyard.shared.game.npc.ecs.TrackingBehaviourSystem;
import com.roo.rogueyard.shared.game.physics.PhysicsSystem;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.physics.VelocitySystem;
import com.roo.rogueyard.shared.game.physics.VelocitySystem;
import com.roo.rogueyard.shared.game.player.PlayerEntity;
import com.roo.rogueyard.shared.game.player.PlayerFactory;
import com.roo.rogueyard.shared.game.player.PlayerInputProcessor;

/**
 * Holds the game state.
 */
public final class GameModel {
    private final CompositeEventBus eventBus = new CompositeEventBus();

    private Entity player;
    private Entity[] enemies;
    private final PlayerEntity player;

    public GameModel() {
        initComponents();
        initSystems();
        initEntities();
    }

    private void initEntities() {
        player = PlayerFactory.createPlayer();

        initInput();

        EnemyNpcFactory enemyFactory = new EnemyNpcFactory();
        enemies = new Entity[3];
        for (int i = 0; i <= 2; i++) {
            enemies[i] = enemyFactory.createNPC();
        }
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
        new ResistanceSystem();
        new HealthSystem();
        new PhysicsSystem();
        new TrackingBehaviourSystem();
        new HuntBehaviourSystem();
        new VelocitySystem();
        new VisualSystem(eventBus);
        new VelocitySystem();
    }

    private void initInput() {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new PlayerInputProcessor(player));
        Gdx.input.setInputProcessor(multiplexer);
    }

    /**
     * Returns the game event bus that can be used to send and recieve events
     * from the GameModel to the GameView.
     */
    public GameEventBus getExternalEventBus() {
        return eventBus.external;
    }

    /**
     * Update the game state.
     */
    public void update(float deltaTime) {
        ECSManager.getInstance().update(deltaTime);

        eventBus.internal.dispatch();
        eventBus.external.dispatch();
    }
}
