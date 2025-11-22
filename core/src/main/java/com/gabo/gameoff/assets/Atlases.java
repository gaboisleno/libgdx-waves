package com.gabo.gameoff.assets;

public enum Atlases {
    player,
    red_npc,
    hit_effect;

    public String getPath() {
        return "atlases/" + name() + ".atlas";
    }
}
