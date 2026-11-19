package com.roo.rogueyard.render;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.physics.TransformComponent;

/**
 * Holds a sprite thats represents an entity. It keeps the sprite up to date
 * according to the entity position, scale and so on.
 */
class GameRenderableEntity implements GameRenderable {
    private Sprite sprite;
    private Entity entity;

    public GameRenderableEntity(Sprite sprite, Entity entity) {
        this.sprite = sprite;
        this.entity = entity;
    }

    @Override
    public void draw(SpriteBatch batch) {
        update();

        sprite.draw(batch);
    }

    /**
     * Update the sprite to conform to the entity.
     */
    protected void update() {
        var transform = entity.getComponent(TransformComponent.class);
        sprite.setPosition(transform.position.x, transform.position.y);
    }
}
