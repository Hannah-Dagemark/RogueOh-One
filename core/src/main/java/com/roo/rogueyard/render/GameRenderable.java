package com.roo.rogueyard.render;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * A renderable object.
 */
public interface GameRenderable {
    /**
     * Draw this object in the given sprite batch.
     */
    void draw(SpriteBatch batch);
}
