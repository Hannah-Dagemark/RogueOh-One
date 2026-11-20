package com.roo.rogueyard.shared.game.npc.ecs;

import com.roo.rogueyard.shared.engine.ecs.Component;
import com.roo.rogueyard.shared.game.AllegianceType;

/**
 * Component which makes the entity 'scan' for entities which have positions within the set trackingDistance.
 * It then uses allegiance, if the other entity has it, to determine if it should hunt that entity.
 */
public class TrackingBehaviourComponent implements Component {
    public float trackingDistance;
    public AllegianceType allegiance; /*TODO: Move allegiance to some Combat component, as it is a prerequisite to be able to be in or be a target of 'combat'*/

    public TrackingBehaviourComponent(float trackingDistance, AllegianceType allegiance) {
        this.trackingDistance = trackingDistance;
        this.allegiance = allegiance;
    }
}
