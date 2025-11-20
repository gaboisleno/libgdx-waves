package com.gabo.gameoff.utils.combat;

import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.gabo.gameoff.entities.BaseUnit;
import com.gabo.gameoff.entities.Enemy;
import com.gabo.gameoff.entities.Hero;
import com.gabo.gameoff.screens.CombatScreen;

public class CombatManager {

    public enum CombatState {
        PLAYER_CHOOSE_ACTION,
        PLAYER_CHOOSE_TARGET,
        ENEMY_TURN,
        COMBAT,
        COMBAT_RESULT,
        RUN,
        END
    }

    CombatScreen screen;

    public CombatState state = CombatState.PLAYER_CHOOSE_ACTION;
    public Array<Turn> turnList = new Array<>();

    public Array<BaseUnit> enemies = new Array<>();
    public Array<BaseUnit> heroes = new Array<>();

    private BaseUnit selectedEnemy;
    private BaseUnit selectedHero;

    public CombatManager(CombatScreen screen) {
        this.screen = screen;

        enemies.add(new Enemy("Skeleton A", 30));
        enemies.add(new Enemy("Skeleton B", 15));
        enemies.add(new Enemy("Skeleton C", 10));

        heroes.add(new Hero("Warrior", 30));
        heroes.add(new Hero("Mage", 10));
        heroes.add(new Hero("Fighter", 20));
    }

    public void setState(CombatState state) {
        this.state = state;

        switch (state) {
            case PLAYER_CHOOSE_ACTION:
                screen.resetSelection();
                break;

            case PLAYER_CHOOSE_TARGET:
                break;

            case ENEMY_TURN:
                // do random enemy attacks
                for (BaseUnit enemy : enemies) {
                    addTurn(enemy,
                            heroes.get(MathUtils.random(heroes.size - 1)));
                }
                for (BaseUnit hero : heroes) {
                    hero.disabled = false;
                }

                shuffleTurn();
                setState(CombatState.COMBAT);
                break;

            case COMBAT:
                screen.animateTurn(turnList.pop());
                break;

            case COMBAT_RESULT:
                if (turnList.isEmpty()) {
                    setState(CombatState.PLAYER_CHOOSE_ACTION);
                } else {
                    setState(CombatState.COMBAT);
                }
                break;

            case RUN:
                // try to scape
                break;

            case END:
                // end combat
                break;
        }
    }

    public Array<Turn> getTurn() {
        return turnList;
    }

    public void addTurn() {
        // TODO rename this
        selectedHero.disabled = true;
        addTurn(selectedHero, selectedEnemy);
    }

    public void addTurn(BaseUnit a, BaseUnit b) {
        Turn turn = new Turn(a, b, MenuActions.FIGHT);
        turnList.add(turn);
    }

    public void shuffleTurn() {
        turnList.shuffle();
    }

    public void clearTurn() {
        turnList.clear();
    }

    public boolean allHeroesPlayed() {
        for (BaseUnit hero : heroes) {
            if (!hero.disabled)
                return false;
        }
        return true;
    }

    public void selectHero(BaseUnit hero) {
        this.selectedHero = hero;
    }

    public void selectEnemy(BaseUnit enemy) {
        this.selectedEnemy = enemy;
    }
}
