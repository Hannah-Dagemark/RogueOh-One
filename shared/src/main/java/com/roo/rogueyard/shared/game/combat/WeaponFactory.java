package com.roo.rogueyard.shared.game.combat;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.engine.ecs.EntityManager;
import com.roo.rogueyard.shared.game.DamageTypes;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.statemachine.GraphBundleBuilder;
import com.roo.rogueyard.shared.game.statemachine.GraphBundleRef;

public class WeaponFactory {
    private GraphBundleRef<WeaponContext> basicGunGraphBundleRef;

    public WeaponFactory() {
        initGraphBundles();
    }

    public Entity createBasicGun() {
        Entity gun = EntityManager.getInstance().createEntity();

        gun.addComponent(new TransformComponent(new Vector2()));
        gun.addComponent(new BaseDamageComponent(new DamageTypes(1, 0, 0, 0, 0)));
        gun.addComponent(new WeaponComponent(new WeaponContext(
                basicGunGraphBundleRef,
                0.5f,
                true)));

        return gun;
    }

    private void initGraphBundles() {
        initBasicGunGraphBundle();
    }

    private void initBasicGunGraphBundle() {
        var builder = new GraphBundleBuilder<WeaponContext>();

        var main = builder.newGraph();

        var idle = main.newState();
        var swing = main.newState();

        /* TODO: Create a real weapon. */
        idle.transition(WeaponConditions.cooldown(), swing,
                ctx -> System.out.println("-> Shoot"));

        swing.transition(c -> true, idle,
                ctx -> System.out.println("-> Idle"));

        main.initial(idle);

        basicGunGraphBundleRef = builder
                .initial(main)
                .build();
    }
}
