package com.gabo.gameoff.utils.combat;

import com.gabo.gameoff.entities.BaseUnit;

public class Turn {
    private BaseUnit attacker;
    private BaseUnit attacked;
    private MenuActions action;

    public Turn(BaseUnit attacker, BaseUnit attacked, MenuActions fight) {
        this.attacker = attacker;
        this.attacked = attacked;
        this.action = fight;
    }

    public BaseUnit getAttacker() {
        return attacker;
    }

    public BaseUnit getAttacked() {
        return attacked;
    }

    public MenuActions getAction() {
        return action;
    }
}
