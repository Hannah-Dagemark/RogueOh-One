package com.roo.rogueyard;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.roo.rogueyard.screens.MainMenu;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {
    @Override
    public void create() {
        setScreen(new MainMenu(this));
    }
}
