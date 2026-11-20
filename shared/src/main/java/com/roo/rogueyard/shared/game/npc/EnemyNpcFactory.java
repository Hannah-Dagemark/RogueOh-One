package com.roo.rogueyard.shared.game.npc;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.engine.ecs.EntityManager;
import com.roo.rogueyard.shared.game.AllegianceType;
import com.roo.rogueyard.shared.game.HealthComponent;
import com.roo.rogueyard.shared.game.VisualComponent;
import com.roo.rogueyard.shared.game.VisualType;
import com.roo.rogueyard.shared.game.npc.ecs.TrackingBehaviourComponent;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.physics.VelocityComponent;

/**
 * Generic enemy Npc factory, which produces an enemy somewhere in the middle of the screen ( when fullscreen ) which
 * chases the player when it gets within range. At the moment, a good baseline and template for expansion. Maybe a
 * good future superclass to unify 'enemy' functionality and structuring.
 */
public class EnemyNpcFactory implements NpcFactory {
    @Override
    public Entity createNPC(){
        Entity newEnemy = EntityManager.getInstance().createEntity();
        newEnemy.addComponent(new TransformComponent(new Vector2().setToRandomDirection().scl(500).add(800, 500), new Vector2(0.2f, 0.2f), 0));
        newEnemy.addComponent(new VelocityComponent(new Vector2(0,0)));
        newEnemy.addComponent(new HealthComponent(100));
        newEnemy.addComponent(new TrackingBehaviourComponent(500, AllegianceType.HOSTILE));
        newEnemy.addComponent(new VisualComponent(VisualType.ENEMY, true));
        return newEnemy;
    };
}
