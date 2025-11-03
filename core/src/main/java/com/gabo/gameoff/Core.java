package com.gabo.gameoff;

import com.badlogic.gdx.Game;
import com.gabo.gameoff.screens.LoadingScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
/* 31/10/2025 gabo isleno */
public class Core extends Game {
    public static final int CELL_WIDTH = 32;
    public static final int CELL_HEIGHT = 32;

    public static final int VIEW_WIDTH = 320;
    public static final int VIEW_HEIGHT = 180;

    public static final boolean DEBUG = true;

    @Override
    public void create() {
        setScreen(new LoadingScreen(this));
    }
}