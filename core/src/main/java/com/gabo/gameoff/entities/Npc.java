package com.gabo.gameoff.entities;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.gabo.gameoff.Core;
import com.gabo.gameoff.assets.Assets;
import com.gabo.gameoff.assets.Atlases;
import com.gabo.gameoff.stages.HouseStage;
import com.gabo.gameoff.utils.AnimatedSprite;

public class Npc extends Actor {

    private AnimatedSprite animatedSprite;
    public Rectangle interactArea;
    public List<String> dialogues = new ArrayList<>();

    public Npc(Assets assets) {
        setBounds(0, 0, Core.CELL_WIDTH, Core.CELL_HEIGHT);
        interactArea = new Rectangle(0, 0, Core.CELL_WIDTH + 16, Core.CELL_HEIGHT + 16);

        animatedSprite = new AnimatedSprite(assets.getAtlas(Atlases.player));
        animatedSprite.setAnimation("idle");
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        animatedSprite.update(delta);
        interactArea.setPosition(
                getX() - (interactArea.width - getWidth()) / 2,
                getY() - (interactArea.height - getHeight()) / 2);
    }

    @Override
    public void draw(Batch batch, float parentAlpha) {
        super.draw(batch, parentAlpha);
        animatedSprite.draw(batch, getX() - getWidth() / 2, getY() - getHeight() / 2);
    }

    public void interact() {
        HouseStage stage = (HouseStage) getStage();
        stage.showDialogues(dialogues);
    }
}
