package com.roo.rogueyard.shared.game.player;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.roo.rogueyard.shared.game.DirectionType;

/* TODO: For MVC adherence, and for multiplayer compatibility in shared, I think this needs to move to core at some point? */

/**
 * Processor for the Player class. Used for movement and combat input. Should be lowest in input multiplexor, as user input to player character should take the least precedent.
 */
public class PlayerInputProcessor extends InputAdapter {
    private final PlayerEntity player;
    public PlayerInputProcessor(PlayerEntity player) {
        this.player = player;
    }

    @Override
    public boolean keyDown(int keycode){
        return switch (keycode) {
            case Input.Keys.LEFT -> {
                player.movePlayer(DirectionType.LEFT);
                yield true;
            }
            case Input.Keys.RIGHT -> {
                player.movePlayer(DirectionType.RIGHT);
                yield true;
            }
            case Input.Keys.UP -> {
                player.movePlayer(DirectionType.UP);
                yield true;
            }
            case Input.Keys.DOWN -> {
                player.movePlayer(DirectionType.DOWN);
                yield true;
            }
            case Input.Keys.SHIFT_LEFT -> {
                player.sprint();
                yield true;
            }
            default -> false;
        };
    }
    @Override
    public boolean keyUp(int keycode){
        return switch (keycode) {
            case Input.Keys.LEFT -> {
                player.stopMovingPlayer(DirectionType.LEFT);
                yield true;
            }
            case Input.Keys.RIGHT -> {
                player.stopMovingPlayer(DirectionType.RIGHT);
                yield true;
            }
            case Input.Keys.UP -> {
                player.stopMovingPlayer(DirectionType.UP);
                yield true;
            }
            case Input.Keys.DOWN -> {
                player.stopMovingPlayer(DirectionType.DOWN);
                yield true;
            }
            case Input.Keys.SHIFT_LEFT -> {
                player.stopSprint();
                yield true;
            }
            default -> false;
        };
    }
}
