package com.roo.rogueyard.shared.game.player;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.engine.ecs.EntityManager;
import com.roo.rogueyard.shared.game.HealthComponent;
import com.roo.rogueyard.shared.game.physics.BodyBlueprintFactory;
import com.roo.rogueyard.shared.game.physics.PhysicsBodyComponent;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.physics.VelocityComponent;
import com.roo.rogueyard.shared.game.VisualComponent;
import com.roo.rogueyard.shared.game.VisualType;

/**
 * Handles creating players.
 */
public final class PlayerFactory {
    private static final float playerMaxHealth = 100;
    private static final float playerBodyRadius = 20;

    private PlayerFactory() {
    }

    /**
     * Creates the default player entity.
     */
    public static Entity createPlayer() {
        Entity player = EntityManager.getInstance().createEntity();
        player.addComponent(new HealthComponent(playerMaxHealth));
        player.addComponent(new TransformComponent(new Vector2(500, 0)));
        player.addComponent(new VisualComponent(VisualType.PLAYER));
        // player.addComponent(new VelocityComponent(new Vector2(0, 0)));
        player.addComponent(new PhysicsBodyComponent(
                BodyBlueprintFactory.createCircle(playerBodyRadius, false, false)));

        return player;
    }
}
