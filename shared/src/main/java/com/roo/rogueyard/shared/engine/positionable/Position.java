package com.roo.rogueyard.shared.engine.positionable;

import com.badlogic.gdx.math.Vector2;

public class Position {
    public Vector2 coordinates;
    public Position(Vector2 startingPosition) {
            this.coordinates = startingPosition;
    }
    public Vector2 getPosition() {
        return this.coordinates;
    }
    public void setPosition(Vector2 newPosition) {
        this.coordinates = newPosition;
    }
    public void updatePosition(Vector2 vector) {
         this.coordinates = this.coordinates.add(vector);
    }
}
