package com.roo.rogueyard.shared.game.npc.ecs;

import com.roo.rogueyard.shared.engine.ecs.Component;
import com.roo.rogueyard.shared.engine.ecs.Entity;

public class HuntBehaviourComponent implements Component {
    public Entity huntTarget;
    public float huntSpeed;

    public HuntBehaviourComponent(Entity huntTarget, float huntSpeed) {
        this.huntTarget = huntTarget;
        this.huntSpeed = huntSpeed;
    }
}
