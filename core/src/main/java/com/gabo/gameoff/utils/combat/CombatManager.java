package com.gabo.gameoff.utils.combat;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;
import com.gabo.gameoff.entities.BaseUnit;
import com.gabo.gameoff.entities.Enemy;
import com.gabo.gameoff.entities.Hero;
import com.gabo.gameoff.screens.CombatScreen;

public class CombatManager {

    public enum CombatState {
        PLAYER_CHOOSE_ACTION, // player chooses action like fight, run, item, etc
        PLAYER_CHOOSE_TARGET, // TODO implement target selection
        ENEMY_TURN, // enemies choose actions
        COMBAT, // execute turn
        COMBAT_RESULT, // 1 vs 1 result
        RUN, // try to escape
        WIN, // win combat
        LOSE // lose combat
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

        enemies.add(new Enemy("Skeleton A", 10, screen.assets));
        enemies.add(new Enemy("Skeleton B", 10, screen.assets));

        heroes.add(new Hero("Warrior", 25, screen.assets));
        heroes.add(new Hero("Mage", 25, screen.assets));
        heroes.add(new Hero("Fighter", 0, screen.assets));
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
                screen.animateTurn(turnList.removeIndex(0));
                break;

            case COMBAT_RESULT:
                if (enemies.isEmpty()) { // all enemies defeated
                    setState(CombatState.WIN);
                    return;
                }

                if (allHeroesDead()) { // all heroes defeated
                    setState(CombatState.LOSE);
                    return;
                }

                if (turnList.isEmpty()) { // all turns executed, back to player action otherwise continue next turn
                    screen.resetSelection();
                    setState(CombatState.PLAYER_CHOOSE_ACTION);
                } else {
                    setState(CombatState.COMBAT);
                }
                break;

            case RUN:
                // TODO try to scape, if successful end combat else enemy turn
                screen.combatResultHandler(state);
                break;

            case WIN:
                // end combat
                screen.combatResultHandler(state);
                break;

            case LOSE:
                // end combat
                screen.combatResultHandler(state);
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
            if (!hero.disabled && hero.isAlive())
                return false;
        }
        return true;
    }

    public boolean allHeroesDead() {
        for (BaseUnit hero : heroes) {
            if (hero.isAlive())
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

    public boolean applyDamage(Turn turn) {
        // First, check if attacker is alive, otherwise skip turn
        if (turn.getAttacker() == null || !turn.getAttacker().isAlive()) {
            Gdx.app.log("CombatManager", "Attacker is dead, skipping turn.");
            return false;
        }

        BaseUnit selectedTarget = turn.getAttacked();

        if (selectedTarget == null || !selectedTarget.isAlive()) {
            Gdx.app.log("CombatManager", "Target is already dead, changing next available target.");

            // Find the next available target
            Array<BaseUnit> potentialTargets = (selectedTarget instanceof Hero) ? heroes : enemies;
            for (BaseUnit unit : potentialTargets) {
                if (unit.isAlive()) {
                    selectedTarget = unit;
                    turn.setAttacked(selectedTarget);
                    break;
                }
            }
        }
        // Apply damage to the (possibly new) target
        selectedTarget.receiveDamage(turn.getAttacker().getDamage());

        // Finally, remove from combat if dead
        if (selectedTarget instanceof Enemy && !selectedTarget.isAlive()) {
            enemies.removeValue(selectedTarget, false);
        }
        return true;
    }
}
