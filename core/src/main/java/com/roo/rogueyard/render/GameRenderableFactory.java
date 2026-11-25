package com.roo.rogueyard.render;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.VisualComponent;
import com.roo.rogueyard.shared.game.VisualType;

import java.util.HashMap;
import java.util.Map;

/**
 * Creates GameRenderable objects.
 */
public final class GameRenderableFactory {
    private final Map<String, Texture> textures = new HashMap<>();

    public GameRenderableFactory() {
    }

    private Texture loadTexture(String key) {
        return new Texture(Gdx.files.internal("sprites/" + key + ".png"));
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
            case VisualType.DESCENDPOINT:
                return createDescendPoint(visual, entity);
            default:
                throw new RuntimeException("Unimplemented visual type.");
        }
    }

    private GameRenderable createPlayer(VisualComponent visual, Entity entity) {
        var sprite = new Sprite(loadTexture("robo-guy"));
        sprite.setAlpha(visual.visible ? 1 : 0);
        return new GameRenderableEntity(sprite, entity);
    }

    private GameRenderable createEnemy(VisualComponent visual, Entity entity) {
        var sprite = new Sprite(loadTexture("bulker"));
        return new GameRenderableEntity(sprite, entity);
    }

    private GameRenderable createDescendPoint(VisualComponent visual, Entity entity) {
        var sprite = new Sprite(loadTexture("descend-point"));
        return new GameRenderableEntity(sprite, entity);
    }

    /**
     * Free up the resources taken up by this class.
     */
    public void dispose() {
        textures.values().forEach(Texture::dispose);
        textures.clear();
    }
}
