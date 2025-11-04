package com.gabo.gameoff.assets;

public enum Atlases {
    player,
    red_npc;

    public String getPath() {
        return "atlases/" + name() + ".atlas";
    }
}
