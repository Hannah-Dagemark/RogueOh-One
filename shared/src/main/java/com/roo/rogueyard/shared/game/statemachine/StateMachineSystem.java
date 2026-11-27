package com.roo.rogueyard.shared.game.statemachine;

import com.roo.rogueyard.shared.engine.ecs.ECSManager;
import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;

/**
 * Updates all state machine components of type C. T is the type of
 * StateMachineContext used by the state machine component.
 */
public class StateMachineSystem<C extends StateMachineComponent<T>, T extends StateMachineContext<T>>
        extends ECSSystemSingle<C> {
    public StateMachineSystem(Class<C> componentType) {
        super(componentType);
    }

    @Override
    protected void updateSingle(Entity entity, C component) {
        GraphBundle<T> graphBundle = GraphBundleManager
                .getInstance().get(component.ctx.graphBundleRef());

        graphBundle.update(
                component.ctx, ECSManager.getInstance().getDeltaTime());
    }
}
