package com.gabo.gameoff.assets;

import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.graphics.Texture;

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

    public Texture getImage(Images img) {
        return manager.get(img.getPath(), Texture.class);
    }

    public void dispose() {
        manager.dispose();
    }
}
