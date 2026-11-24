package com.roo.rogueyard.shared.game.physics;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

/**
 * Holds all the needed data to create a Box2D body.
 */
public record BodyBlueprint(
        BodyDef bodyDef,
        FixtureDef fixtureDef,
        BodyType bodyType,
        boolean isBullet) {
}
