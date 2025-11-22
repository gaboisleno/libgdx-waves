package com.gabo.gameoff.utils.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gabo.gameoff.assets.Assets;
import com.gabo.gameoff.entities.BaseUnit;

public class HeroItemRenderer implements ItemRenderer<BaseUnit> {

    private final Skin skin;

    public HeroItemRenderer(Assets assets) {
        this.skin = assets.getSkin();
    }

    @Override
    public void applySelectionStyle(Option<BaseUnit> row, boolean isSelected, boolean isFocused) {
        Color color;
        if (row.getValue().isAlive()) {
            color = (isSelected) ? Color.WHITE : Color.GRAY;
        } else {
            color = Color.MAROON;
        }

        for (Label l : row.labels) {
            l.setColor(color);
        }

    }

    @Override
    public void render(Table table, Option<BaseUnit> row) {
        Label name = new Label(row.getValue().name, skin);
        Label hp = new Label(row.getValue().hp + " / " + row.getValue().maxHp, skin);

        if (!row.getValue().isAlive()) {
            name.setColor(Color.RED);
            hp.setColor(Color.RED);
        }

        table.add(name).left().padRight(10);
        table.add(hp);
        table.row();

        row.labels.add(name);
        row.labels.add(hp);
    }
}
