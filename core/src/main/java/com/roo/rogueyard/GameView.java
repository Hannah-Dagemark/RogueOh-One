package com.roo.rogueyard;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.roo.rogueyard.render.GameRenderable;
import com.roo.rogueyard.render.GameRenderableFactory;
import com.roo.rogueyard.shared.game.GameModel;
import com.roo.rogueyard.shared.game.event.CreateVisualEvent;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/**
 * The view part of MVC.
 */
public class GameView {
    private final SpriteBatch batch;
    private final GameRenderableFactory renderableFactory;

    private final List<GameRenderable> renderables = new ArrayList<>();

    public final Consumer<CreateVisualEvent> createVisualListener = this::onCreateVisualEvent;

    public GameView(GameModel model) {
        batch = new SpriteBatch();
        renderableFactory = new GameRenderableFactory();
    }

    /**
     * Renders everything in the GameView.
     */
    public void render(float deltaTime) {
        batch.begin();
        for (var renderable : renderables) {
            renderable.draw(batch);
        }
        batch.end();
    }

    /**
     * Handles CreateVisualEvent events.
     */
    private void onCreateVisualEvent(CreateVisualEvent event) {
        renderables.add(renderableFactory.fromVisualComponent(
                event.visualComponent(),
                event.entity()));
    }

    /**
     * Free up the resources taken up by this class.
     */
    public void dispose() {
        renderableFactory.dispose();
        batch.dispose();
    }
}
