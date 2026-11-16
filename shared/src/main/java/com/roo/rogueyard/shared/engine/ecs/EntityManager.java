package com.roo.rogueyard.shared.engine.ecs;

import java.util.List;
import java.util.ArrayList;

import com.roo.rogueyard.shared.engine.utils.Singleton;

public final class EntityManager extends Singleton {
    private final int maxId = 16777216;

    private int nextId = 0;
    private boolean[] destroyed = new boolean[maxId];

    private List<Entity> markedForDestruction = new ArrayList<>();
    private List<Component<?>> components = new ArrayList<>();

    public EntityManager() {
    }

    public Entity CreateEntity() {
        if (nextId > maxId) {
            throw new RuntimeException("Max entites reached");
        }

        return new Entity(nextId++);
    }

    public void DestroyEntity(Entity entity) {
        if (nextId < entity.id()) {
            throw new RuntimeException("Entity does not exist");
        }

        if (!destroyed[entity.id()]) {
            destroyed[entity.id()] = true;
            markedForDestruction.add(entity);
        }
    }

    public void PurgeEntitiesMarkedForDestruction() {
        for (var entity : markedForDestruction) {
            for (var component : components) {
                component.entities.remove(entity.id());
            }
        }
    }

    public boolean EntityExists(Entity entity) {
        return !destroyed[entity.id()];
    }
}
