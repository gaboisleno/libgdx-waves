package com.gabo.gameoff.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gabo.gameoff.Core;
import com.gabo.gameoff.assets.Assets;
import com.gabo.gameoff.assets.Atlases;
import com.gabo.gameoff.stages.GameStage;
import com.gabo.gameoff.utils.AnimatedSprite;

public class Player extends Actor {
    private enum Direction {
        UP, DOWN, LEFT, RIGHT
    }

    private final float SPEED = 96;
    private AnimatedSprite animatedSprite;
    private Direction direction = Direction.DOWN;

    public Player(Assets assets) {
        setBounds(0, 0, Core.CELL_WIDTH - 4, Core.CELL_HEIGHT - 4);

        animatedSprite = new AnimatedSprite(assets.getAtlas(Atlases.player));
        animatedSprite.setAnimation("idle");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        animatedSprite.update(delta);

        float moveX = 0, moveY = 0;

        if (Gdx.input.isKeyPressed(Keys.A)) {
            moveX = -1;
        } else if (Gdx.input.isKeyPressed(Keys.D)) {
            moveX = 1;
        }

        if (Gdx.input.isKeyPressed(Keys.W)) {
            moveY = 1;
        } else if (Gdx.input.isKeyPressed(Keys.S)) {
            moveY = -1;
        }

        if (moveX != 0 || moveY != 0) {
            Vector2 dir = new Vector2(moveX, moveY).nor();
            direction = Math.abs(dir.x) > Math.abs(dir.y)
                    ? (dir.x > 0 ? Direction.RIGHT : Direction.LEFT)
                    : (dir.y > 0 ? Direction.UP : Direction.DOWN);

            tryMoveBy(dir.x * SPEED * delta, dir.y * SPEED * delta);
            setWalkingAnimation();
        } else {
            setIdleAnimation();
        }

        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
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
        GameStage stage = (GameStage) getStage();
        Rectangle bounds = new Rectangle(moveX, moveY, getWidth(), getHeight());
        Rectangle wallBounds = new Rectangle(0, 0, 0, 0);

        for (Actor wall : stage.wallsGroup.getChildren()) {
            wallBounds = new Rectangle(wall.getX(), wall.getY(), wall.getWidth(), wall.getHeight());
            if (bounds.overlaps(wallBounds)) {
                return true;
            }
        }
        return false;
    }

    public void interact() {
        GameStage stage = (GameStage) getStage();
        Rectangle bounds = new Rectangle(getX(), getY(), getWidth(), getHeight());

        for (Actor actor : stage.npcsGroup.getChildren()) {
            Npc npc = (Npc) actor;

            if (npc.interactArea.overlaps(bounds)) {
                npc.interact();
            }
        }
    }

    private void setWalkingAnimation() {
        animatedSprite.setFlip(false);
        switch (direction) {
            case UP:
                animatedSprite.setAnimation("walk_up");
                break;
            case DOWN:
                animatedSprite.setAnimation("walk");
                break;
            case RIGHT:
                animatedSprite.setAnimation("walk_right");
                break;
            case LEFT:
                animatedSprite.setFlip(true);
                animatedSprite.setAnimation("walk_right");
                break;
            default:
                break;
        }
    }

    public void setIdleAnimation() {
        animatedSprite.setFlip(false);
        switch (direction) {
            case UP:
                animatedSprite.setAnimation("idle_up");
                break;
            case DOWN:
                animatedSprite.setAnimation("idle");
                break;
            case RIGHT:
                animatedSprite.setAnimation("idle_right");
                break;
            case LEFT:
                animatedSprite.setFlip(true);
                animatedSprite.setAnimation("idle_right");
                break;
            default:
                break;
        }
    }
}
