package com.gabo.gameoff.entities;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.gabo.gameoff.assets.Assets;
import com.gabo.gameoff.assets.Images;

public class BaseUnit extends Actor {

    public String name;
    public int hp;
    public int maxHp;
    public boolean disabled = false;
    public Image image;
    public int attack = 5;

    public BaseUnit(String name, int hp, Assets assets) {
        this.name = name;
        this.hp = hp;
        this.maxHp = hp;
        this.image = new Image(assets.getImage(Images.creature_058));
    }

    public boolean isAlive() {
        return hp > 0;
    }

    public int getDamage() {
        return attack;
    }

    public void receiveDamage(int damage) {
        hp -= damage;
        if (hp < 0) {
            hp = 0;
        }
    }
}
