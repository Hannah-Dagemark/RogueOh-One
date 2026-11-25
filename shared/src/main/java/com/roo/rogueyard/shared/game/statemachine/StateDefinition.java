package com.roo.rogueyard.shared.game.statemachine;

import java.util.List;

record StateDefinition<T extends StateMachineContext>(
        State state,
        List<Action<T>> onEnter,
        List<Action<T>> onExit,
        List<Transition<T>> transitions) {
}
