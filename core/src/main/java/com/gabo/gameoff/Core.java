package com.gabo.gameoff;

import com.badlogic.gdx.Game;
import com.gabo.gameoff.screens.LoadingScreen;

/**
 * {@link com.badlogic.gdx.ApplicationListener} implementation shared by all
 * platforms.
 */
/* 31/10/2025 gabo isleno */
public class Core extends Game {
    @Override
    public void create() {
        setScreen(new LoadingScreen(this));
    }
}