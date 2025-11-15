/*
 * @gaboisleno
*/
package com.gabo.gameoff.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gabo.gameoff.Core;
import com.gabo.gameoff.utils.OptionsTable;

public class CombatScreen implements Screen {

    GameScreen game;
    Stage stage;

    // Example
    private String[] actions = { "Fight", "Spell", "Items", "Run" };
    private String[] enemies = { "Goblin", "Slime", "Zombie" };

    Table mainTable;
    OptionsTable enemyTable;
    OptionsTable actionsTable;

    public CombatScreen(GameScreen previousScreen) {
        this.game = previousScreen;
        stage = new Stage(new FitViewport(Core.VIEW_WIDTH, Core.VIEW_HEIGHT));
        stage.setDebugAll(false);

        mainTable = new Table();
        mainTable.setFillParent(true);
        stage.addActor(mainTable);

        addActionListTable();
        addEnemyListTable();
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

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

    private void addActionListTable() {
        mainTable.bottom().left();
        actionsTable = new OptionsTable(actions, game.skin);

        // // TODO make this dynamic

        // attack option
        actionsTable.setCallback(0, () -> {
            actionsTable.setFocus(false);
            enemyTable.setFocus(true);
            return;
        });

        // run option
        actionsTable.setCallback(3, () -> {
            game.hideCombatScreen();
            return;
        });

        actionsTable.setFocus(true);
        mainTable.add(actionsTable);
    }

    private void addEnemyListTable() {
        enemyTable = new OptionsTable(enemies, game.skin);
        // TODO make this dynamic
        enemyTable.setCallback(0, () -> {
            actionsTable.setFocus(true);
            enemyTable.setFocus(false);
        });

        mainTable.add(enemyTable).expandX().fillX();
    }
}
