package com.roo.rogueyard.shared.game.statemachine;

import java.util.ArrayList;
import java.util.List;

public final class StateDefinitionBuilder<T extends StateMachineContext> {
    private final List<Action<T>> onEnters = new ArrayList<>();
    private final List<Action<T>> onExits = new ArrayList<>();
    private final List<Transition<T>> transitions = new ArrayList<>();

    StateDefinitionBuilder() {
    }

    @SafeVarargs
    public final StateDefinitionBuilder<T> onEnter(Action<T>... a) {
        onEnters.addAll(List.of(a));
        return this;
    }

    @SafeVarargs
    public final StateDefinitionBuilder<T> onExit(Action<T>... a) {
        onExits.addAll(List.of(a));
        return this;
    }

    public final StateDefinitionBuilder<T> transition(
            Condition<T> condition,
            State target,
            Action<T> onTrigger) {
        transitions.add(new Transition<>(condition, target, onTrigger));
        return this;
    }

    final StateDefinition<T> build(State initialState) {
        return new StateDefinition<T>(initialState, onEnters, onExits, transitions);
    }
}
