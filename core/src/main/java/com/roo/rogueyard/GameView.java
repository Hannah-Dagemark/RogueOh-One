package com.roo.rogueyard;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.roo.rogueyard.render.GameRenderable;
import com.roo.rogueyard.render.GameRenderableFactory;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.GameModel;
import com.roo.rogueyard.shared.game.event.CreateVisualEvent;
import com.roo.rogueyard.shared.game.event.DestroyVisualEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * The view part of MVC.
 */
public class GameView {
    private final SpriteBatch batch;
    private final GameRenderableFactory renderableFactory;

    private final Map<Entity, GameRenderable> renderables = new HashMap<>();

    public final Consumer<CreateVisualEvent> createVisualListener = this::onCreateVisualEvent;
    public final Consumer<DestroyVisualEvent> destroyVisualListener = this::onDestroyVisualEvent;

    public GameView(GameModel model) {
        Assets.init();
        batch = new SpriteBatch();
        renderableFactory = new GameRenderableFactory();
    }

    /**
     * Renders everything in the GameView.
     */
    public void render(float deltaTime) {
        batch.begin();
        for (var renderable : renderables.values()) {
            renderable.draw(batch);
        }
        batch.end();
    }

    /**
     * Handles CreateVisualEvent events.
     */
    private void onCreateVisualEvent(CreateVisualEvent event) {
        renderables.put(
                event.entity(),
                renderableFactory.fromVisualComponent(
                        event.visualComponent(),
                        event.entity()));
    }

    /**
     * Handles CreateVisualEvent events.
     */
    private void onDestroyVisualEvent(DestroyVisualEvent event) {
        renderables.remove(event.entity());
    }

    /**
     * Free up the resources taken up by this class.
     */
    public void dispose() {
        batch.dispose();
        Assets.dispose();
    }
}
