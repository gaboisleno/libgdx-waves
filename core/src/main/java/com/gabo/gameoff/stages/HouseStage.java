package com.gabo.gameoff.stages;

import java.util.List;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Group;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gabo.gameoff.Core;
import com.gabo.gameoff.entities.Player;
import com.gabo.gameoff.screens.GameScreen;

public class HouseStage extends Stage {
    GameScreen game;
    public Group walls = new Group();
    public Group player = new Group();
    public Group npcs = new Group();

    public HouseStage(GameScreen game) {
        super();
        this.game = game;
        setDebugAll(Core.DEBUG);

        setViewport(new FitViewport(Core.VIEW_WIDTH, Core.VIEW_HEIGHT));
        getCamera().update();

        // add example wall
        Actor wall = new Actor();
        wall.setBounds(64, 64, Core.CELL_WIDTH, Core.CELL_HEIGHT);
        walls.addActor(wall);

        Player player = new Player(game.assets);
        addActor(player);
        addActor(walls);
    }

    public void showDialogues(List<String> lines) {
        game.showDialogues(lines);
    }
}
