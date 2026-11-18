package com.roo.rogueyard.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.utils.ScreenUtils;
import com.roo.rogueyard.GameView;
import com.roo.rogueyard.shared.game.GameModel;
import com.roo.rogueyard.shared.game.event.CreateVisualEvent;

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

        gameModel.getEventBus().subscribe(
                CreateVisualEvent.class,
                gameView.createVisualListener);
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
        Gdx.app.debug("Cubocy", "dispose game screen");
        gameView.dispose();
    }
}
