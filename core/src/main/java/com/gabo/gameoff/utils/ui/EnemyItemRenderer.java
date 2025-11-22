package com.gabo.gameoff.utils.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gabo.gameoff.assets.Assets;
import com.gabo.gameoff.entities.BaseUnit;

public class EnemyItemRenderer implements ItemRenderer<BaseUnit> {

    private final Skin skin;

    public EnemyItemRenderer(Assets assets) {
        this.skin = assets.getSkin();
    }

    @Override
    public void applySelectionStyle(Option<BaseUnit> row, boolean isSelected, boolean isFocused) {
        row.labels.get(0).setVisible(isSelected && isFocused);
    }

    @Override
    public void render(Table table, Option<BaseUnit> row) {
        Label cursor = new Label(">", skin);

        OptionsTable.addCursorAnimation(cursor);

        row.labels.add(cursor);

        table.add(cursor).padLeft(5).padRight(5);
        table.add(row.getValue().image);
        table.row();
    }
}
