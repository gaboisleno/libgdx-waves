package com.gabo.gameoff.utils.ui;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.utils.Array;
import com.gabo.gameoff.entities.BaseUnit;

public class HeroesTable extends OptionsTable<BaseUnit> {
    public HeroesTable(Array<BaseUnit> heroes, Skin skin) {
        super(heroes, skin, (table, hero, row) -> {
            Label name = new Label(hero.name, skin);
            Label hp = new Label(hero.hp + " / " + hero.maxHp, skin);

            table.add(name).left().padRight(10);
            table.add(hp);

            row.add(name);
            row.add(hp);
        });
    }

    @Override
    public void act(float delta) {
    }

    @Override
    public void updateVisualState() {
        for (int i = 0; i < rows.size; i++) {
            Row r = rows.get(i);
            r.cursor.setVisible(false);
            for (Label l : r.labels) {
                l.setColor(i == selectedIndex ? Color.WHITE : Color.GRAY);
            }
        }
        return;
    }

    @Override
    protected void addCursorAnimation(Label cursor) {
    }

    @Override
    public void clearSelectionHighlight() {
        for (int i = 0; i < rows.size; i++) {
            Row r = rows.get(i);
            r.cursor.setVisible(false);
            for (Label l : r.labels) {
                l.setColor(Color.WHITE);
            }
        }
    }

}
