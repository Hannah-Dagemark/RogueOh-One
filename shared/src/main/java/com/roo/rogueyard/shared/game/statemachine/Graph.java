package com.roo.rogueyard.shared.game.statemachine;

import java.util.List;

// TODO: Make record constructors private
public record Graph<T extends StateMachineContext>(
        List<StateDefinition<T>> stateDefinitions,
        List<Transition<T>> fromAnyTransitions,
        State initialState) {
}
