package com.roo.rogueyard;

import com.badlogic.gdx.Game;
import com.roo.rogueyard.screens.MainMenu;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
public class Main extends Game {
    @Override
    public void create() {
        Assets.init(); // NOTE: should this be here?
        setScreen(new MainMenu(this));
    }
}
