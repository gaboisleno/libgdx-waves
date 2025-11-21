package com.gabo.gameoff.utils.combat;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.gabo.gameoff.Core;
import com.gabo.gameoff.assets.Assets;
import com.gabo.gameoff.entities.BaseUnit;
import com.gabo.gameoff.entities.Enemy;
import com.gabo.gameoff.screens.CombatScreen;
import com.gabo.gameoff.utils.ui.ActionItemRenderer;
import com.gabo.gameoff.utils.ui.DialogueBox;
import com.gabo.gameoff.utils.ui.EnemyItemRenderer;
import com.gabo.gameoff.utils.ui.HeroItemRenderer;
import com.gabo.gameoff.utils.ui.OptionsTable;
import com.gabo.gameoff.utils.ui.OptionsTable.Option;

public class CombatUI {

    public interface UIListener {
        void onActionSelected(MenuActions action);

        void onEnemySelected(BaseUnit enemy);

        void onHeroSelected(BaseUnit hero);

        void allHeroesFinished();
    }

    Stage stage;
    Skin skin;
    Assets assets;
    CombatScreen screen;

    // UI Elements
    Table mainTable;
    OptionsTable<BaseUnit> enemiesTable;
    OptionsTable<BaseUnit> heroesTable;
    OptionsTable<MenuActions> actionsTable;
    OptionsTable<?> currentTable;

    private final Array<MenuActions> actions = new Array<>();
    {
        actions.add(MenuActions.FIGHT);
        actions.add(MenuActions.SPELL);
        actions.add(MenuActions.ITEM);
        actions.add(MenuActions.RUN);
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

    private void prepareActionListTable() {
        actionsTable = new OptionsTable<>(actions, skin, new ActionItemRenderer(assets));
        actionsTable.setCallback((item) -> {
            screen.onActionSelected(item.value);
        });
    }

    private void prepareHeroListTable() {
        Array<BaseUnit> heroes = screen.combatManager.heroes;

        heroesTable = new OptionsTable<BaseUnit>(heroes, skin, new HeroItemRenderer(assets));
        heroesTable.setFocus(false);

        for (int i = 0; i < heroes.size; i++) {
            heroesTable.setCallback((item) -> {
                screen.onHeroSelected(item.value);
            });
        }
    }

    private void prepareEnemyListTable() {
        Array<BaseUnit> enemies = screen.combatManager.enemies;

        enemiesTable = new OptionsTable<BaseUnit>(enemies, skin, new EnemyItemRenderer(assets));

        for (int i = 0; i < enemies.size; i++) {
            enemiesTable.setCallback((item) -> {
                screen.onEnemySelected(item.value);
            });
        }
    }

    public void focusEnemy() {
        this.focusTable(enemiesTable);
    }

    public void focusActions() {
        this.focusTable(actionsTable);
    }

    public final void focusTable(OptionsTable<?> table) {
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

    public void animate(Turn turn, Runnable onFinish) {
        enemiesTable.setFocus(false);

        DialogueBox dialogue = new DialogueBox(skin);
        stage.addActor(dialogue);
        List<String> info = new ArrayList<>();
        info.add(turn.getAttacker().name + " attacks to " + turn.getAttacked().name);

        if (turn.getAttacked() instanceof Enemy) {
            Option option = enemiesTable.findByOption(turn.getAttacked());

            if (option != null) {
                option.image.addAction(
                        Actions.repeat(5,
                                Actions.sequence(
                                        Actions.alpha(0),
                                        Actions.delay(.1f),
                                        Actions.alpha(1),
                                        Actions.delay(.1f))));
            }
        }

        dialogue.setLines(info, () -> {
            dialogue.remove();
            onFinish.run();
        });

        dialogue.addLineEndListener(() -> {
            dialogue.addAction(Actions.sequence(
                    Actions.delay(1.25f),
                    Actions.run(dialogue::nextLine)));
        });
    }

    public void resetHeroSelection() {
        heroesTable.resetSelection();
        heroesTable.clearSelectionHighlight();
    }

    public void refreshHeroesInfo() {
        heroesTable.refresh(screen.combatManager.heroes);
    }
}
