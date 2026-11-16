package com.roo.rogueyard.shared.engine.ecs;

import java.util.ArrayList;
import java.util.List;

public final class EntityManager {
    private static EntityManager instance = new EntityManager();

    private final int maxId = 16777216; // 2^24

    private int nextId = 0;
    private boolean[] destroyed = new boolean[maxId];

    private List<Entity> markedForDestruction = new ArrayList<>();
    private List<Component<?>> components = new ArrayList<>();

    private EntityManager() {
    }

    public static EntityManager getInstance() {
        return instance;
    }

    public Entity createEntity() {
        if (nextId > maxId) {
            throw new RuntimeException("Max entites reached");
        }

        return new Entity(nextId++);
    }

    public void destroyEntity(Entity entity) {
        if (nextId < entity.id()) {
            throw new RuntimeException("Entity does not exist");
        }

        if (!destroyed[entity.id()]) {
            destroyed[entity.id()] = true;
            markedForDestruction.add(entity);
        }
    }

    public boolean existsEntity(Entity entity) {
        return !destroyed[entity.id()];
    }

    /**
     * Remove all components for all entities marked for destruction.
     */
    public void purgeEntities() {
        for (var entity : markedForDestruction) {
            for (var component : components) {
                component.entities.remove(entity.id());
            }
        }

        markedForDestruction.clear();
    }

    /**
     * Remove all entities and their components.
     */
    public void clearEntities() {
        for (int i = 0; i < nextId; i++) {
            var entity = new Entity(i);
            if (existsEntity(entity)) {
                destroyEntity(entity);
            }
        }

        purgeEntities();

        nextId = 0;
        destroyed = new boolean[maxId];
    }

    public void registerComponent(Component<?> component) {
        components.add(component);
    }
}
