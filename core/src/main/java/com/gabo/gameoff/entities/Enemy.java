package com.gabo.gameoff.entities;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class Enemy extends Actor {
    public String name;
    public int hp;
    public int maxHp;

    public Enemy(String name, int hp) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
    }
}
