package com.roo.rogueyard.shared.game.combat;

import com.roo.rogueyard.shared.game.statemachine.GraphBundleRef;
import com.roo.rogueyard.shared.game.statemachine.StateMachineContext;

public class WeaponContext extends StateMachineContext<WeaponContext> {
    /** The cooldown for the weapon action in seconds. */
    public float cooldown;
    /**
     * If true the weapon continuously does its action while the input is held.
     */
    public boolean autoRepeat;

    public WeaponContext(GraphBundleRef<WeaponContext> graphBundleRef, float cooldown, boolean autoRepeat) {
        super(graphBundleRef);

        this.cooldown = cooldown;
        this.autoRepeat = autoRepeat;
    }
}
