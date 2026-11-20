package com.roo.rogueyard.shared.game;

import com.roo.rogueyard.shared.engine.ecs.ComponentListener;
import com.roo.rogueyard.shared.engine.ecs.ComponentManager;
import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.event.CompositeEventBus;
import com.roo.rogueyard.shared.game.event.CreateVisualEvent;

/**
 * Handles sending events to the GameView whenever a VisualComponent is added.
 */
public class VisualSystem extends ECSSystemSingle<VisualComponent> implements ComponentListener {
    private CompositeEventBus eventBus;

    public VisualSystem(CompositeEventBus eventBus) {
        super(VisualComponent.class);

        this.eventBus = eventBus;

        ComponentManager.getInstance().addComponentListener(this);
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
}
