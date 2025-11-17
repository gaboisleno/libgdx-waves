package com.gabo.gameoff.entities;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor {
    public String name;
    public int hp;
    public int maxHp;
    public boolean disabled = false;

    public Enemy(String name, int hp) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
    }

    public void applyDamage() {
        hp -= 5;
        if (hp <= 0) {
            hp = 0;
            disabled = true;
        }
    }
}
