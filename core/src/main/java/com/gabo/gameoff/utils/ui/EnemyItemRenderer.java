package com.gabo.gameoff.utils.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.gabo.gameoff.assets.Assets;
import com.gabo.gameoff.assets.Images;
import com.gabo.gameoff.entities.BaseUnit;

public class EnemyItemRenderer implements ItemRenderer<BaseUnit> {

    private final Skin skin;
    private final Assets assets;

    public EnemyItemRenderer(Assets assets) {
        this.assets = assets;
        this.skin = assets.getSkin();
    }

    @Override
    public void applySelectionStyle(Array<Label> rowLabels, boolean isSelected) {
        rowLabels.get(0).setVisible(isSelected);
    }

    @Override
    public void render(Table table, OptionsTable<BaseUnit>.Option row) {
        Label cursor = new Label(">", skin);
        Image sprite = new Image(assets.getImage(Images.creature_058));

        OptionsTable.addCursorAnimation(cursor);
        
        row.labels.add(cursor);
        row.image = sprite;
        
        table.add(cursor).padLeft(5).padRight(5);
        table.add(sprite);
    }
}
