package com.roo.rogueyard.shared.game;

import com.badlogic.gdx.graphics.Sprite;
import com.roo.rogueyard.shared.engine.ecs.Component;

public class SpriteComponent implements Component {
    public Sprite sprite;

    public SpriteComponent(Sprite sprite) {
        this.sprite = sprite;
    }
}
