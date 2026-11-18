package com.roo.rogueyard.shared.game;

import com.roo.rogueyard.shared.engine.ecs.Component;

/**
 * Holds the data required to select a sprite for this entity inside view.
 */
public final class VisualComponent implements Component {
    public VisualType visualType;
    public boolean visible;

    public VisualComponent(VisualType visualType) {
        this(visualType, true);
    }

    public VisualComponent(VisualType visualType, boolean visible) {
        this.visualType = visualType;
        this.visible = visible;
    }
}
