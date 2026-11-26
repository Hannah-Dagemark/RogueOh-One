package com.roo.rogueyard.shared.game.statemachine;

import java.util.List;

import com.roo.rogueyard.shared.engine.ecs.ECSManager;
import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.statemachine.Target.Type;

/**
 * Updates all state machine components of type C.
 */
public class StateMachineSystem<C extends StateMachineComponent<T>, T extends StateMachineContext>
        extends ECSSystemSingle<C> {
    public StateMachineSystem(Class<C> componentType) {
        super(componentType);
    }

    @Override
    protected void updateSingle(Entity entity, C component) {
        var ctx = component.ctx;

        @SuppressWarnings("unchecked")
        GraphBundle<T> graphBundle = (GraphBundle<T>) GraphBundleManager
                .getInstance().get(ctx.graphBundleRef());

        if (ctx.graphRef() == null) {
            ctx.setGraphRef(graphBundle.initialGraph());
        }

        Graph<T> graph = graphBundle.get(ctx.graphRef());

        if (ctx.state() == null) {
            ctx.setState(graph.initialState());
        }

        ctx.setTimeInState(
                ctx.timeInState() + ECSManager.getInstance().getDeltaTime());

        var transition = graph.nextTransition(ctx);
        if (transition == null) {
            return;
        }

        var from = graph.get(ctx.state());

        run(from.onExit(), ctx);

        if (transition.action() != null) {
            transition.action().execute(ctx);
        }

        ctx.setTimeInState(0);

        if (transition.target().type() == Type.STATE) {
            var targetState = transition.target().asState();

            ctx.setState(targetState);

            run(graph.get(targetState).onEnter(), ctx);
        } else {
            ctx.setGraphRef(transition.target().asGraphRef());
        }
    }

    private void run(List<Action<T>> actions, T ctx) {
        for (var action : actions) {
            action.execute(ctx);
        }
    }
}
