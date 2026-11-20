package com.roo.rogueyard.shared.game.npc.ecs;

import com.roo.rogueyard.shared.engine.ecs.Component;

/**
 * Abstract representation of a behaviour component. For now, this is mainly a template for creating new behaviour components.
 * In the future it might be used for things like inheritance which could allow for behaviour centralization or shared systems.
 */
public abstract class BehaviourComponent implements Component {

    public BehaviourComponent() {
    }
}
