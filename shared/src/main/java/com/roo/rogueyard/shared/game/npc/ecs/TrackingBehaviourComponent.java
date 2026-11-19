package com.roo.rogueyard.shared.game.npc.ecs;

import com.roo.rogueyard.shared.engine.ecs.Component;
import com.roo.rogueyard.shared.game.AllegianceType;

public class TrackingBehaviourComponent implements Component {
    public float trackingDistance;
    public AllegianceType allegiance; /*TODO: Move allegiance to some Combat component, as it is a prerequisite to be able to be in or be a target of 'combat'*/

    public TrackingBehaviourComponent(float trackingDistance, AllegianceType allegiance) {
        this.trackingDistance = trackingDistance;
        this.allegiance = allegiance;
    }
}
