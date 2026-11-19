package com.roo.rogueyard.shared.game;

import java.util.List;

import com.roo.rogueyard.shared.engine.ecs.Component;
import com.roo.rogueyard.shared.engine.ecs.ComponentWithRequirements;
import com.roo.rogueyard.shared.engine.ecs.RequiredComponentsBuilder;
import com.roo.rogueyard.shared.game.physics.TransformComponent;

/**
 * Holds the data required to select a sprite for this entity inside view.
 */
public final class VisualComponent implements ComponentWithRequirements {
    public VisualType visualType;
    public boolean visible;

    /**
     * Create a visual component with the given visual type and visible set to
     * true.
     */
    public VisualComponent(VisualType visualType) {
        this(visualType, true);
    }

    /**
     * Create a visual component with the given visual type and given visible.
     */
    public VisualComponent(VisualType visualType, boolean visible) {
        this.visualType = visualType;
        this.visible = visible;
    }

    @Override
    public List<Class<? extends Component>> requiredComponents() {
        return new RequiredComponentsBuilder()
                .add(TransformComponent.class)
                .build();
    }
}
