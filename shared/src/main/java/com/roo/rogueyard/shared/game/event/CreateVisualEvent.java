package com.roo.rogueyard.shared.game.event;

import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.VisualComponent;

/**
 * Represents the event that a new visual element needs to be created for
 * an object that was newly created in the GameModel.
 */
public record CreateVisualEvent(Entity entity, VisualComponent visualComponent) implements GameEvent {
}
