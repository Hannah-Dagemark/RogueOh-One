package com.roo.rogueyard.shared.game.player;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.engine.ecs.EntityManager;
import com.roo.rogueyard.shared.game.HealthComponent;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.VisualComponent;
import com.roo.rogueyard.shared.game.VisualType;
import com.roo.rogueyard.shared.game.physics.VelocityComponent;

/**
 * Handles creating players.
 */
public final class PlayerFactory {
    private static final float playerMaxHealth = 100;

    private PlayerFactory() {
    }

    /**
     * Creates the default player entity.
     */
    public static PlayerEntity createPlayer() {
        PlayerEntity player = new PlayerEntity(EntityManager.getInstance().claimEntityId());
        player.addComponent(new HealthComponent(playerMaxHealth));
        player.addComponent(new TransformComponent(new Vector2(500, 200)));
        player.addComponent(new VisualComponent(VisualType.PLAYER));
        player.addComponent(new VelocityComponent(new Vector2(0, 0)));

        // TODO: Create body for player
        /* player.addComponent(new PhysicsBodyComponent(null)); */

        return player;
    }
}
