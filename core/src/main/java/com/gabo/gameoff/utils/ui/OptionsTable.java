package com.gabo.gameoff.utils.ui;

import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class OptionsTable<T> extends Table {

    public class Option {
        public Array<Label> labels = new Array<>();
        public Image image;
        public T value;
    }

    public Array<Option> optionList = new Array<>();

    protected Consumer<Option> callback;
    protected ItemRenderer<T> renderer;
    public Skin skin;

    protected boolean focused = false;
    protected int selectedIndex = 0;
    protected float keyCooldown = 0;

    public OptionsTable(Array<T> items, Skin skin, ItemRenderer<T> renderer) {
        this.renderer = renderer;
        this.skin = skin;

        background(skin.getDrawable("dialogue"));
        buildRows(items);
        updateVisualState();
    }

    private void buildRows(Array<T> options) {
        for (T option : options) {

            Option row = new Option();
            row.value = option;
            row.labels = new Array<>();

            renderer.render(this, row);
            row();

            optionList.add(row);
        }
    }

    public void refresh(Array<T> items) {
        clearChildren();
        optionList.clear();
        buildRows(items);
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (keyCooldown > 0) {
            keyCooldown -= delta;
            return;
        }

        if (!focused)
            return;

        // TODO you shouldn't manage the inputs directly in the table, but i dont have
        // time to change it
        if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
            emmitEvent(selectedIndex);
            return;
        }
        if (Gdx.input.isKeyJustPressed(Keys.W))
            prevIndex();
        if (Gdx.input.isKeyJustPressed(Keys.S))
            nextIndex();
    }

    public void updateVisualState() {
        for (int i = 0; i < optionList.size; i++) {
            Array<Label> r = optionList.get(i).labels;

            boolean isSelected = (i == selectedIndex);
            renderer.applySelectionStyle(r, isSelected, focused);
        }
    }

    private void emmitEvent(int index) {
        if (callback != null)
            callback.accept(optionList.get(index));
    }

    public void setFocus(boolean value) {
        keyCooldown = 0.10f;
        this.focused = value;
        updateVisualState();
    }

    public void setCallback(Consumer<Option> callback) {
        this.callback = callback;
    }

    public T getFocused() {
        return optionList.get(selectedIndex).value;
    }

    public void nextIndex() {
        selectedIndex = (selectedIndex + 1) % optionList.size;
        updateVisualState();
    }

    public void prevIndex() {
        selectedIndex = (selectedIndex - 1 + optionList.size) % optionList.size;
        updateVisualState();
    }

    public void resetSelection() {
        selectedIndex = 0;
        updateVisualState();
    }

    public Option findByOption(T item) {
        for (int i = 0; i < optionList.size; i++) {
            if (optionList.get(i).value == item) {
                return optionList.get(i);
            }
        }
        return null;
    }

    public void clearSelectionHighlight() {
        // TODO
    }

    public static void addCursorAnimation(Label cursor) {
        cursor.addAction(Actions.forever(
                Actions.sequence(
                        Actions.alpha(0f),
                        Actions.delay(0.25f),
                        Actions.alpha(1f),
                        Actions.delay(0.25f))));
    }

}
