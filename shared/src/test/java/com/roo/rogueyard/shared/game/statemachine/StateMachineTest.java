package com.roo.rogueyard.shared.game.statemachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class StateMachineTest {
    private GraphBundleRef<TestContext> createBasicTransitionGraphBundle() {
        var builder = new GraphBundleBuilder<TestContext>();

        var main = builder.newGraph();

        var first = main.newState();
        var second = main.newState();

        first.transition(c -> true, second);

        second.transition(c -> true, first, ctx -> ctx.setPassed(true));

        main.initial(first);

        return builder
                .initial(main)
                .build();
    }

    @Test
    void basicTranitionTest() {
        var ctx = new TestContext(createBasicTransitionGraphBundle());

        var graphBundle = GraphBundleManager
                .getInstance().get(ctx.graphBundleRef());

        graphBundle.update(ctx, 0);
        graphBundle.update(ctx, 0);

        assertEquals(true, ctx.passed());
    }

    private GraphBundleRef<TestContext> createGraphTransitionGraphBundle() {
        var builder = new GraphBundleBuilder<TestContext>();

        var main = builder.newGraph();
        var secondary = builder.newGraph();

        var mainFirst = main.newState();
        var mainSecond = main.newState();

        mainFirst.transition(c -> true, mainSecond);

        mainSecond.transition(c -> true, secondary);

        main.initial(mainFirst);

        var secondaryFirst = secondary.newState();
        var secondarySecond = secondary.newState();

        secondaryFirst.transition(c -> true, secondarySecond);

        secondarySecond.transition(
                c -> true,
                secondaryFirst,
                ctx -> ctx.setPassed(true));

        secondary.initial(secondaryFirst);

        return builder
                .initial(main)
                .build();
    }

    @Test
    void graphTransitionTest() {
        var ctx = new TestContext(createGraphTransitionGraphBundle());

        var graphBundle = GraphBundleManager
                .getInstance().get(ctx.graphBundleRef());

        graphBundle.update(ctx, 0);
        graphBundle.update(ctx, 0);
        graphBundle.update(ctx, 0);
        graphBundle.update(ctx, 0);

        assertEquals(true, ctx.passed());
    }

    private GraphBundleRef<TestContext> createOnEnterGraphBundle() {
        var builder = new GraphBundleBuilder<TestContext>();

        var main = builder.newGraph();

        var first = main.newState();
        var second = main.newState();

        first.transition(c -> true, second);

        second.onEnter(ctx -> ctx.setPassed(true));

        main.initial(first);

        return builder
                .initial(main)
                .build();
    }

    @Test
    void onEnterTest() {
        var ctx = new TestContext(createOnEnterGraphBundle());

        var graphBundle = GraphBundleManager
                .getInstance().get(ctx.graphBundleRef());

        graphBundle.update(ctx, 0);

        assertEquals(true, ctx.passed());
    }

    private GraphBundleRef<TestContext> createOnExitGraphBundle() {
        var builder = new GraphBundleBuilder<TestContext>();

        var main = builder.newGraph();

        var first = main.newState();
        var second = main.newState();

        first
                .onExit(ctx -> ctx.setPassed(true))
                .transition(c -> true, second);

        main.initial(first);

        return builder
                .initial(main)
                .build();
    }

    @Test
    void onExitTest() {
        var ctx = new TestContext(createOnExitGraphBundle());

        var graphBundle = GraphBundleManager
                .getInstance().get(ctx.graphBundleRef());

        graphBundle.update(ctx, 0);

        assertEquals(true, ctx.passed());
    }

    private GraphBundleRef<TestContext> createFromAnyGraphBundle() {
        var builder = new GraphBundleBuilder<TestContext>();

        var main = builder.newGraph();

        var first = main.newState();
        var second = main.newState();

        main
                .initial(first)
                .transitionFromAny(
                        c -> true, second, ctx -> ctx.setPassed(true));

        return builder
                .initial(main)
                .build();
    }

    @Test
    void fromAnyTest() {
        var ctx = new TestContext(createFromAnyGraphBundle());

        var graphBundle = GraphBundleManager
                .getInstance().get(ctx.graphBundleRef());

        graphBundle.update(ctx, 0);

        assertEquals(true, ctx.passed());
    }

    private GraphBundleRef<TestContext> createFromAnyToGraphGraphBundle() {
        var builder = new GraphBundleBuilder<TestContext>();

        var main = builder.newGraph();
        var secondary = builder.newGraph();

        var mainFirst = main.newState();

        main
                .initial(mainFirst)
                .transitionFromAny(c -> true, secondary);

        var secondaryFirst = secondary.newState();
        var secondarySecond = secondary.newState();

        secondaryFirst.transition(
                c -> true,
                secondarySecond,
                ctx -> ctx.setPassed(true));

        secondary.initial(secondaryFirst);

        return builder
                .initial(main)
                .build();
    }

    @Test
    void fromAnyToGraphTest() {
        var ctx = new TestContext(createFromAnyToGraphGraphBundle());

        var graphBundle = GraphBundleManager
                .getInstance().get(ctx.graphBundleRef());

        graphBundle.update(ctx, 0);
        graphBundle.update(ctx, 0);

        assertEquals(true, ctx.passed());
    }

    private GraphBundleRef<TestContext> createGraphFromAnyGraphBundle() {
        var builder = new GraphBundleBuilder<TestContext>();

        var main = builder.newGraph();
        var secondary = builder.newGraph();

        var mainFirst = main.newState();

        main.initial(mainFirst);

        var secondaryFirst = secondary.newState();

        secondary.initial(secondaryFirst);

        return builder
                .initial(main)
                .transitionFromAny(c -> true, secondary, ctx -> ctx.setPassed(true))
                .build();
    }

    @Test
    void graphFromAnyTest() {
        var ctx = new TestContext(createGraphFromAnyGraphBundle());

        var graphBundle = GraphBundleManager
                .getInstance().get(ctx.graphBundleRef());

        graphBundle.update(ctx, 0);
        graphBundle.update(ctx, 0);

        assertEquals(true, ctx.passed());
    }

    @Test
    void missingInitialStateTest() {
        var builder = new GraphBundleBuilder<TestContext>();

        var main = builder.newGraph();

        main.newState();

        assertThrows(IllegalStateException.class,
                () -> builder
                        .initial(main)
                        .build());
    }

    @Test
    void missingInitialGraphTest() {
        var builder = new GraphBundleBuilder<TestContext>();

        var main = builder.newGraph();

        main.newState();

        assertThrows(IllegalStateException.class, () -> builder.build());
    }

    /** Minimal context used for testing. */
    class TestContext extends StateMachineContext<TestContext> {
        private boolean passed = false;

        TestContext(GraphBundleRef<TestContext> graphBundleRef) {
            super(graphBundleRef);
        }

        /** If the test passed. */
        public boolean passed() {
            return passed;
        }

        /** Set if the test passed. */
        public void setPassed(boolean passed) {
            this.passed = passed;
        }
    }
}
