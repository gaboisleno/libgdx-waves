package com.gabo.gameoff.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.gabo.gameoff.entities.Player;
import com.gabo.gameoff.stages.HouseStage;

public class LdtkLevel {
    public Image background;
    private final String path = "maps/demo/simplified/";

    public LdtkLevel(HouseStage stage) {
        JsonValue root = new JsonReader().parse(Gdx.files.internal(path + "Level_0/data.json"));
        Texture mapTexture = new Texture(Gdx.files.internal(path + "Level_0/Tiles.png"));

        background = new Image(mapTexture);
        stage.addActor(background);

        float levelHeight = root.getFloat("height");

        JsonValue entities = root.get("entities");
        if (entities == null)
            return;

        if (entities.has("Player")) {
            JsonValue layer = entities.get("Player");
            for (JsonValue entity : layer) {
                float x = entity.getFloat("x");
                float y = levelHeight - entity.getFloat("y") - entity.getFloat("height");
                Player player = new Player(stage.game.assets);
                player.setPos(x, y);

                stage.player.addActor(player);
            }
            stage.addActor(stage.player);
        }

        if (entities.has("Walls")) {
            JsonValue layer = entities.get("Walls");
            for (JsonValue entity : layer) {
                float x = entity.getFloat("x");
                float y = levelHeight - entity.getFloat("y") - entity.getFloat("height");
                Actor wall = new Actor();
                wall.setBounds(x, y, entity.getInt("width"), entity.getInt("height"));
                stage.walls.addActor(wall);
            }
            stage.addActor(stage.walls);
        }
    }

}
