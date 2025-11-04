package com.gabo.gameoff.screens;

import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gabo.gameoff.assets.Assets;
import com.gabo.gameoff.stages.GameStage;
import com.gabo.gameoff.stages.GuiStage;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen implements Screen {
    public Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

    public GameStage stage;
    public GuiStage guiStage;
    public Assets assets;

    public GameScreen(Assets assets) {
        this.assets = assets;

        stage = new GameStage(this);
        guiStage = new GuiStage(this);

        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(stage);
        inputMultiplexer.addProcessor(guiStage);
        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        if (guiStage.isDialogueFinish()) {
            stage.act(delta);
        }

        stage.draw();

        guiStage.act(delta);
        guiStage.draw();
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0)
            return;
        stage.getViewport().update(width, height);
        guiStage.getViewport().update(width, height);
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
    }

    public void showDialogues(List<String> dialogues) {
        guiStage.setDialogues(dialogues);
    }
}