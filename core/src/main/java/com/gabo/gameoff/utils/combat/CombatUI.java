package com.gabo.gameoff.utils.combat;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.gabo.gameoff.Core;
import com.gabo.gameoff.assets.Assets;
import com.gabo.gameoff.assets.Images;
import com.gabo.gameoff.entities.BaseUnit;
import com.gabo.gameoff.screens.CombatScreen;
import com.gabo.gameoff.utils.ui.HeroesTable;
import com.gabo.gameoff.utils.ui.OptionsTable;

public class CombatUI {
    public interface UIListener {
        void onActionSelected(String action);

        void onEnemySelected(BaseUnit enemy);

        void onHeroSelected(BaseUnit hero);

        void onRunSelected();

        void allHeroesFinished();
    }

    Stage stage;
    Skin skin;
    Assets assets;
    CombatScreen screen;
    Table mainTable;
    OptionsTable<BaseUnit> enemiesTable;
    OptionsTable<BaseUnit> heroesTable;
    OptionsTable<String> actionsTable;
    OptionsTable<?> currentTable;

    private Array<String> actions = new Array<>();
    {
        actions.add("Fight");
        actions.add("Spell");
        actions.add("Items");
        actions.add("Run");
    }

    public CombatUI(CombatScreen screen, Stage stage, Assets assets) {
        this.stage = stage;
        this.screen = screen;
        this.assets = assets;
        this.skin = assets.getSkin();

        mainTable = new Table();
        mainTable.setFillParent(true);
        mainTable.pad(10);
        stage.addActor(mainTable);

        prepareEnemyListTable();
        prepareActionListTable();
        prepareHeroListTable();

        buildLayout();
        focusTable(actionsTable);
    }

    public void buildLayout() {
        mainTable.add(enemiesTable)
                .expandX()
                .fillX()
                .minHeight(Core.VIEW_HEIGHT / 2f)
                .colspan(2);
        mainTable.row();
        mainTable.add(actionsTable).bottom();
        mainTable.add(heroesTable).expandX().fillX().bottom();
    }

    private void prepareActionListTable() {
        actionsTable = new OptionsTable<>(actions, skin, (table, option, row) -> {
            Label action = new Label(option, skin);
            table.add(action).left().padRight(10);
            row.add(action);
        });

        // attack option
        actionsTable.setCallback(0, () -> {
            screen.onActionSelected("Fight");
            return;
        });

        // run option
        actionsTable.setCallback(3, () -> {
            screen.onRunSelected();
            return;
        });
    }

    private void prepareHeroListTable() {
        Array<BaseUnit> heroes = screen.combatManager.heroes;

        heroesTable = new HeroesTable(heroes, skin);
        heroesTable.setFocus(false);

        for (int i = 0; i < heroes.size; i++) {
            int index = i;
            heroesTable.setCallback(i, () -> {
                screen.onHeroSelected(heroes.get(index));
            });
        }
    }

    private void prepareEnemyListTable() {
        Array<BaseUnit> enemies = screen.combatManager.enemies;

        enemiesTable = new OptionsTable<BaseUnit>(enemies, skin, (table, enemy, row) -> {
            Image sprite = new Image(assets.getImage(Images.creature_058));
            table.add(sprite);
        });

        for (int i = 0; i < enemies.size; i++) {
            int index = i;
            enemiesTable.setCallback(i, () -> {
                screen.onEnemySelected(enemies.get(index));
            });
        }
    }

    public void focusEnemy() {
        this.focusTable(enemiesTable);
    }

    public void focusActions() {
        this.focusTable(actionsTable);
    }

    public void focusTable(OptionsTable<?> table) {
        if (currentTable != null) {
            currentTable.setFocus(false);
        }
        currentTable = table;

        if (currentTable != null) {
            table.setFocus(true);
        }
    }

    public void heroesNextIndex() {
        heroesTable.nextIndex();
    }

    public BaseUnit getFocusedHero() {
        return heroesTable.getFocused();
    }
}
