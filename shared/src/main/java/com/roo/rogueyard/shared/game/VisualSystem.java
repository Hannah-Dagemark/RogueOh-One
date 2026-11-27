package com.roo.rogueyard.shared.game;

import com.roo.rogueyard.shared.engine.ecs.ComponentAddedListener;
import com.roo.rogueyard.shared.engine.ecs.ComponentManager;
import com.roo.rogueyard.shared.engine.ecs.ComponentRemovedListener;
import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.event.CompositeEventBus;
import com.roo.rogueyard.shared.game.event.CreateVisualEvent;
import com.roo.rogueyard.shared.game.event.DestroyVisualEvent;

/**
 * Handles sending events to the GameView whenever a VisualComponent is added.
 */
public class VisualSystem extends ECSSystemSingle<VisualComponent>
        implements ComponentAddedListener, ComponentRemovedListener {
    private CompositeEventBus eventBus;

    public VisualSystem(CompositeEventBus eventBus) {
        super(VisualComponent.class);

        this.eventBus = eventBus;

        ComponentManager.getInstance().addComponentAddedListener(this);
        ComponentManager.getInstance().addComponentRemovedListener(this);
    }

    @Override
    protected void updateSingle(Entity entity, VisualComponent visualComponent) {
    }

    @Override
    public void onComponentAdded(Entity entity, Class<?> componentType, Object component) {
        if (componentType == VisualComponent.class) {
            eventBus.external.publish(new CreateVisualEvent(entity, (VisualComponent) component));
        }
    }

    @Override
    public void onComponentRemoved(Entity entity, Class<?> componentType, Object component) {
        if (componentType == VisualComponent.class) {
            eventBus.external.publish(new DestroyVisualEvent(entity));
        }
    }
}
