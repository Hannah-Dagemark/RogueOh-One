package com.roo.rogueyard.shared.game.physics;

import com.roo.rogueyard.shared.engine.ecs.ComponentListener;
import com.roo.rogueyard.shared.engine.ecs.ComponentManager;
import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;

/**
 * Implements Box2D physics in the ECS.
 */
public class PhysicsSystem extends ECSSystemSingle<PhysicsBodyComponent> implements ComponentListener {
    public PhysicsSystem() {
        super(PhysicsBodyComponent.class);
        ComponentManager.getInstance().addComponentListener(this);
    }

    @Override
    protected void updateSingle(Entity entity, PhysicsBodyComponent physicsBody) {
        var transform = entity.getComponent(TransformComponent.class);
    }

    @Override
    public void onComponentAdded(Entity entity, Class<?> componentType, Object component) {
        if (componentType == PhysicsBodyComponent.class) {
            component = (PhysicsBodyComponent) component;
            // TODO: Add into the physics world.
        }
    }
}
