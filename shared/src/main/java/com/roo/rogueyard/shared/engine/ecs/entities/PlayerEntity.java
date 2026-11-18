package com.roo.rogueyard.shared.engine.ecs.entities;

import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.positionable.Position;
import com.roo.rogueyard.shared.engine.positionable.Positionable;

public class PlayerEntity extends Entity implements Positionable {
    Position position;
    public PlayerEntity(int id) {
        super(id);
        this.position = new Position(new Vector2(0,0));
    }

    @Override
    public Vector2 getPosition() {
        return this.position.getPosition();
    }

    @Override
    public void setPosition(Vector2 position) {
        this.position.setPosition(position);
    }

    @Override
    public void updatePosition(Vector2 delta) {
        this.position.updatePosition(delta);
    }
}
