package com.roo.rogueyard.shared.engine.ecs;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ArrayList;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.game.VisualComponent;
import com.roo.rogueyard.shared.game.VisualSystem;
import com.roo.rogueyard.shared.game.VisualType;
import com.roo.rogueyard.shared.game.event.CreateVisualEvent;
import com.roo.rogueyard.shared.game.event.DestroyVisualEvent;
import com.roo.rogueyard.shared.game.event.GameEventBus;
import com.roo.rogueyard.shared.game.physics.TransformComponent;

class OnComponentAddedRemovedListenerTest {
    private static final GameEventBus eventBus = new GameEventBus();

    @BeforeAll
    static void setUp() {
        ComponentManager.getInstance().registerComponent(TransformComponent.class);

        new VisualSystem(eventBus);
    }

    @BeforeEach
    void setUpEach() {
        EntityManager.getInstance().clearEntities();
    }

    @Test
    void testAddVisualComponent() {
        var entity = EntityManager.getInstance().createEntity();
        entity.addComponent(new TransformComponent(new Vector2()));

        // Adding the component leads to a CreateVisualEvent being queued.
        entity.addComponent(new VisualComponent(VisualType.PLAYER));

        var happened = new ArrayList<>();
        eventBus.subscribe(
                CreateVisualEvent.class,
                (e) -> happened.add(true));

        // Before the update the event should not have been recieved.
        assertTrue(happened.isEmpty());

        eventBus.dispatch();

        // After an update the event should have been recieved by the listener.
        assertTrue(happened.size() == 1);
    }

    @Test
    void testRemoveVisualComponent() {
        var entity = EntityManager.getInstance().createEntity();
        entity.addComponent(new TransformComponent(new Vector2()));
        entity.addComponent(new VisualComponent(VisualType.PLAYER));

        // Removing the component leads to a DestroyVisualEvent being queued.
        entity.removeComponent(VisualComponent.class);

        var happened = new ArrayList<>();
        eventBus.subscribe(
                DestroyVisualEvent.class,
                (e) -> happened.add(true));

        // Before the update the event should not have been recieved.
        assertTrue(happened.isEmpty());

        eventBus.dispatch();

        // After an update the event must have been recieved by the listener.
        assertTrue(happened.size() == 1);
    }
}
