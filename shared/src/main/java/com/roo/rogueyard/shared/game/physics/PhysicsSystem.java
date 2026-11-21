package com.roo.rogueyard.shared.game.physics;

import com.roo.rogueyard.shared.engine.ecs.ComponentAddedListener;
import com.roo.rogueyard.shared.engine.ecs.ComponentManager;
import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;

/**
 * Implements Box2D physics in the ECS.
 */
public class PhysicsSystem extends ECSSystemSingle<PhysicsBodyComponent> implements ComponentAddedListener {
    public PhysicsSystem() {
        super(PhysicsBodyComponent.class);

        ComponentManager.getInstance().addComponentAddedListener(this);
    }

    @Override
    protected void updateSingle(Entity entity, PhysicsBodyComponent physicsBody) {
        var transform = entity.getComponent(TransformComponent.class);

        transform.position.x += 1;
    }

    @Override
    public void onComponentAdded(Entity entity, Class<?> componentType, Object component) {
        if (componentType == PhysicsBodyComponent.class) {
            component = (PhysicsBodyComponent) component;
            // TODO: Add into the physics world.
        }
    }
}
