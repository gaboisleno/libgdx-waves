package com.gabo.gameoff.screens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gabo.gameoff.assets.Assets;
import com.gabo.gameoff.stages.HouseStage;
import com.gabo.gameoff.utils.DialogueBox;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen implements Screen {
    Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));

    public HouseStage stage;
    public Assets assets;
    public DialogueBox dialogueBox;

    public GameScreen(Assets assets) {

        this.assets = assets;
        stage = new HouseStage(this);

        dialogueBox = new DialogueBox(skin);
        stage.addActor(dialogueBox);

        stage.addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ENTER) {
                    if (dialogueBox.isFinished()) {
                        dialogueBox.nextLine();
                    } else {
                        dialogueBox.skip();
                    }
                }
                return true;
            }
        });

        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);
        stage.act(delta);
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        if (width <= 0 || height <= 0)
            return;
        stage.getViewport().update(width, height);
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

    public void setDialogues() {
        List<String> lines = new ArrayList<>(
                Arrays.asList(
                        "They have been coming.",
                        "They have been coming, in waves.",
                        "To take them all.",
                        "Oh no please.",
                        "I don't want go to..."));

        dialogueBox.setLines(lines);
    }
}