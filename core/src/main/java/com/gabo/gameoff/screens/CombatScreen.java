/*
 * @gaboisleno
*/
package com.gabo.gameoff.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gabo.gameoff.Core;
import com.gabo.gameoff.entities.Enemy;
import com.gabo.gameoff.utils.ui.OptionsTable;

public class CombatScreen implements Screen {

    GameScreen game;
    Stage stage;

    private Array<String> actions = new Array<>();
    {
        actions.add("Fight");
        actions.add("Spell");
        actions.add("Items");
        actions.add("Run");
    }

    Table mainTable;
    OptionsTable<Enemy> enemyTable;
    OptionsTable<String> actionsTable;

    OptionsTable<?> currentTable;

    public enum CombatState {
        PLAYER_CHOOSE_ACTION,
        PLAYER_CHOOSE_TARGET,
        ENEMY_TURN,
        RUN,
        END
    }

    private CombatState state = CombatState.PLAYER_CHOOSE_ACTION;

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

        switch (state) {

            case PLAYER_CHOOSE_ACTION:
                // wait selection
                break;

            case PLAYER_CHOOSE_TARGET:
                // select enemy
                break;

            case ENEMY_TURN:
                // enemy attacks
                break;

            case RUN:
                // run
                break;

            case END:
                // end combat
                break;
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

    private void addActionListTable() {
        mainTable.bottom().pad(10);

        actionsTable = new OptionsTable<>(actions, game.skin, (table, item) -> {
            table.add(new Label(item, game.skin)).left().padRight(10);
        });

        // attack option
        actionsTable.setCallback(0, () -> {
            state = CombatState.PLAYER_CHOOSE_TARGET;
            focusTable(enemyTable);
            return;
        });

        // run option
        actionsTable.setCallback(3, () -> {
            state = CombatState.RUN;
            game.hideCombatScreen();
            return;
        });
        mainTable.add(actionsTable);
        focusTable(actionsTable);
    }

    private void addEnemyListTable() {
        Array<Enemy> enemies = new Array<>();
        enemies.add(new Enemy("Goblin", 30));
        enemies.add(new Enemy("Slime", 15));
        enemies.add(new Enemy("Skeleton", 10));

        enemyTable = new OptionsTable<Enemy>(enemies, game.skin, (table, enemy) -> {
            table.add(new Label(enemy.name, game.skin)).left().padRight(10);
            table.add(new Label(enemy.hp + " / " + enemy.maxHp, game.skin));
        });

        for (int i = 0; i < enemies.size; i++) {
            enemyTable.setCallback(i, () -> {
                // apply enemy damage
                focusTable(actionsTable);
            });
        }

        mainTable.add(enemyTable).expandX().fillX().bottom();
    }

    public void focusTable(OptionsTable<?> table) {
        if (currentTable != null) {
            currentTable.setFocus(false);
        }
        table.setFocus(true);
        currentTable = table;
    }
}
