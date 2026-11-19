package com.roo.rogueyard.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;

/**
 * The base screen all other screens should inherit from. Empty methods give
 * a default blank implmentation so all other screens don't need to provide
 * that themselves.
 */
public abstract class BaseScreen implements Screen {
    Game game;

    public BaseScreen(Game game) {
        this.game = game;
    }

    // TODO: Remove these and implement them in the child classes. What
    // happens when the game is paused? And so on.
    @Override
    public void resize(int width, int height) {
    }

    @Override
    public void show() {
    }

    @Override
    public void hide() {
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void dispose() {
    }
}
