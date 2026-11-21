package com.roo.rogueyard.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.roo.rogueyard.GameView;
import com.roo.rogueyard.shared.game.GameModel;
import com.roo.rogueyard.shared.game.event.CreateVisualEvent;
import com.roo.rogueyard.shared.game.event.DestroyVisualEvent;

/**
 * The main screen where the game is played. Acts as the controller part of
 * MVC.
 */
public class MainScreen extends BaseScreen {
    GameModel gameModel;
    GameView gameView;

    public MainScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        gameModel = new GameModel();
        gameView = new GameView(gameModel);

        gameModel.getExternalEventBus().subscribe(
                CreateVisualEvent.class,
                gameView.createVisualListener);
        gameModel.getExternalEventBus().subscribe(
                DestroyVisualEvent.class,
                gameView.destroyVisualListener);
    }

    @Override
    public void render(float deltaTime) {
        deltaTime = Math.min(0.06f, Gdx.graphics.getDeltaTime());
        gameModel.update(deltaTime);
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        gameView.render(deltaTime);
    }

    @Override
    public void hide() {
        gameView.dispose();
    }
}
