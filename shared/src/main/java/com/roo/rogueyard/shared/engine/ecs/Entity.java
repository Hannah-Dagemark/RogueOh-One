package com.roo.rogueyard.shared.engine.ecs;

/**
 * Represents an ECS entity. Has no state on it's own but can be added to
 * components which hold their respective part of the total state.
 */
public final class Entity {
    private final int id;

    public Entity(int id) {
        this.id = id;
    }

    /**
     * The unique entity id.
     */
    public int id() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        return o instanceof Entity other && id == other.id;
    }

    @Override
    public int hashCode() {
        return Integer.hashCode(id);
    }

    @Override
    public String toString() {
        return "Entity(" + id + ")";
    }
}
