package com.gabo.gameoff.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;

public class Assets {
    private final AssetManager manager;

    public Assets() {
        this.manager = new AssetManager();
    }

    public void finishLoading() {
        manager.finishLoading();
    }

    public boolean update() {
        return manager.update();
    }

    public float getProgress() {
        return manager.getProgress();
    }

    public void loadImages() {
        for (Images img : Images.values()) {
            manager.load(img.getPath(), Texture.class);
        }
    }

    public void loadAtlases() {
        for (Atlases atlas : Atlases.values()) {
            manager.load(atlas.getPath(), TextureAtlas.class);
        }
    }

    public Texture getImage(Images img) {
        return manager.get(img.getPath(), Texture.class);
    }

    public void dispose() {
        manager.dispose();
    }

    public TextureAtlas getAtlas(Atlases atlas) {
        return manager.get(atlas.getPath(), TextureAtlas.class);
    }

    public void loadSkin() {
        manager.load("custom-skin/uiskin.json", Skin.class);
    }

    public Skin getSkin() {
        return manager.get("custom-skin/uiskin.json", Skin.class);
    }
}
