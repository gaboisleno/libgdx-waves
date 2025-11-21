package com.gabo.gameoff.utils.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;
import com.gabo.gameoff.assets.Assets;
import com.gabo.gameoff.entities.BaseUnit;

public class HeroItemRenderer implements ItemRenderer<BaseUnit> {

    private final Skin skin;

    public HeroItemRenderer(Assets assets) {
        this.skin = assets.getSkin();
    }

    @Override
    public void applySelectionStyle(Array<Label> rowLabels, boolean isSelected, boolean isFocused) {
        Color color = (isSelected) ? Color.WHITE : Color.GRAY;

        for (Label l : rowLabels) {
            l.setColor(color);
        }
    }

    @Override
    public void render(Table table, OptionsTable<BaseUnit>.Option row) {
        Label name = new Label(row.value.name, skin);
        Label hp = new Label(row.value.hp + " / " + row.value.maxHp, skin);
        table.add(name).left().padRight(10);
        table.add(hp);

        row.labels.add(name);
        row.labels.add(hp);
    }
}
