package com.roo.rogueyard.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.roo.rogueyard.Assets;

/**
 * The main menu screen.
 */
public class MainMenu extends BaseScreen {
    TextureRegion title;
    SpriteBatch batch;

    BitmapFont testFont;

    public MainMenu(Game game) {
        super(game);
    }

    @Override
    public void show() {
        title = new Sprite(Assets.getTexture("logo.png"));

        testFont = Assets.getFont("inter.ttf", Assets.FontSize.BIG);

        batch = new SpriteBatch();
        batch.getProjectionMatrix().setToOrtho2D(0, 0, 1600, 1000);
    }

    @Override
    public void render(float deltaTime) {
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        batch.draw(title, 100, 0, 1400, 1000);

        testFont.setColor(0.3f, 0.2f, 1, 1);
        testFont.getData().setScale(1,1);
        testFont.draw(batch, "WELCOME TO ROGUEYARD!!!", 400, 400);

        batch.end();
        if (Gdx.input.isKeyPressed(Keys.SPACE)) {
            game.setScreen(new MainScreen(game));
        }
    }

    @Override
    public void hide() {
        batch.dispose();
    }
}
