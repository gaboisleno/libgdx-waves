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
import com.gabo.gameoff.utils.combat.animations.AnimationStrategy;
import com.gabo.gameoff.utils.combat.animations.PhysicalAttackAnimation;
import com.gabo.gameoff.utils.ui.ActionItemRenderer;
import com.gabo.gameoff.utils.ui.DialogueBox;
import com.gabo.gameoff.utils.ui.EnemyItemRenderer;
import com.gabo.gameoff.utils.ui.HeroItemRenderer;
import com.gabo.gameoff.utils.ui.Option;
import com.gabo.gameoff.utils.ui.OptionsTable;

public class CombatUI {

    public interface UIListener {
        void onActionSelected(Option<MenuActions> action);

        void onEnemySelected(Option<BaseUnit> enemy);

        void onHeroSelected(Option<BaseUnit> hero);

        void allHeroesFinished();
    }

    public Stage stage;
    Skin skin;
    public Assets assets;
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

    public CombatUI(CombatScreen screen) {
        this.screen = screen;
        this.stage = screen.stage;
        this.assets = screen.assets;
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
            screen.onActionSelected(item);
        });
    }

    private void prepareHeroListTable() {
        Array<BaseUnit> heroes = screen.combatManager.heroes;

        heroesTable = new OptionsTable<BaseUnit>(heroes, skin, new HeroItemRenderer(assets));
        heroesTable.setFocus(false);

        for (int i = 0; i < heroes.size; i++) {
            heroesTable.setCallback((item) -> {
                screen.onHeroSelected(item);
            });
        }
    }

    private void prepareEnemyListTable() {
        Array<BaseUnit> enemies = screen.combatManager.enemies;

        enemiesTable = new OptionsTable<BaseUnit>(enemies, skin, new EnemyItemRenderer(assets));

        for (int i = 0; i < enemies.size; i++) {
            enemiesTable.setCallback((item) -> {
                screen.onEnemySelected(item);
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

        if (turn.getAttacked() instanceof Enemy) {
            AnimationStrategy strategy = new PhysicalAttackAnimation();
            strategy.animate(turn, onFinish, this);
        } else {
            List<String> info = new ArrayList<>();
            info.add(turn.getAttacker().name + " attack " + turn.getAttacked().name);

            DialogueBox dialogue = new DialogueBox(skin);
            stage.addActor(dialogue);

            dialogue.setLines(info, () -> {
                dialogue.remove();
                onFinish.run();
            });

            dialogue.addLineEndListener(() -> {
                dialogue.addAction(Actions.sequence(
                        Actions.delay(1f),
                        Actions.run(dialogue::nextLine)));
            });
        }
    }

    public void resetHeroSelection() {
        heroesTable.resetSelection();
    }

    public void refreshHeroesInfo() {
        heroesTable.refresh(screen.combatManager.heroes);
    }

    Option<BaseUnit> findEnemy(BaseUnit enemy) {
        return enemiesTable.findByOption(enemy);
    }
}
