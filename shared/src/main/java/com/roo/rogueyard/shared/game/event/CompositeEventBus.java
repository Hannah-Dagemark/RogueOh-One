package com.roo.rogueyard.shared.game.event;

/**
 * Holds two separate event busses to separate events inside and outside the
 * game model.
 */
public class CompositeEventBus {
    /**
     * Strictly used for events subscribed to inside the model.
     */
    public final GameEventBus internal;
    /**
     * Strictly used for events subscribed to outside the model.
     */
    public final GameEventBus external;

    public CompositeEventBus() {
        internal = new GameEventBus();
        external = new GameEventBus();
    }
}
