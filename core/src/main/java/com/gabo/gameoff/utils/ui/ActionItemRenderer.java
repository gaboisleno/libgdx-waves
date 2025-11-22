package com.gabo.gameoff.utils.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.gabo.gameoff.assets.Assets;
import com.gabo.gameoff.utils.combat.MenuActions;

public class ActionItemRenderer implements ItemRenderer<MenuActions> {

    private final Skin skin;

    public ActionItemRenderer(Assets assets) {
        this.skin = assets.getSkin();
    }

    @Override
    public void applySelectionStyle(Option<MenuActions> row, boolean isSelected, boolean isFocused) {
        row.labels.get(0).setVisible(isSelected && isFocused);

        Color color = (isSelected) ? Color.WHITE : Color.GRAY;

        for (Label l : row.labels) {
            l.setColor(color);
        }
    }

    @Override
    public void render(Table table, Option<MenuActions> row) {
        Label actionLabel = new Label(row.getValue().toString(), skin);
        Label cursor = new Label(">", skin);

        OptionsTable.addCursorAnimation(cursor);

        row.labels.add(cursor);
        row.labels.add(actionLabel);

        table.add(cursor).padLeft(5).padRight(5);
        table.add(actionLabel).left().padRight(5);
        table.row();
    }

}
