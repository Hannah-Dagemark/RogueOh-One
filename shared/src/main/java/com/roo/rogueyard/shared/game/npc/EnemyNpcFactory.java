package com.roo.rogueyard.shared.game.npc;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.engine.ecs.EntityManager;
import com.roo.rogueyard.shared.game.AllegianceType;
import com.roo.rogueyard.shared.game.HealthComponent;
import com.roo.rogueyard.shared.game.npc.ecs.TrackingBehaviourComponent;
import com.roo.rogueyard.shared.game.physics.TransformComponent;

public class EnemyNpcFactory extends NpcFactory {

    @Override
    public Entity createNPC(){
        Entity newEnemy = EntityManager.getInstance().createEntity();
        newEnemy.addComponent(new TransformComponent(new Vector2(0,0), new Vector2(1 ,1), 0));
        newEnemy.addComponent(new HealthComponent(100));
        newEnemy.addComponent(new TrackingBehaviourComponent(500, AllegianceType.HOSTILE));
        return newEnemy;
    };
}
