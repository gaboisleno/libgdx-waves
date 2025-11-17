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
    Array<Enemy> enemies = new Array<>();
    Array<Enemy> heroes = new Array<>();

    private Array<String> actions = new Array<>();
    {
        actions.add("Fight");
        actions.add("Spell");
        actions.add("Items");
        actions.add("Run");
    }

    Table mainTable;
    OptionsTable<Enemy> enemiesTable;
    OptionsTable<Enemy> heroesTable;
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

        prepareEnemyListTable();
        prepareActionListTable();
        prepareHeroListTable();

        buildLayout();

        focusTable(heroesTable);
    }

    private void buildLayout() {
        mainTable.add(enemiesTable)
                .expandX()
                .fillX()
                .minHeight(Core.VIEW_HEIGHT / 2f)
                .colspan(2);
        mainTable.row();
        mainTable.add(actionsTable).bottom();
        mainTable.add(heroesTable).expandX().fillX().bottom();
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

    private void prepareActionListTable() {
        actionsTable = new OptionsTable<>(actions, game.skin, (table, item) -> {
            table.add(new Label(item, game.skin)).left().padRight(10);
        });

        // attack option
        actionsTable.setCallback(0, () -> {
            state = CombatState.PLAYER_CHOOSE_TARGET;
            focusTable(enemiesTable);
            return;
        });

        // run option
        actionsTable.setCallback(3, () -> {
            state = CombatState.RUN;
            game.hideCombatScreen();
            return;
        });
    }

    private void prepareHeroListTable() {
        heroes.add(new Enemy("Warrior", 30));
        heroes.add(new Enemy("Mage", 20));

        heroesTable = new OptionsTable<Enemy>(heroes, game.skin, (table, hero) -> {
            Label name = new Label(hero.name, game.skin);
            Label hp = new Label(hero.hp + " / " + hero.maxHp, game.skin);
            table.add(name).left().padRight(10);
            table.add(hp);
        });
        for (int i = 0; i < heroes.size; i++) {
            heroesTable.setCallback(i, () -> {
                focusTable(actionsTable);
            });
        }
    }

    private void prepareEnemyListTable() {
        enemies.add(new Enemy("Goblin", 30));
        enemies.add(new Enemy("Slime", 15));
        enemies.add(new Enemy("Skeleton", 10));

        enemiesTable = new OptionsTable<Enemy>(enemies, game.skin, (table, enemy) -> {
            Label name = new Label(enemy.name, game.skin);
            table.add(name).left().padRight(10);
        });

        for (int i = 0; i < enemies.size; i++) {
            int index = i;
            enemiesTable.setCallback(i, () -> {
                Enemy enemy = enemies.get(index);
                enemy.applyDamage();
                enemiesTable.refresh();
                focusTable(heroesTable);
            });
        }
    }

    public void focusTable(OptionsTable<?> table) {
        if (currentTable != null) {
            currentTable.setFocus(false);
        }
        table.setFocus(true);
        currentTable = table;
    }
}
