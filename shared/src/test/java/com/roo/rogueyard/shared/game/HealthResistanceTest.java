package com.roo.rogueyard.shared.game;

import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.roo.rogueyard.shared.engine.ecs.ECSManager;
import com.roo.rogueyard.shared.engine.ecs.EntityManager;

class HealthResistanceTest {
    @BeforeAll
    static void setUp() {
        new ResistanceSystem();
        new HealthSystem();
    }

    @BeforeEach
    void setUpEach() {
        EntityManager.getInstance().clearEntities();
    }

    @Test
    void testBasicDamage() {
        float maxHealth = 100;
        float damage = 10;

        var entity = EntityManager.getInstance().createEntity();
        entity.addComponent(new HealthComponent(maxHealth));

        var health = entity.getComponent(HealthComponent.class);
        health.accumulatedDamage.basic += damage;

        assertTrue(health.health == maxHealth);

        ECSManager.getInstance().update(0);

        assertTrue(health.health == maxHealth - damage);
    }

    @Test
    void testMultipleDamageTypes() {
        float maxHealth = 100;
        float basicDamage = 10;
        float rangedDamage = 40;

        var entity = EntityManager.getInstance().createEntity();
        entity.addComponent(new HealthComponent(maxHealth));

        var health = entity.getComponent(HealthComponent.class);
        health.accumulatedDamage.basic += basicDamage;
        health.accumulatedDamage.ranged += rangedDamage;

        assertTrue(health.health == maxHealth);

        ECSManager.getInstance().update(0);

        assertTrue(health.health == maxHealth - basicDamage - rangedDamage);
    }

    @Test
    void testDamageWithResistance() {
        float maxHealth = 100;
        float damage = 10;
        float resistance = 0.5f;

        var entity = EntityManager.getInstance().createEntity();
        entity.addComponent(new HealthComponent(maxHealth));
        entity.addComponent(new ResistanceComponent(
                new DamageResistance(resistance, 1f, 1f, 1f, 1f)));

        var health = entity.getComponent(HealthComponent.class);
        health.accumulatedDamage.basic += damage;

        assertTrue(health.health == maxHealth);

        ECSManager.getInstance().update(0);

        assertTrue(health.health == maxHealth - damage * resistance);
    }
}
