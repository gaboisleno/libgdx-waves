/*
 * @gaboisleno
*/
package com.gabo.gameoff.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gabo.gameoff.Core;
import com.gabo.gameoff.entities.BaseUnit;
import com.gabo.gameoff.utils.combat.CombatManager;
import com.gabo.gameoff.utils.combat.CombatManager.CombatState;
import com.gabo.gameoff.utils.combat.CombatUI;
import com.gabo.gameoff.utils.combat.CombatUI.UIListener;
import com.gabo.gameoff.utils.combat.Turn;

public class CombatScreen implements Screen, UIListener {
    GameScreen game;
    public Stage stage;

    public CombatManager combatManager;
    public CombatUI ui;

    public CombatScreen(GameScreen previousScreen) {
        game = previousScreen;
        stage = new Stage(new FitViewport(Core.VIEW_WIDTH, Core.VIEW_HEIGHT));

        combatManager = new CombatManager();
        ui = new CombatUI(this, stage, game.assets);
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0f, 0f, 0f, 1f);

        switch (combatManager.state) {

            case PLAYER_CHOOSE_ACTION:
                break;

            case PLAYER_CHOOSE_TARGET:
                break;

            case ENEMY_TURN:
                // do random enemy attacks
                for (BaseUnit enemy : combatManager.enemies) {
                    combatManager.addTurn(enemy,
                            combatManager.heroes.get(MathUtils.random(combatManager.heroes.size - 1)));
                }
                for (BaseUnit hero : combatManager.heroes) {
                    hero.disabled = false;
                }

                combatManager.shuffleTurn();
                // heroesTable.resetSelection();
                // heroesTable.clearSelectionHighlight();
                combatManager.setState(CombatState.COMBAT);
                break;

            case COMBAT:
                for (Turn turn : combatManager.getTurn()) {
                    Gdx.app.log("Turn:", turn.attacker.name + " attacks to " + turn.attacked.name);
                }
                // focusTable(actionsTable);
                combatManager.clearTurn();
                combatManager.setState(CombatState.PLAYER_CHOOSE_ACTION);
                break;

            case COMBAT_RESULT:
                break;

            case RUN:
                // try to scape
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

    @Override
    public void onActionSelected(String action) {
        // combatManager.setState(CombatState.PLAYER_CHOOSE_TARGET);
        combatManager.selectedHero = ui.getFocusedHero();
        ui.focusEnemy();
    }

    @Override
    public void onEnemySelected(BaseUnit enemy) {
        combatManager.selectedEnemy = enemy;
        combatManager.selectedHero.disabled = true;
        combatManager.addTurn();

        if (combatManager.allHeroesPlayed()) {
            combatManager.setState(CombatState.ENEMY_TURN);
            return;
        }

        ui.heroesNextIndex();
        ui.focusActions();
        return;
    }

    @Override
    public void onHeroSelected(BaseUnit hero) {
        combatManager.setState(CombatState.PLAYER_CHOOSE_ACTION);
        combatManager.selectedHero = hero;
        ui.focusActions();
    }

    @Override
    public void onRunSelected() {
        combatManager.setState(CombatState.RUN);
        game.hideCombatScreen();
    }

    @Override
    public void allHeroesFinished() {
        combatManager.setState(CombatState.ENEMY_TURN);
    }
}
