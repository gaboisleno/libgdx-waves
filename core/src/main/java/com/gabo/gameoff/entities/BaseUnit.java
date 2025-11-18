package com.gabo.gameoff.entities;

import com.badlogic.gdx.scenes.scene2d.Actor;

public class BaseUnit extends Actor {

    public String name;
    public int hp;
    public int maxHp;
    public boolean disabled = false;

    public BaseUnit(String name, int hp) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
    }

}
