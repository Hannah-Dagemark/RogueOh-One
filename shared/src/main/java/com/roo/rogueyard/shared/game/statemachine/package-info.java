/**
 * A general state machine system.
 *
 * A state machine is defined for a state machine context. The context is the
 * data that the state machine can read and modify and is individual for every
 * object assigned to the state machine. The state machine is built up of
 * states, transitions, actions, graphs, and graph bundles. States are the
 * smallest building block. They can have transitions, on exit and on enter
 * actions. Actions are functions that can read and modify the context. A graph
 * is a collection of states that can have transitions between them. A graph
 * bundle is a collection of graphs that can have transitions between them.
 *
 * Usage is as follows:
 * Define a subclass {@code T} of {@code StateMachineContext} that holds the
 * data you want your state machine to operate on. Then define a subclass
 * of {@code StateMachineComponent<T>} as the component added to all entities
 * you want to be within the state machine. Then create a
 * {@code new StateMachineSystem<T>(T.class)} that handles those components.
 * Now within the factory where you create your enemies add the state machine
 * component. That factory should also define and store a
 * {@code GraphBundleRef} that defines the state machine graph. The same
 * subclass of {@code StateMachineContext} can have multiple graph bundles
 * for different types of weapons. Each subclass is seperated by what data
 * they hold. For example if one group of weapons all have ammo they should
 * share the same subclass but could have different graph bundles. The following
 * is an example of how to create a graph bundle:
 *
 * <pre>{@code
 * var builder = new GraphBundleBuilder<ExampleContext>();
 *
 * var main = builder.newGraph();
 *
 * var idle = main.newState();
 * var swing = main.newState();
 *
 * idle.transition(WeaponConditions.cooldown(), swing,
 *         ctx -> System.out.println("-> Shoot"));
 *
 * swing.transition(c -> true, idle,
 *         ctx -> System.out.println("-> Idle"));
 *
 * main.initial(idle);
 *
 * exampleGraphBundleRef = builder
 *         .initial(main)
 *         .build();
 * }</pre>
 *
 * Then to add an entity to the state machine:
 *
 * <pre>{@code
 * Entity example = EntityManager.getInstance().createEntity();
 * example.addComponent(new ExampleComponent(
 *          new ExampleContext(exampleGraphBundleRef));
 * }</pre>
 *
 * It will now travel through the state machine on every ECS update!
 */
package com.roo.rogueyard.shared.game.statemachine;
