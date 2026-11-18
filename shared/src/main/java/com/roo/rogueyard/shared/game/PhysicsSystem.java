package com.roo.rogueyard.shared.game;

import com.roo.rogueyard.shared.engine.ecs.ComponentListener;
import com.roo.rogueyard.shared.engine.ecs.ComponentManager;
import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;

public class PhysicsSystem extends ECSSystemSingle<PhysicsBodyComponent> implements ComponentListener {
    public PhysicsSystem() {
        super(PhysicsBodyComponent.class);
        ComponentManager.getInstance().addComponentListener(this);
    }

    @Override
    protected void updateSingle(Entity entity, PhysicsBodyComponent physicsBodyComponent) {
        var transformComponent = entity.getComponent(TransformComponent.class);

        // physicsBodyComponent.body;
    }

    @Override
    public void onComponentAdded(Entity entity, Class<?> componentType, Object component) {
        if (componentType == PhysicsBodyComponent.class) {
            component = (PhysicsBodyComponent) component;
        }
    }
}
