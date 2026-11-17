package com.roo.rogueyard.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.roo.rogueyard.GameView;
import com.roo.rogueyard.shared.game.GameModel;

public class MainScreen extends BaseScreen {
    GameModel gameModel;
    GameView gameView;

    public MainScreen(Game game) {
        super(game);
    }

    @Override
    public void show() {
        gameModel = new GameModel();
        gameView = new GameView();
    }

    @Override
    public void render(float deltaTime) {
        deltaTime = Math.min(0.06f, Gdx.graphics.getDeltaTime());
        gameModel.update(deltaTime);
        Gdx.gl.glClearColor(0.1f, 0.1f, 0.1f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        gameView.render(deltaTime);
    }

    @Override
    public void hide() {
        Gdx.app.debug("Cubocy", "dispose game screen");
        gameView.dispose();
    }
}
