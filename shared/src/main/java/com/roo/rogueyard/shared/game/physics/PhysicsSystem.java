package com.roo.rogueyard.shared.game.physics;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.roo.rogueyard.shared.engine.ecs.ComponentAddedListener;
import com.roo.rogueyard.shared.engine.ecs.ComponentManager;
import com.roo.rogueyard.shared.engine.ecs.ComponentRemovedListener;
import com.roo.rogueyard.shared.engine.ecs.ECSManager;
import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;

/**
 * Implements Box2D physics in the ECS.
 */
public class PhysicsSystem extends ECSSystemSingle<PhysicsBodyComponent>
        implements ComponentAddedListener, ComponentRemovedListener {
    private final World world = new World(Vector2.Zero, true);

    private static final int positionIterations = 5;
    private static final int velocityIterations = 5;

    public PhysicsSystem() {
        super(PhysicsBodyComponent.class);

        ComponentManager.getInstance().addComponentAddedListener(this);
    }

    @Override
    protected void updateSingle(Entity entity, PhysicsBodyComponent physicsBody) {
        var transform = entity.getComponent(TransformComponent.class);

        transform.position = new Vector2(physicsBody.body.getPosition());
    }

    @Override
    public void update() {
        world.step(
                ECSManager.getInstance().getDeltaTime(),
                positionIterations,
                velocityIterations);

        super.update();
    }

    @Override
    public void onComponentAdded(Entity entity, Class<?> componentType, Object component) {
        if (componentType != PhysicsBodyComponent.class) {
            return;
        }

        var physicsBody = (PhysicsBodyComponent) component;

        var body = world.createBody(physicsBody.bodyBlueprint.bodyDef());

        if (physicsBody.bodyBlueprint.fixtureDef() != null) {
            body.createFixture(physicsBody.bodyBlueprint.fixtureDef());
        }

        body.setType(physicsBody.bodyBlueprint.bodyType());
        body.setBullet(physicsBody.bodyBlueprint.isBullet());

        var transform = entity.getComponent(TransformComponent.class);
        body.setTransform(transform.position, transform.rotation);

        physicsBody.body = body;
    }

    @Override
    public void onComponentRemoved(Entity entity, Class<?> componentType, Object component) {
        if (componentType != PhysicsBodyComponent.class) {
            return;
        }

        var physicsBody = (PhysicsBodyComponent) component;

        world.destroyBody(physicsBody.body);
    }
}
