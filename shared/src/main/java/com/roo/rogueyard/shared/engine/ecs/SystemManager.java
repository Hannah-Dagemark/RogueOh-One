package com.roo.rogueyard.shared.engine.ecs;

import java.util.ArrayList;
import java.util.List;

/**
 * Handles actions that affect all systems.
 */
public final class SystemManager {
    private static SystemManager instance = new SystemManager();

    private final List<ECSSystem<?>> systems = new ArrayList<>();

    private SystemManager() {
    }

    public static SystemManager getInstance() {
        return instance;
    }

    /**
     * Registers a system to this manager.
     */
    public void register(ECSSystem<?> system) {
        systems.add(system);
    }

    /**
     * Runs `update` on all systems.
     */
    public void update() {
        for (var system : systems) {
            system.update();
        }
    }

    /**
     * Unassigns an entity from all systems.
     */
    public void unassignFromAll(Entity entity) {
        for (var system : systems) {
            system.components.remove(entity.id());
        }
    }
}
