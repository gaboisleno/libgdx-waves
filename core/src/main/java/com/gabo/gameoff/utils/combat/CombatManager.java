package com.gabo.gameoff.utils.combat;

import com.badlogic.gdx.utils.Array;
import com.gabo.gameoff.entities.BaseUnit;
import com.gabo.gameoff.entities.Enemy;
import com.gabo.gameoff.entities.Hero;

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

    public CombatState state = CombatState.PLAYER_CHOOSE_ACTION;
    public Array<Turn> turnList = new Array<>();

    public Array<BaseUnit> enemies = new Array<>();
    public Array<BaseUnit> heroes = new Array<>();

    public BaseUnit selectedEnemy;
    public BaseUnit selectedHero;

    public CombatManager() {
        enemies.add(new Enemy("Goblin", 30));
        enemies.add(new Enemy("Slime", 15));
        enemies.add(new Enemy("Skeleton", 10));

        heroes.add(new Hero("Warrior", 30));
        heroes.add(new Hero("Mage", 10));
        heroes.add(new Hero("Fighter", 20));
    }

    public void setState(CombatState state) {
        this.state = state;
    }

    public Array<Turn> getTurn() {
        return turnList;
    }

    public void addTurn() {
        Turn turn = new Turn();
        turn.attacker = selectedHero;
        turn.attacked = selectedEnemy;
        turn.action = "fight"; // hardcoded for now
        turnList.add(turn);
    }

    public void addTurn(BaseUnit a, BaseUnit b) {
        Turn turn = new Turn();
        turn.attacker = a;
        turn.attacked = b;
        turn.action = "fight"; // hardcoded for now
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
}
