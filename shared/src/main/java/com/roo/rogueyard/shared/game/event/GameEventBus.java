package com.roo.rogueyard.shared.game.event;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * An event bus that allows the view to subscribe to certian events instead of
 * all at once.
 */
public class GameEventBus {
    private final Map<Class<? extends GameEvent>, List<Consumer<? extends GameEvent>>> listeners = new HashMap<>();

    private final Queue<GameEvent> pending = new ArrayDeque<>();

    public <T extends GameEvent> void subscribe(Class<T> eventType, Consumer<T> listener) {
        listeners.computeIfAbsent(eventType, key -> new ArrayList<>())
                .add(listener);
    }

    public <T extends GameEvent> void unsubscribe(Class<T> eventType, Consumer<T> listener) {
        List<Consumer<? extends GameEvent>> eventListeners = listeners.get(eventType);
        if (eventListeners != null) {
            eventListeners.remove(listener);
        }
    }

    /**
     * Queues an event. It will be delivered on the next dispatch() call.
     */
    public <T extends GameEvent> void publish(T event) {
        pending.add(event);
    }

    /**
     * Delivers all queued events, including events published by listeners
     * while dispatching.
     */
    public void dispatch() {
        GameEvent event;
        while ((event = pending.poll()) != null) {
            deliver(event);
        }
    }

    @SuppressWarnings({ "unchecked", "rawtypes" })
    private void deliver(GameEvent event) {
        List<Consumer<? extends GameEvent>> eventListeners = listeners.get(event.getClass());
        if (eventListeners == null) {
            return;
        }

        for (Consumer<? extends GameEvent> listener : new ArrayList<>(eventListeners)) {
            ((Consumer) listener).accept(event);
        }
    }

    public void clear() {
        listeners.clear();
        pending.clear();
    }
}
