package com.gabo.gameoff.assets;

public enum Atlases {
    player;

    public String getPath() {
        return "atlases/" + name() + ".atlas";
    }
}
