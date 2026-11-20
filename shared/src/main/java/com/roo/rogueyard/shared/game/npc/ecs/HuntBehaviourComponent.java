package com.roo.rogueyard.shared.game.npc.ecs;

import com.roo.rogueyard.shared.engine.ecs.Component;
import com.roo.rogueyard.shared.engine.ecs.Entity;

/**
 * Behaviuor component for entities which are 'hunting' other entities. In this case, 'hunting' means chasing towards
 * a target entity at a set hunt speed. If an entity has a hunt component, it will continously move towards the hunt
 * target at a hunt speed.
 */
public class HuntBehaviourComponent implements Component {
    public Entity huntTarget;
    public float huntSpeed;

    public HuntBehaviourComponent(Entity huntTarget, float huntSpeed) {
        this.huntTarget = huntTarget;
        this.huntSpeed = huntSpeed;
    }
}
