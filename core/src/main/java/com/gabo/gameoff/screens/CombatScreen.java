package com.gabo.gameoff.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class CombatScreen implements Screen {

    GameScreen game;
    Stage stage;

    private int selectedIndex = 0;
    private Label cursorLabel;
    private String[] actions = { "Fight", "Spell", "Items", "Run" };
    private Array<Label> options;

    // Example
    private Array<Actor> heroes; // allies {name, hp, mp, spells[], attacks[]}
    private Array<Actor> enemies;

    public CombatScreen(GameScreen previousScreen) {

        this.game = previousScreen;
        stage = new Stage();
        stage.setDebugAll(false);

        createMenu();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            if (selectedIndex == 3) {
                game.hideCombatScreen();
            }
        }

        if (Gdx.input.isKeyJustPressed(Keys.W)) {
            selectedIndex = (selectedIndex - 1 + options.size) % options.size;
        }

        if (Gdx.input.isKeyJustPressed(Keys.S)) {
            selectedIndex = (selectedIndex + 1) % options.size;
        }

        for (int i = 0; i < options.size; i++) {
            Label label = options.get(i);
            if (i == selectedIndex) {
                cursorLabel.setPosition(
                        label.getX() - cursorLabel.getWidth() - 8,
                        label.getY());
            }
        }

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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
        stage.dispose();
    }

    private void createMenu() {
        options = new Array<Label>();

        cursorLabel = new Label(">", game.skin);
        cursorLabel.addAction(Actions.forever(
                Actions.sequence(
                        Actions.visible(false),
                        Actions.delay(0.25f),
                        Actions.visible(true),
                        Actions.delay(0.25f))));
        stage.addActor(cursorLabel);

        Table mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.bottom().left().padLeft(40).padBottom(20);

        for (String action : actions) {
            Label label = new Label(action, game.skin);
            label.setAlignment(Align.left);
            mainTable.add(label).left().pad(3).row();
            options.add(label);
        }

        stage.addActor(mainTable);
    }
}
