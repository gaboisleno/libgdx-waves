package com.gabo.gameoff.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.utils.Array;

public class AnimatedSprite {
    private TextureAtlas atlas;
    private TextureAtlas.AtlasRegion currentFrame;
    private Animation<TextureAtlas.AtlasRegion> animation;
    private String animationName = "";
    private float stateTime = 0f;
    private float frameDuration = 0.1f;
    public boolean flipH;

    public AnimatedSprite(TextureAtlas atlas) {
        this.atlas = atlas;
    }

    public void setAnimation(String track) {
        if (animationName.equals(track))
            return;

        Array<TextureAtlas.AtlasRegion> frames = atlas.findRegions(track);
        if (frames.isEmpty()) {
            Gdx.app.error("AnimatedSprite", "No frames found for animation: " + track);
            animation = null;
            return;
        }

        animationName = track;
        stateTime = 0f;
        animation = new Animation<>(frameDuration, frames, Animation.PlayMode.LOOP);
    }

    public void update(float delta) {
        if (animation == null)
            return;
        stateTime += delta;
        currentFrame = animation.getKeyFrame(stateTime);
        if (currentFrame.isFlipX() != flipH) {
            currentFrame.flip(true, false);
        }
    }

    public void draw(Batch batch, float x, float y) {
        batch.draw(currentFrame, x, y);
    }

    public boolean isFinished() {
        return animation != null && animation.isAnimationFinished(stateTime);
    }

    public void setLooping(boolean looping) {
        if (looping) {
            this.animation.setPlayMode(Animation.PlayMode.LOOP);
        } else {
            this.animation.setPlayMode(Animation.PlayMode.NORMAL);
        }
    }

    public void setFrameDuration(float duration) {
        this.frameDuration = duration;
        animation.setFrameDuration(duration);
    }

    public void setFlip(boolean flipX) {
        this.flipH = flipX;
        if (currentFrame.isFlipX() != flipH) {
            currentFrame.flip(true, false);
        }
    }
}
