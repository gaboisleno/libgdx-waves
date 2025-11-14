package com.gabo.gameoff.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gabo.gameoff.Core;

public class CombatScreen implements Screen {

    GameScreen game;
    Stage stage;

    private int selectedIndex = 0;
    private String[] actions = { "Fight", "Spell", "Items", "Run" };
    private Array<Label> options, cursors;

    // Example
    private Array<Actor> heroes; // allies {name, hp, mp, spells[], attacks[]}
    private Array<Actor> enemies;

    public CombatScreen(GameScreen previousScreen) {

        this.game = previousScreen;
        stage = new Stage(new FitViewport(Core.VIEW_WIDTH, Core.VIEW_HEIGHT));
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

        for (int i = 0; i < cursors.size; i++) {
            cursors.get(i).setVisible(i == selectedIndex);
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
        cursors = new Array<Label>();

        Table mainTable = new Table();
        mainTable.setFillParent(true);

        Table optionsTable = new Table();
        optionsTable.background(game.skin.getDrawable("dialogue"));

        mainTable.bottom().left().padLeft(20).padBottom(20);
        mainTable.add(optionsTable);

        for (String action : actions) {

            Label cursor = new Label(">", game.skin);

            cursor.addAction(Actions.forever(
                    Actions.sequence(
                            Actions.alpha(0f),
                            Actions.delay(0.25f),
                            Actions.alpha(1f),
                            Actions.delay(0.25f))));
            cursor.setVisible(false);

            Label label = new Label(action, game.skin);

            optionsTable.add(cursor).padLeft(5).padRight(5);
            optionsTable.add(label).left().padRight(10).row();

            cursors.add(cursor);
            options.add(label);
        }

        stage.addActor(mainTable);
    }
}
