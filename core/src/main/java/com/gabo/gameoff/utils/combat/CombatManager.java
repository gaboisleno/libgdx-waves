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

    private BaseUnit selectedTarget;
    private BaseUnit selectedUnit;
    private MenuActions selectedAction;

    public CombatManager(CombatScreen screen) {
        this.screen = screen;

        enemies.add(new Enemy("Skeleton A", 30, screen.assets));
        enemies.add(new Enemy("Skeleton B", 15, screen.assets));

        heroes.add(new Hero("Warrior", 30, screen.assets));
        heroes.add(new Hero("Mage", 10, screen.assets));
        heroes.add(new Hero("Fighter", 20, screen.assets));
    }

    public void setState(CombatState state) {
        this.state = state;

        switch (state) {
            case PLAYER_CHOOSE_ACTION:
                break;

            case PLAYER_CHOOSE_TARGET:
                break;

            case ENEMY_TURN:
                // do random enemy attacks
                for (BaseUnit enemy : enemies) {
                    setSelectedUnit(enemy);
                    setSelectedTarget(heroes.get(MathUtils.random(heroes.size - 1)));
                    setSelectedAction(MenuActions.FIGHT);
                    addTurn();
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
                    screen.resetSelection();
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

    public Array<Turn> getTurnList() {
        return turnList;
    }

    public void addTurn() {
        getSelectedUnit().disabled = true;
        turnList.add(new Turn(getSelectedUnit(), getSelectedTarget(), getSelectedAction()));
        setSelectedUnit(null);
        setSelectedTarget(null);
        setSelectedAction(null);
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

    public void setSelectedUnit(BaseUnit unit) {
        this.selectedUnit = unit;
    }

    public void setSelectedTarget(BaseUnit target) {
        this.selectedTarget = target;
    }

    public void setSelectedAction(MenuActions action) {
        this.selectedAction = action;
    }

    public BaseUnit getSelectedUnit() {
        return selectedUnit;
    }

    public BaseUnit getSelectedTarget() {
        return selectedTarget;
    }

    public MenuActions getSelectedAction() {
        return selectedAction;
    }

    public void applyDamage(Turn turn) {
        turn.getAttacked().receiveDamage(turn.getAttacker().getDamage());
    }
}
