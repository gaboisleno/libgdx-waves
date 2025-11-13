package com.gabo.gameoff.screens;

import com.badlogic.gdx.Screen;
import com.gabo.gameoff.Core;
import com.gabo.gameoff.assets.Assets;

public class LoadingScreen implements Screen {
    Assets assets = new Assets();
    Core core;

    public LoadingScreen(Core core) {
        this.core = core;
        assets.loadImages();
        assets.loadAtlases();
        assets.loadSkin();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (assets.update()) {
            core.setScreen(new GameScreen(assets, core));
        }
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0)
            return;
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        assets.dispose();
    }

}
