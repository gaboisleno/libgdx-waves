package com.gabo.gameoff.screens;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.ScreenUtils;
import com.gabo.gameoff.assets.Assets;
import com.gabo.gameoff.utils.DialogueBox;

/**
 * First screen of the application. Displayed after the application is created.
 */
public class GameScreen implements Screen {

    Stage stage = new Stage();
    Skin skin = new Skin(Gdx.files.internal("skin/uiskin.json"));
    Assets assets;

    DialogueBox dialogueBox;

    public GameScreen(Assets assets) {
        this.assets = assets;
        /*
         * Image background = new Image(assets.getImage(Images.space));
         * background.setFillParent(true);
         * background.setOrigin(Align.center);
         * background.getColor().a = 0f;
         * background.addAction(
         * Actions.parallel(
         * Actions.fadeIn(5f),
         * Actions.scaleTo(1.5f, 1.5f, 20f, Interpolation.smooth)));
         * stage.addActor(background);
         */
        dialogueBox = new DialogueBox(skin);
        List<String> lines = new ArrayList<>(
                Arrays.asList(
                        "They have been coming.",
                        "They have been coming, in waves.",
                        "To take them all.",
                        "Oh no please.",
                        "I don't want go to..."));

        dialogueBox.setLines(lines);
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
}