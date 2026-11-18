package com.roo.rogueyard.shared.engine.positionable;

import com.badlogic.gdx.math.Vector2;

public interface Positionable {
    Vector2 getPosition();
    void setPosition(Vector2 position);
    void updatePosition(Vector2 delta);
}
