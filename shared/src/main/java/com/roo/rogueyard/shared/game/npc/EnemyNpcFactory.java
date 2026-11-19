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

public class EnemyNpcFactory extends NpcFactory {

    @Override
    public Entity createNPC(){
        Entity newEnemy = EntityManager.getInstance().createEntity();
        newEnemy.addComponent(new TransformComponent(new Vector2(0,0), new Vector2(0.5F, 0.5F), 0));
        newEnemy.addComponent(new VelocityComponent(new Vector2(0,0)));
        newEnemy.addComponent(new HealthComponent(100));
        newEnemy.addComponent(new TrackingBehaviourComponent(1000, AllegianceType.HOSTILE));
        newEnemy.addComponent(new VisualComponent(VisualType.PLAYER, true));
        return newEnemy;
    };
}
