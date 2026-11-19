package com.roo.rogueyard.shared.engine.ecs;

import java.util.List;

/**
 * A component that also has requirements.
 */
public interface ComponentWithRequirements extends Component {
    /**
     * Returns a list of component types that must be added to an entity
     * before this component is added to that entity.
     */
    List<Class<? extends Component>> requiredComponents();
}
