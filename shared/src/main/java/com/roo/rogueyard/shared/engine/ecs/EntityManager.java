package com.roo.rogueyard.shared.engine.ecs;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * The manager which handles creating, destroying or otherwise affecting entities.
 */
public final class EntityManager {
    private static final EntityManager instance = new EntityManager();

    private final int maxId = 16777216; // 2^24

    private int nextId = 0;
    private boolean[] destroyed = new boolean[maxId];

    private List<Entity> markedForDestruction = new ArrayList<>();

    private HashMap<Integer, Entity> idEntityMap = new HashMap<>();

    private EntityManager() {
    }

    /**
     * If there is entity with id 'id', return that entity object. Otherwise, returns blank entity with error ID ( -1 ) :>
     */
    public Entity getEntity(Integer id) {
        return idEntityMap.getOrDefault(id, new Entity(-1));
    }

    /**
     * Registers an Entity to this manager.
     */
    public void register(Entity entity) {
        idEntityMap.put(entity.id(), entity);
    }

    /**
     * Returns the EntityManager instance
     */
    public static EntityManager getInstance() {
        return instance;
    }

    /**
     * Returns an entity with the next free entity id.
     */
    public Entity createEntity() {
        if (nextId > maxId) {
            throw new RuntimeException("Max entities reached");
        }

        return new Entity(nextId++);
    }

    /**
     * Marks an entity for destruction upon calling `cleanUpEntities`.
     */
    public void destroyEntity(Entity entity) {
        if (nextId < entity.id()) {
            throw new RuntimeException("Entity does not exist");
        }

        if (!destroyed[entity.id()]) {
            destroyed[entity.id()] = true;
            markedForDestruction.add(entity);
        }
    }

    /**
     * Returns if the given entity is marked for destruction or destroyed.
     */
    public boolean existsEntity(Entity entity) {
        return !destroyed[entity.id()];
    }

    /**
     * Removes all components for all entities marked for destruction.
     */
    public void cleanUpEntities() {
        for (var entity : markedForDestruction) {
            entity.removeAllComponents();
        }

        markedForDestruction.clear();
    }

    /**
     * Removes all entities and their components and resets the next free id
     * to 0.
     */
    public void clearEntities() {
        for (int i = 0; i < nextId; i++) {
            var entity = new Entity(i);
            if (existsEntity(entity)) {
                destroyEntity(entity);
            }
        }

        cleanUpEntities();

        nextId = 0;
        destroyed = new boolean[maxId];
    }
}
