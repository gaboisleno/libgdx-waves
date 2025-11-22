/*
 * @gaboisleno
*/
package com.gabo.gameoff.screens;

import com.badlogic.gdx.Screen;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gabo.gameoff.Core;
import com.gabo.gameoff.assets.Assets;
import com.gabo.gameoff.entities.BaseUnit;
import com.gabo.gameoff.utils.combat.CombatManager;
import com.gabo.gameoff.utils.combat.CombatManager.CombatState;
import com.gabo.gameoff.utils.combat.CombatUI;
import com.gabo.gameoff.utils.combat.CombatUI.UIListener;
import com.gabo.gameoff.utils.combat.MenuActions;
import com.gabo.gameoff.utils.combat.Turn;
import com.gabo.gameoff.utils.ui.Option;

public class CombatScreen implements Screen, UIListener {
    GameScreen game;
    public Stage stage;

    public CombatManager combatManager;
    public CombatUI ui;
    public Assets assets;

    public CombatScreen(GameScreen previousScreen) {
        game = previousScreen;
        stage = new Stage(new FitViewport(Core.VIEW_WIDTH, Core.VIEW_HEIGHT));
        assets = game.assets;

        combatManager = new CombatManager(this);
        ui = new CombatUI(this);
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

    @Override
    public void onActionSelected(Option<MenuActions> action) {
        switch (action.getValue()) {
            case FIGHT:
                combatManager.setSelectedAction(action.getValue());
                combatManager.setSelectedUnit(ui.getFocusedHero());
                combatManager.setState(CombatState.PLAYER_CHOOSE_TARGET);
                ui.focusEnemy();
                break;

            case RUN:
                combatManager.setState(CombatState.RUN);
                game.hideCombatScreen();
                break;

            default:
                break;
        }
    }

    @Override
    public void onEnemySelected(Option<BaseUnit> enemy) {
        combatManager.setSelectedTarget(enemy.getValue());
        combatManager.addTurn();

        if (combatManager.allHeroesPlayed()) {
            combatManager.setState(CombatState.ENEMY_TURN);
            return;
        }

        ui.heroesNextIndex();
        ui.focusActions();
    }

    @Override
    public void onHeroSelected(Option<BaseUnit> hero) {
        combatManager.setState(CombatState.PLAYER_CHOOSE_ACTION);
        combatManager.setSelectedUnit(hero.getValue());
        ui.focusActions();
    }

    @Override
    public void allHeroesFinished() {
        combatManager.setState(CombatState.ENEMY_TURN);
    }

    public void animateTurn(Turn turn) {
        combatManager.applyDamage(turn);
        ui.animate(turn, () -> {
            ui.refreshHeroesInfo();
            combatManager.setState(CombatState.COMBAT_RESULT);
        });

    }

    public void resetSelection() {
        ui.focusActions();
        ui.resetHeroSelection();
    }
}
