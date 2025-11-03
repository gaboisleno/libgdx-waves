package com.gabo.gameoff.stages;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gabo.gameoff.Core;
import com.gabo.gameoff.screens.GameScreen;
import com.gabo.gameoff.utils.LdtkLevel;

public class HouseStage extends Stage {
    public GameScreen game;

    public Group walls = new Group();
    public Group player = new Group();
    public Group npcs = new Group();

    public HouseStage(GameScreen game) {
        super();
        this.game = game;
        setDebugAll(Core.DEBUG);

        LdtkLevel level = new LdtkLevel(this);

        setViewport(new FitViewport(Core.VIEW_WIDTH, Core.VIEW_HEIGHT));
        getCamera().update();

    }

    public void showDialogues(List<String> lines) {
        game.showDialogues(lines);
    }
}
