package com.roo.rogueyard.shared.game.combat;

import com.roo.rogueyard.shared.game.statemachine.Condition;

final class WeaponConditions {
    public static Condition<WeaponContext> timeInState(float seconds) {
        return c -> c.timeInState() >= seconds;
    }
}
