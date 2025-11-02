package com.gabo.gameoff.assets;

public enum Images {
    alien_egg,
    ship,
    space;

    public String getPath() {
        return "images/" + name() + ".png";
    }
}
