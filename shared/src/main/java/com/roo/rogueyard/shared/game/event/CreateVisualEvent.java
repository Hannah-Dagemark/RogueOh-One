package com.roo.rogueyard.shared.game.event;

import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.VisualComponent;

public record CreateVisualEvent(Entity entity, VisualComponent visualComponent) implements GameEvent {
}
