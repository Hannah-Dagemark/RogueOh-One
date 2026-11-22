package com.roo.rogueyard.shared.game.player;
import com.badlogic.gdx.math.Vector2;
import com.roo.rogueyard.shared.engine.ecs.Entity;
import com.roo.rogueyard.shared.game.DirectionType;
import com.roo.rogueyard.shared.game.physics.TransformComponent;
import com.roo.rogueyard.shared.game.physics.VelocityComponent;
import com.roo.rogueyard.shared.game.physics.VelocityFactorComponent;

/**
 * Core Player variant of the Entity class. This class holds the functionality unique to player characters, currently being controlled by users.
 * It is a subclass of the Entity class, making it able to interface with the ECS system. This class should however only hold non-ECS functionality,
 * as ECS functions should be left to systems and components, which are attached in runtime using a factory / wrapper.
 */
public class PlayerEntity extends Entity {
    private final Vector2 leftVector = new Vector2(-1f, 0);
    private final Vector2 rightVector = new Vector2(1f, 0);
    private final Vector2 upVector = new Vector2(0, 1f);
    private final Vector2 downVector = new Vector2(0, -1f);
    public float movementSpeedMultiplier;

    public PlayerEntity(int id) {
        super(id);
    }

    public void sprint() {
        this.getComponent(VelocityFactorComponent.class).additiveVelocityFactor += 3;
    }

    public void stopSprint() {
        this.getComponent(VelocityFactorComponent.class).additiveVelocityFactor -= 3;
    }

    public boolean movePlayer (DirectionType direction) {
        VelocityComponent velocityComponent = this.getComponent(VelocityComponent.class);
        return switch (direction) {
            case DirectionType.LEFT -> {
                if (canMovePlayerBy(leftVector)) {
                    velocityComponent.velocity.add(leftVector);
                    yield true;
                }
                yield false;
            }
            case DirectionType.RIGHT -> {
                if (canMovePlayerBy(rightVector)) {
                    velocityComponent.velocity.add(rightVector);
                    yield true;
                }
                yield false;
            }
            case DirectionType.UP -> {
                if (canMovePlayerBy(upVector)) {
                    velocityComponent.velocity.add(upVector);
                    yield true;
                }
                yield false;
            }
            case DirectionType.DOWN -> {
                if (canMovePlayerBy(downVector)) {
                    velocityComponent.velocity.add(downVector);
                    yield true;
                }
                yield false;
            }
        };
    }

    public boolean stopMovingPlayer (DirectionType direction) {
        VelocityComponent velocityComponent = this.getComponent(VelocityComponent.class);
        return switch (direction) {
            case DirectionType.LEFT -> {
                if (velocityComponent.velocity.x < 0){
                    velocityComponent.velocity.x = 0;
                    yield true;
                }
                yield false;
            }
            case DirectionType.RIGHT -> {
                if (velocityComponent.velocity.x > 0){
                    velocityComponent.velocity.x = 0;
                    yield true;
                }
                yield false;
            }
            case DirectionType.UP -> {
                if (velocityComponent.velocity.y > 0){
                    velocityComponent.velocity.y = 0;
                    yield true;
                }
                yield false;
            }
            case DirectionType.DOWN -> {
                if (velocityComponent.velocity.y < 0){
                    velocityComponent.velocity.y = 0;
                    yield true;
                }
                yield false;
            }
        };
    }

    private boolean canMovePlayerBy(Vector2 movement){
        float maxX = 1900;
        float maxY = 1000;
        /* Currently max position values are arbitrary, TODO: make player-map hitbox detection and use that instead */

        Vector2 newPosition = new Vector2(this.getComponent(TransformComponent.class).position).add(movement);
        return (newPosition.x >= 0 && newPosition.x <= maxX) && (newPosition.y >= 0 && newPosition.y <= maxY); /* Position after movement is not outside of screen check*/
    }
}
