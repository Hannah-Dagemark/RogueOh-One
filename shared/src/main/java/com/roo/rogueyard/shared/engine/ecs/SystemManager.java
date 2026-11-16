package com.roo.rogueyard.shared.engine.ecs;

import java.util.ArrayList;
import java.util.List;

public class SystemManager {
    private static SystemManager instance = new SystemManager();

    private List<System> systems = new ArrayList<>();

    private SystemManager() {
    }

    public static SystemManager getInstance() {
        return instance;
    }

    public void registerSystem(System system) {
        systems.add(system);
    }

    public void update() {
        for (var system : systems) {
            system.update();
        }
    }
}
