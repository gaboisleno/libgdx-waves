package com.gabo.gameoff.entities;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gabo.gameoff.Core;
import com.gabo.gameoff.assets.Assets;
import com.gabo.gameoff.assets.Atlases;
import com.gabo.gameoff.stages.HouseStage;
import com.gabo.gameoff.utils.AnimatedSprite;

public class Player extends Actor {
    private final float SPEED = 100;
    private AnimatedSprite animatedSprite;

    public Player(Assets assets) {
        setBounds(0, 0, Core.CELL_WIDTH, Core.CELL_HEIGHT);

        animatedSprite = new AnimatedSprite(assets.getAtlas(Atlases.player));
        animatedSprite.setAnimation("idle");
    }

    public void setPos(float x, float y) {
        this.setPosition(x, y);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        animatedSprite.update(delta);

        float moveX = 0, moveY = 0;

        if (Gdx.input.isKeyPressed(Keys.A)) {
            moveX = -SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Keys.D)) {
            moveX = SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Keys.W)) {
            moveY = SPEED * delta;
        }
        if (Gdx.input.isKeyPressed(Keys.S)) {
            moveY = -SPEED * delta;
        }
        if (moveX != 0 || moveY != 0) {
            tryMoveBy(moveX, moveY);
            animatedSprite.setAnimation("walk");
        } else {
            animatedSprite.setAnimation("idle");
        }

        if (Gdx.input.isKeyPressed(Keys.ENTER)) {
            interact();
        }
        getStage().getCamera().position.set(getX() + Core.CELL_WIDTH / 2, getY() + Core.CELL_HEIGHT / 2, 0);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        animatedSprite.draw(batch, getX() - getWidth() / 2, getY() - getHeight() / 2);
    }

    private void tryMoveBy(float dx, float dy) {
        if (!collides(getX() + dx, getY())) {
            moveBy(dx, 0);
        }
        if (!collides(getX(), getY() + dy)) {
            moveBy(0, dy);
        }
    }

    private boolean collides(float moveX, float moveY) {
        HouseStage stage = (HouseStage) getStage();
        Rectangle bounds = new Rectangle(moveX, moveY, getWidth(), getHeight());
        Rectangle wallBounds = new Rectangle(0, 0, 0, 0);

        for (Actor wall : stage.walls.getChildren()) {
            wallBounds = new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
            if (bounds.overlaps(wallBounds)) {
                return true;
            }
        }
        return false;
    }

    public void interact() {
        HouseStage stage = (HouseStage) getStage();
        List<String> exampleLines = new ArrayList<>(
                Arrays.asList(
                        "Hi... my name is Richard",
                        "There is something strage happening.",
                        "The rain is red",
                        "I need a refugee, would you let me in please ?."));
        stage.showDialogues(exampleLines);
    }
}
