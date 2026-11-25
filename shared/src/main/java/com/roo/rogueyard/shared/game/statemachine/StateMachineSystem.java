package com.roo.rogueyard.shared.game.statemachine;

import java.util.List;

import com.roo.rogueyard.shared.engine.ecs.ECSManager;
import com.roo.rogueyard.shared.engine.ecs.ECSSystemSingle;
import com.roo.rogueyard.shared.engine.ecs.Entity;

public class StateMachineSystem<C extends StateMachineComponent<T>, T extends StateMachineContext>
        extends ECSSystemSingle<C> {
    private final GraphRegistry<T> graphRegistry;

    public StateMachineSystem(Class<C> componentType, GraphRegistry<T> graphRegistry) {
        super(componentType);

        this.graphRegistry = graphRegistry;
    }

    @Override
    protected void updateSingle(Entity entity, C component) {
        var ctx = component.ctx;
        var graph = graphRegistry.get(ctx.graphId());

        ctx.setTimeInState(
                ctx.timeInState() + ECSManager.getInstance().getDeltaTime());

        var transition = nextTransition(graph, ctx);
        if (transition == null) {
            return;
        }

        var from = graph.stateDefinitions().get(ctx.state().id());
        var to = graph.stateDefinitions().get(transition.target().id());

        run(from.onExit(), ctx);

        if (transition.action() != null) {
            transition.action().execute(ctx);
        }

        ctx.setState(transition.target());
        ctx.setTimeInState(0);

        run(to.onEnter(), ctx);
    }

    private Transition<T> nextTransition(Graph<T> graph, T ctx) {
        for (Transition<T> transition : graph.fromAnyTransitions()) {
            if (transition.matches(ctx)) {
                return transition;
            }
        }
        for (Transition<T> transition : graph.stateDefinitions()
                .get(ctx.state().id()).transitions())
            if (transition.matches(ctx))
                return transition;
        return null;
    }

    private void run(List<Action<T>> actions, T ctx) {
        for (var action : actions) {
            action.execute(ctx);
        }
    }
}
