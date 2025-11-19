package com.gabo.gameoff.stages;

import com.badlogic.gdx.graphics.Camera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gabo.gameoff.Core;
import com.gabo.gameoff.screens.GameScreen;
import com.gabo.gameoff.utils.LdtkLevel;

public class GameStage extends Stage {
    public GameScreen game;

    public Group wallsGroup = new Group();
    public Group playerGroup = new Group();
    public Group npcsGroup = new Group();
    public Group backgroundGroup = new Group();

    public Actor cameraTarget;

    public GameStage(GameScreen game) {
        super();
        this.game = game;
        setDebugAll(Core.DEBUG);

        LdtkLevel level = new LdtkLevel(this);

        setViewport(new FitViewport(Core.VIEW_WIDTH, Core.VIEW_HEIGHT));
        getCamera().update();

        addActor(backgroundGroup);
        addActor(wallsGroup);
        addActor(npcsGroup);
        addActor(playerGroup);

        setCameraTarget(level.player);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        updateCamera();
    }

    public void setCameraTarget(Actor target) {
        this.cameraTarget = target;
        updateCamera();
    }

    public void updateCamera(){
        if (cameraTarget == null) return;
        Camera cam = getCamera();

        float camX = cameraTarget.getX() + Core.CELL_WIDTH / 2f;
        float camY = cameraTarget.getY() + Core.CELL_HEIGHT / 2f;

        camX = MathUtils.clamp(camX, cam.viewportWidth / 2f, 320 - cam.viewportWidth / 2f);
        camY = MathUtils.clamp(camY, cam.viewportHeight / 2f, 240 - cam.viewportHeight / 2f);

        cam.position.set(camX, camY, 0);
        cam.update();
    }
}
