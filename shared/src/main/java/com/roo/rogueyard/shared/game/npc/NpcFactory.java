package com.roo.rogueyard.shared.game.npc;

import com.roo.rogueyard.shared.engine.ecs.Entity;

/**
 * Generic NPC factory interface, use this to create entities and create spawner functions which take these as argument.
 */
public interface NpcFactory {
    Entity createNPC();
}
