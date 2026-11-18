package com.roo.rogueyard.shared.game;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.engine.ecs.EntityManager;

public final class PlayerFactory {
    private static final float playerMaxHealth = 100;

    private PlayerFactory() {
    }

    public static Entity createPlayer() {
        Entity player = EntityManager.getInstance().createEntity();
        player.addComponent(new HealthComponent(playerMaxHealth));
        player.addComponent(new TransformComponent(new Vector2(0, 0), new Vector2(1, 1), 0));
        player.addComponent(new VisualComponent(VisualType.PLAYER, true));
        return player;
    }
}
