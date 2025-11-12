package com.gabo.gameoff.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;

public class CombatScreen implements Screen {

    GameScreen game;
    Stage stage;

    private int selectedIndex = 0;
    private String[] actions = { "FIGHT", "ITEMS", "RUN" };
    private Array<Label> options;

    public CombatScreen(GameScreen previousScreen) {
        this.game = previousScreen;
        stage = new Stage();

        createMenu();
    }

    private void createMenu() {
        options = new Array<Label>();

        Table table = new Table();
        table.setFillParent(true);
        table.bottom().padBottom(10);

        for (String action : actions) {
            Label label = new Label(action, game.skin);
            label.setAlignment(Align.left);
            table.add(label).left().pad(3).row();
            options.add(label);
        }

        stage.addActor(table);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        if (Gdx.input.isKeyJustPressed(Keys.SPACE)) {
            game.hideCombatScreen();
        }

        if (Gdx.input.isKeyJustPressed(Keys.S)) {
            selectedIndex = (selectedIndex + 1) % options.size;
        }
        if (Gdx.input.isKeyJustPressed(Keys.W)) {
            selectedIndex = (selectedIndex - 1 + options.size) % options.size;
        }

        for (int i = 0; i < options.size; i++) {
            Label label = options.get(i);
            if (i == selectedIndex) {
                label.setColor(Color.YELLOW);
            } else {
                label.setColor(Color.WHITE);
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
    }

}
