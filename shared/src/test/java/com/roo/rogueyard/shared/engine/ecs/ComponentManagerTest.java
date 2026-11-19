package com.roo.rogueyard.shared.engine.ecs;

import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.VisualComponent;
import com.roo.rogueyard.shared.game.VisualType;

class ComponentManagerTest {
    @BeforeEach
    void setUp() {
        EntityManager.getInstance().clearEntities();

        var componentManager = ComponentManager.getInstance();
        componentManager.registerComponent(TransformComponent.class);
        componentManager.registerComponent(VisualComponent.class);
    }

    @Test
    void testAddSameComponent() {
        var entity = EntityManager.getInstance().createEntity();
        entity.addComponent(new TransformComponent(new Vector2()));

        // Adding another component of the same type is an error.
        assertThrows(RuntimeException.class,
                () -> entity.addComponent(new TransformComponent(new Vector2())));
    }

    @Test
    void testAddComponentMissingRequirements() {
        var entity = EntityManager.getInstance().createEntity();

        // Adding a VisualComponent without its required TransformComponent
        // is an error.
        assertThrows(RuntimeException.class,
                () -> entity.addComponent(new VisualComponent(VisualType.PLAYER)));
    }

    @Test
    void testRemoveRequiredComponent() {
        var entity = EntityManager.getInstance().createEntity();
        entity.addComponent(new TransformComponent(new Vector2()));
        entity.addComponent(new VisualComponent(VisualType.PLAYER));

        // Removing TransformComponent before VisualComponent which requires
        // it is an error.
        assertThrows(RuntimeException.class,
                () -> entity.removeComponent(TransformComponent.class));
    }
}
