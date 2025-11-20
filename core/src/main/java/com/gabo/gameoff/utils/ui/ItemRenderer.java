package com.gabo.gameoff.utils.ui;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

/*
 * @gaboisleno
 * 15 nov 2025
 * this interface is used to interfere diferent types of options inside the OptionTable as enemies, items, spells,etc...
*/
public interface ItemRenderer<T> {
    void render(Table table, OptionsTable<T>.Option row);
    void applySelectionStyle(Array<Label> row, boolean isSelected);
}