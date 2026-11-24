package com.roo.rogueyard.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.roo.rogueyard.Assets;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.VisualComponent;
import com.roo.rogueyard.shared.game.VisualType;

import java.util.HashMap;
import java.util.Map;

/**
 * Creates GameRenderable objects.
 */
public final class GameRenderableFactory {

    public GameRenderableFactory() {
    }

    /**
     * Create a GameRenderable to represent a given VisualComponent belonging
     * to the given entity.
     */
    public GameRenderable fromVisualComponent(VisualComponent visual, Entity entity) {
        switch (visual.visualType) {
            case VisualType.PLAYER:
                return createPlayer(visual, entity);
            case VisualType.ENEMY:
                return createEnemy(visual, entity);
            default:
                throw new RuntimeException("Unimplmented visual type.");
        }
    }

    private GameRenderable createPlayer(VisualComponent visual, Entity entity) {
        var sprite = new Sprite(Assets.getTexture("robo-guy.png"));
        sprite.setAlpha(visual.visible ? 1 : 0);
        return new GameRenderableEntity(sprite, entity);
    }

    private GameRenderable createEnemy(VisualComponent visual, Entity entity) {
        var sprite = new Sprite(Assets.getTexture("bulker.png"));
        return new GameRenderableEntity(sprite, entity);
    }
}
