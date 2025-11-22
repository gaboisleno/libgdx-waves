package com.gabo.gameoff.utils.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

/*
 * @gaboisleno
 * 15 nov 2025
 * this interface is used to interfere diferent types of options inside the OptionTable as enemies, items, spells,etc...
*/
public interface ItemRenderer<T> {
    void render(Table table, Option<T> row);

    void applySelectionStyle(Option<T> row, boolean isSelected, boolean isFocused);
}