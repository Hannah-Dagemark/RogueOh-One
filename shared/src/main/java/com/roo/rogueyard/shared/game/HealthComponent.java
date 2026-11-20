package com.roo.rogueyard.shared.game;

import com.roo.rogueyard.shared.engine.ecs.Component;

public class HealthComponent implements Component {
    public float health;
    public float maxHealth;
    public float accumulatedHealing;
    public AccumulatedDamage accumulatedDamage;

    public HealthComponent(float maxHealth) {
        this.health = maxHealth;
        this.maxHealth = maxHealth;
    }
}
