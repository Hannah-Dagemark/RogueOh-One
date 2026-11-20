package com.roo.rogueyard.shared.game.event;

import com.roo.rogueyard.shared.engine.ecs.Entity;

/**
 * Represents the event that a visual element needs to be removed that
 * previously represented an entity in the GameModel.
 */
public record DestroyVisualEvent(Entity entity) implements GameEvent {
}
