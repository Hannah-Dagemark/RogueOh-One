package com.roo.rogueyard.shared.game;

import com.roo.rogueyard.shared.engine.ecs.ComponentManager;
import com.roo.rogueyard.shared.engine.ecs.ECSManager;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.combat.BaseDamageComponent;
import com.roo.rogueyard.shared.game.combat.WeaponComponent;
import com.roo.rogueyard.shared.game.combat.WeaponFactory;
import com.roo.rogueyard.shared.game.event.CompositeEventBus;
import com.roo.rogueyard.shared.game.event.GameEventBus;
import com.roo.rogueyard.shared.game.npc.EnemyNpcFactory;
import com.roo.rogueyard.shared.game.npc.ecs.HuntBehaviourSystem;
import com.roo.rogueyard.shared.game.npc.ecs.TrackingBehaviourSystem;
import com.roo.rogueyard.shared.game.physics.PhysicsSystem;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.physics.VelocitySystem;
import com.roo.rogueyard.shared.game.player.PlayerFactory;
import com.roo.rogueyard.shared.game.statemachine.StateMachineSystem;

/**
 * Holds the game state.
 */
public final class GameModel {
    private final CompositeEventBus eventBus = new CompositeEventBus();

    private Entity player;
    private Entity[] enemies;

    private WeaponFactory weaponFactory;

    public GameModel() {
        initComponents();
        initSystems();
        initFactories();
        initEntities();
    }

    private void initEntities() {
        player = PlayerFactory.createPlayer();
        weaponFactory.createBasicGun();

        /* EnemyNpcFactory enemyFactory = new EnemyNpcFactory();
        enemies = new Entity[3];
        for (int i = 0; i <= 2; i++) {
            enemies[i] = enemyFactory.createNPC();
        }*/
    }

    /**
     * Sets up all ECS components that don't have a primary system that
     * initializes them.
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
        new ResistanceSystem();
        new HealthSystem();
        new PhysicsSystem();
        new TrackingBehaviourSystem();
        new HuntBehaviourSystem();
        new VelocitySystem();
        new StateMachineSystem<>(WeaponComponent.class);
        new VisualSystem(eventBus);
    }

    /** Creates all factories. */
    private void initFactories() {
        weaponFactory = new WeaponFactory();
    }

    /**
     * Returns the game event bus that can be used to send events
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
