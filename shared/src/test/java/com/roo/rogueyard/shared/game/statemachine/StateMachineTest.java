package com.roo.rogueyard.shared.game.statemachine;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import org.junit.jupiter.api.Test;

class StateMachineTest {
    private GraphBundleRef<TestContext> createBasicTransitionGraphBundle() {
        var builder = new GraphBundleBuilder<TestContext>();

        GraphRef main = builder.createGraph();

        GraphBuilder<TestContext> mainBuilder = builder.define(main);

        State first = mainBuilder.createState();
        State second = mainBuilder.createState();

        mainBuilder.define(first)
                .transition(c -> true, second);

        mainBuilder.define(second)
                .transition(c -> true, first, ctx -> ctx.setPassed(true));

        mainBuilder.initial(first);

        return builder
                .initial(main)
                .build(TestContext.class);
    }

    @Test
    void basicTranitionTest() {
        var ctx = new TestContext(createBasicTransitionGraphBundle());

        var graphBundle = GraphBundleManager
                .getInstance().get(ctx.graphBundleRef());

        graphBundle.update(ctx, 0);

        assertEquals(false, ctx.passed());

        graphBundle.update(ctx, 0);

        assertEquals(true, ctx.passed());
    }

    private GraphBundleRef<TestContext> createGraphTransitionGraphBundle() {
        var builder = new GraphBundleBuilder<TestContext>();

        GraphRef main = builder.createGraph();
        GraphRef secondary = builder.createGraph();

        GraphBuilder<TestContext> mainBuilder = builder.define(main);

        State mainFirst = mainBuilder.createState();
        State mainSecond = mainBuilder.createState();

        mainBuilder.define(mainFirst)
                .transition(c -> true, mainSecond);

        mainBuilder.define(mainSecond)
                .transition(c -> true, secondary);

        mainBuilder.initial(mainFirst);

        GraphBuilder<TestContext> secondaryBuilder = builder.define(secondary);

        State secondaryFirst = secondaryBuilder.createState();
        State secondarySecond = secondaryBuilder.createState();

        secondaryBuilder.define(secondaryFirst)
                .transition(c -> true, secondarySecond);

        secondaryBuilder.define(secondarySecond)
                .transition(c -> true, secondaryFirst, ctx -> ctx.setPassed(true));

        secondaryBuilder.initial(secondaryFirst);

        return builder
                .initial(main)
                .build(TestContext.class);
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

        GraphRef main = builder.createGraph();

        GraphBuilder<TestContext> mainBuilder = builder.define(main);

        State first = mainBuilder.createState();
        State second = mainBuilder.createState();

        mainBuilder.define(first)
                .transition(c -> true, second);

        mainBuilder.define(second)
                .onEnter(ctx -> ctx.setPassed(true));

        mainBuilder.initial(first);

        return builder
                .initial(main)
                .build(TestContext.class);
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

        GraphRef main = builder.createGraph();

        GraphBuilder<TestContext> mainBuilder = builder.define(main);

        State first = mainBuilder.createState();
        State second = mainBuilder.createState();

        mainBuilder.define(first)
                .onExit(ctx -> ctx.setPassed(true))
                .transition(c -> true, second);

        mainBuilder.initial(first);

        return builder
                .initial(main)
                .build(TestContext.class);
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

        GraphRef main = builder.createGraph();

        GraphBuilder<TestContext> mainBuilder = builder.define(main);

        State first = mainBuilder.createState();
        State second = mainBuilder.createState();

        mainBuilder.define(first);

        mainBuilder
                .initial(first)
                .transitionFromAny(c -> true, second, ctx -> ctx.setPassed(true));

        return builder
                .initial(main)
                .build(TestContext.class);
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

        GraphRef main = builder.createGraph();
        GraphRef secondary = builder.createGraph();

        GraphBuilder<TestContext> mainBuilder = builder.define(main);

        State mainFirst = mainBuilder.createState();

        mainBuilder
                .initial(mainFirst)
                .transitionFromAny(c -> true, secondary);

        GraphBuilder<TestContext> secondaryBuilder = builder.define(secondary);

        State secondaryFirst = secondaryBuilder.createState();
        State secondarySecond = secondaryBuilder.createState();

        secondaryBuilder.define(secondaryFirst)
                .transition(c -> true, secondarySecond, ctx -> ctx.setPassed(true));

        secondaryBuilder.initial(secondaryFirst);

        return builder
                .initial(main)
                .build(TestContext.class);
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

        GraphRef main = builder.createGraph();
        GraphRef secondary = builder.createGraph();

        GraphBuilder<TestContext> mainBuilder = builder.define(main);

        State mainFirst = mainBuilder.createState();

        mainBuilder.initial(mainFirst);

        GraphBuilder<TestContext> secondaryBuilder = builder.define(secondary);

        State secondaryFirst = secondaryBuilder.createState();

        secondaryBuilder.initial(secondaryFirst);

        return builder
                .initial(main)
                .transitionFromAny(c -> true, secondary, ctx -> ctx.setPassed(true))
                .build(TestContext.class);
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

        GraphRef main = builder.createGraph();

        GraphBuilder<TestContext> mainBuilder = builder.define(main);

        mainBuilder.createState();

        assertThrows(IllegalStateException.class,
                () -> builder
                        .initial(main)
                        .build(TestContext.class));
    }

    @Test
    void missingInitialGraphTest() {
        var builder = new GraphBundleBuilder<TestContext>();

        GraphRef main = builder.createGraph();

        GraphBuilder<TestContext> mainBuilder = builder.define(main);

        mainBuilder.createState();

        assertThrows(IllegalStateException.class,
                () -> builder.build(TestContext.class));
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
