package com.roo.rogueyard.shared.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.roo.rogueyard.shared.engine.ecs.ComponentManager;
import com.roo.rogueyard.shared.engine.ecs.ECSManager;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.combat.BaseDamageComponent;
import com.roo.rogueyard.shared.game.combat.WeaponComponent;
import com.roo.rogueyard.shared.game.event.CompositeEventBus;
import com.roo.rogueyard.shared.game.event.GameEventBus;
import com.roo.rogueyard.shared.game.npc.EnemyNpcFactory;
import com.roo.rogueyard.shared.game.npc.ecs.HuntBehaviourSystem;
import com.roo.rogueyard.shared.game.npc.ecs.TrackingBehaviourSystem;
import com.roo.rogueyard.shared.game.physics.PhysicsSystem;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.physics.VelocityFactorSystem;
import com.roo.rogueyard.shared.game.physics.VelocitySystem;
import com.roo.rogueyard.shared.game.player.PlayerEntity;
import com.roo.rogueyard.shared.game.player.PlayerFactory;
import com.roo.rogueyard.shared.game.player.PlayerInputProcessor;
import com.roo.rogueyard.shared.game.statemachine.StateMachineSystem;

/**
 * Holds the game state.
 */
public final class GameModel {
    private final CompositeEventBus eventBus = new CompositeEventBus();

    private Entity[] enemies;
    private final PlayerEntity player;

    public GameModel() {
        initComponents();
        initSystems();
        initEntities();

        player = PlayerFactory.createPlayer();

        /* Must happen AFTER player creation */
        initInput(player);
    }

    /**
     * Initialise all non-player entities. At the moment used for demo, this will be
     * moved to layer-specific
     * functionality later on.
     */
    /* TODO: Move to Layer's concern once layer is implemented. */
    private void initEntities() {
        EnemyNpcFactory enemyFactory = new EnemyNpcFactory();
        enemies = new Entity[3];
        for (int i = 0; i <= 2; i++) {
            enemies[i] = enemyFactory.createNPC();
        }
    }

    /**
     * Sets up all ECS components that don't have a primary system that
     * initialises them.
     */
    private void initComponents() {
        ComponentManager.getInstance().registerComponent(TransformComponent.class);
        ComponentManager.getInstance().registerComponent(BaseDamageComponent.class);
    }

    /**
     * Sets up all ECS systems.
     */
    private void initSystems() {
        // Order here is the order systems are executed in.
        new StateMachineSystem<>(WeaponComponent.class);
        new ResistanceSystem();
        new HealthSystem();
        new PhysicsSystem();
        new TrackingBehaviourSystem();
        new HuntBehaviourSystem();
        new VelocitySystem();
        new VelocityFactorSystem();
        new VisualSystem(eventBus);
    }

    /**
     * Initialise input processors which are attached to the Gdx input multiplexer.
     */
    private void initInput(PlayerEntity player) {
        InputMultiplexer multiplexer = new InputMultiplexer();
        multiplexer.addProcessor(new PlayerInputProcessor(player));
        Gdx.input.setInputProcessor(multiplexer);
    }

    /**
     * Returns the game event bus that can be used to send and receive events
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
