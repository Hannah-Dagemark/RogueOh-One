package com.roo.rogueyard;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class GameView {
    private SpriteBatch batch;

    public GameView() {
        batch = new SpriteBatch();
    }

    public void render(float deltaTime) {
        batch.begin();
        // batch.draw(image, 140, 210);
        batch.end();
    }

    public void dispose() {
        batch.dispose();
    }
}
