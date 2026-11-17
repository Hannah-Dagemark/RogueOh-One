package com.roo.rogueyard;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.ScreenUtils;

public class GameView {
    private SpriteBatch batch;

    public GameView() {
        batch = new SpriteBatch();
    }

    public void render(float deltaTime) {
        ScreenUtils.clear(0.15f, 0.15f, 0.2f, 1f);
        batch.begin();
        // batch.draw(image, 140, 210);
        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }
}
