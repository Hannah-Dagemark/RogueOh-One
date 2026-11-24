package com.roo.rogueyard.shared.game.physics;

import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.Shape;
import com.badlogic.gdx.physics.box2d.BodyDef.BodyType;

public final class BodyBlueprintFactory {
    private BodyBlueprintFactory() {
    }

    private static FixtureDef defaultFixture(Shape shape) {
        FixtureDef fixtureDef = new FixtureDef();
        fixtureDef.shape = shape;
        fixtureDef.density = 1.0f;
        fixtureDef.friction = 0.1f;
        fixtureDef.restitution = 0f;
        return fixtureDef;
    }

    /**
     * Creates a circle body blueprint with the given radius. If isStatic
     * chooses StaticBody, otherwise DynamicBody. If isBullet makes the body
     * check further to not pass through other bodies.
     */
    public static BodyBlueprint createCircle(float radius, boolean isStatic, boolean isBullet) {
        var shape = new CircleShape();
        shape.setRadius(radius);

        var bodyDef = new BodyDef();
        bodyDef.bullet = isBullet;

        var fixtureDef = defaultFixture(shape);

        var bodyType = isStatic ? BodyType.StaticBody : BodyType.DynamicBody;

        return new BodyBlueprint(bodyDef, fixtureDef, bodyType, isBullet);
    }
}
