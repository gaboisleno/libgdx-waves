package com.gabo.gameoff.utils;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;
import com.gabo.gameoff.entities.Npc;
import com.gabo.gameoff.entities.Player;
import com.gabo.gameoff.stages.GameStage;

public class LdtkLevel {
    private final String path = "maps/demo/";
    float levelHeight, levelWidth;
    GameStage stage;
    public Actor player;

    public LdtkLevel(GameStage stage) {
        this.stage = stage;
        loadLevel(stage);
    }

    private void loadLevel(GameStage stage) {
        JsonValue root = new JsonReader().parse(Gdx.files.internal(path + "Level_0.ldtkl"));

        levelHeight = root.getFloat("pxHei");
        levelWidth = root.getFloat("pxWid");

        JsonValue layerInstances = root.get("layerInstances");

        if (layerInstances == null)
            return;

        for (JsonValue layer : layerInstances) {
            String type = layer.getString("__type");

            switch (type) {
                case "Entities":
                    loadEntities(layer);
                    break;
                case "Tiles":
                    loadTiles(layer);
                    break;
                case "IntGrid":
                    loadIntGrid(layer);
                    break;
                default:
                    break;
            }
        }

    }

    private void loadIntGrid(JsonValue layer) {
        int gridSize = layer.getInt("__gridSize");
        JsonValue intGrid = layer.get("intGridCsv");

        // cada valor del intGridCsv representa una celda
        int cWid = layer.getInt("__cWid");
        int cHei = layer.getInt("__cHei");

        for (int i = 0; i < intGrid.size; i++) {
            int value = intGrid.getInt(i);
            if (value == 0)
                continue; // sin colisión

            int x = (i % cWid) * gridSize;
            int y = (i / cWid) * gridSize;

            // invertir eje Y (LDtk usa 0 arriba)
            y = (cHei * gridSize) - y - gridSize;

            // crear un actor invisible de colisión
            Actor wall = new Actor();
            wall.setBounds(x, y, gridSize, gridSize);

            stage.wallsGroup.addActor(wall);
        }
    }

    private void loadTiles(JsonValue layer) {
        String pathUrl = "maps/demo/png/Level_0" + "__" + layer.getString("__identifier") + ".png";
        Texture mapTexture = new Texture(Gdx.files.internal(pathUrl));
        Image background = new Image(mapTexture);
        // Since map is reading from top to bottom, I need to change the layer order
        stage.backgroundGroup.addActorAt(0, background);
    }

    private void loadEntities(JsonValue layer) {
        JsonValue entities = layer.get("entityInstances");
        if (entities == null)
            return;

        for (JsonValue entity : entities) {
            float x = entity.getFloat("__worldX");
            float y = levelHeight - entity.getFloat("__worldY") - entity.getFloat("height");

            switch (entity.getString("__identifier")) {
                case "Player":
                    Player player = new Player(stage.game.assets);
                    player.setPosition(x, y);
                    stage.playerGroup.addActor(player);
                    
                    // store player ref
                    this.player = player;
                    break;

                case "Npc":
                    Npc npc = new Npc(stage.game.assets);
                    npc.setPosition(x, y);

                    for (JsonValue field : entity.get("fieldInstances")) {
                        if (field.getString("__identifier").equals("Dialogues")) {
                            JsonValue dialoguesArray = field.get("__value");
                            if (dialoguesArray != null) {
                                for (JsonValue dialog : dialoguesArray) {
                                    npc.dialogues.add(dialog.asString());
                                }
                            }
                        }
                    }

                    stage.npcsGroup.addActor(npc);
                    break;

                default:
                    break;
            }
        }
    }
}
