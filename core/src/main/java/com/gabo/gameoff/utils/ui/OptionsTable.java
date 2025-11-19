package com.gabo.gameoff.utils.ui;

import java.util.function.Consumer;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class OptionsTable<T> extends Table {
    protected static class Row {
        Label cursor;
        Array<Label> labels = new Array<>();
    }

    protected Array<T> options = new Array<>();
    public Array<Row> rows = new Array<>();
    protected Consumer<T> callback;

    protected ItemRenderer<T> renderer;
    public Skin skin;

    protected boolean focused = false;
    protected int selectedIndex = 0;
    protected float keyCooldown = 0;

    public OptionsTable(Array<T> items, Skin skin, ItemRenderer<T> renderer) {
        this.options = items;
        this.renderer = renderer;
        this.skin = skin;
        background(skin.getDrawable("dialogue"));
        buildRows();
        updateVisualState();
    }

    private void buildRows() {
        for (int i = 0; i < options.size; i++) {
            Row row = new Row();
            rows.add(row);

            row.cursor = new Label(">", skin);
            addCursorAnimation(row.cursor);

            add(row.cursor).pad(0, 5, 0, 5);

            row.labels.add(row.cursor);
            renderer.render(this, options.get(i), row.labels);
            row();
        }
    }

    public void refresh() {
        clearChildren();
        rows.clear();
        buildRows();
    }

    protected void addCursorAnimation(Label cursor) {
        cursor.addAction(Actions.forever(
                Actions.sequence(
                        Actions.alpha(0f),
                        Actions.delay(0.25f),
                        Actions.alpha(1f),
                        Actions.delay(0.25f))));
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
        for (int i = 0; i < rows.size; i++) {
            Row r = rows.get(i);
            r.cursor.setVisible(i == selectedIndex && focused);
        }
    }

    private void emmitEvent(int index) {
        if (callback != null)
            callback.accept(options.get(index));
    }

    public void setFocus(boolean value) {
        keyCooldown = 0.10f;
        this.focused = value;
        updateVisualState();
    }

    public void setCallback(Consumer<T> callback) {
        this.callback = callback;
    }

    public T getFocused() {
        return options.get(selectedIndex);
    }

    public void nextIndex() {
        selectedIndex = (selectedIndex + 1) % options.size;
        updateVisualState();
    }

    public void prevIndex() {
        selectedIndex = (selectedIndex - 1 + options.size) % options.size;
        updateVisualState();
    }

    public void resetSelection() {
        selectedIndex = 0;
        updateVisualState();
    }

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
