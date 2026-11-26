package com.roo.rogueyard.shared.game.statemachine;

import java.util.List;

/** Represents the definition of a state within a graph. */
record StateDefinition<T extends StateMachineContext>(
        /** The actions that should be executed when entering this state. */
        List<Action<T>> onEnter,
        /** The actions that should be executed when exiting this state. */
        List<Action<T>> onExit,
        /** The outgoing transitions from this state in order of priority. */
        List<Transition<T>> transitions) {
}
