package com.roo.rogueyard.shared.game;

import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.engine.ecs.EntityManager;

public final class PlayerFactory {
    private static final float playerMaxHealth = 100;

    private PlayerFactory() {
    }

    public static Entity createPlayer() {
        Entity player = EntityManager.getInstance().createEntity();
        player.addComponent(new HealthComponent(playerMaxHealth));
        return player;
    }
}
