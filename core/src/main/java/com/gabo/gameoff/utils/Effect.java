package com.gabo.gameoff.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;

public class Effect extends Actor {
    AnimatedSprite animatedSprite;

    public Effect(TextureAtlas atlas, float x, float y) {
        setPosition(x, y);
        this.animatedSprite = new AnimatedSprite(atlas);
        animatedSprite.setAnimation("hit_effect");
        animatedSprite.setLooping(false);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        animatedSprite.update(delta);

        if (animatedSprite.isFinished()) {
            Gdx.app.log("Effect", "Animation finished, removing effect actor.");
            remove();
        }
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        animatedSprite.draw(batch, getX(), getY());
    }

}
