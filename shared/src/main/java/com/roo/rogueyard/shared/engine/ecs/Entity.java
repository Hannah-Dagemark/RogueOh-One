package com.roo.rogueyard.shared.engine.ecs;

/**
 * Represents an ECS entity. Has no state on it's own but can be added to
 * components which hold their respective part of the total state.
 */
public class Entity {
    private final int id;

    public Entity(int id) {
        this.id = id;
        EntityManager.getInstance().register(this);
    }

    /**
     * The unique entity id.
     */
    public int id() {
        return id;
    }

    /**
     * Returns the component of the specified entity and type.
     * More intuitive helper method instead of using `ComponentManager`.
     */
    public <T extends Component> T getComponent(Class<T> componentType) {
        return ComponentManager.getInstance().getComponent(this, componentType);
    }

    /**
     * Adds the specified component to the specified entity.
     * More intuitive helper method instead of using `ComponentManager`.
     */
    public <T extends Component> void addComponent(T component) {
        ComponentManager.getInstance().addComponent(this, component);
    }

    /**
     * Removes the specified component from the specified entity.
     * More intuitive helper method instead of using `ComponentManager`.
     */
    public <T extends Component> void removeComponent(Class<T> componentType) {
        ComponentManager.getInstance().removeComponent(this, componentType);
    }

    /**
     * Removes all components from the specified entity.
     * More intuitive helper method instead of using `ComponentManager`.
     */
    public void removeAllComponents() {
        ComponentManager.getInstance().removeAllComponents(this);
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
