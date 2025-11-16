package com.gabo.gameoff.utils.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Array;

public class OptionsTable<T> extends Table {
    private Array<Label> cursors = new Array<Label>();

    private Array<T> options = new Array<>();
    private boolean focused = false;
    private int selectedIndex = 0;
    private Array<Runnable> callbacks = new Array<>();

    private float keyCooldown = 0f;

    private ItemRenderer<T> renderer;
    Skin skin;

    public OptionsTable(Array<T> items, Skin skin, ItemRenderer<T> renderer) {
        this.options = items;
        this.renderer = renderer;
        this.skin = skin;
        background(skin.getDrawable("dialogue"));
        buildRows();
    }

    private void buildRows() {
        for (T opt : options) {

            Label cursor = new Label(">", skin);
            cursor.addAction(Actions.forever(
                    Actions.sequence(
                            Actions.alpha(0f),
                            Actions.delay(0.1f),
                            Actions.alpha(1f),
                            Actions.delay(0.1f))));

            add(cursor).padLeft(5).padRight(5);
            cursors.add(cursor);

            renderer.render(this, opt);

            row();

            callbacks.add(null);
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        if (keyCooldown > 0) {
            keyCooldown -= delta;
            return;
        }
        if (focused) {
            if (Gdx.input.isKeyJustPressed(Keys.ENTER)) {
                emmitEvent(selectedIndex);
                return;
            }
            if (Gdx.input.isKeyJustPressed(Keys.W)) {
                selectedIndex = (selectedIndex - 1 + options.size) % options.size;
            }
            if (Gdx.input.isKeyJustPressed(Keys.S)) {
                selectedIndex = (selectedIndex + 1) % options.size;
            }
            for (int i = 0; i < cursors.size; i++) {
                cursors.get(i).setVisible(i == selectedIndex);
            }
        } else {
            for (int i = 0; i < cursors.size; i++) {
                cursors.get(i).setVisible(false);
            }
        }
    }

    private void emmitEvent(int index) {
        Runnable r = callbacks.get(index);
        if (r != null)
            r.run();
    }

    public void setFocus(boolean value) {
        keyCooldown = 0.10f;
        this.focused = value;
    }

    public void setCallback(int index, Runnable callback) {
        callbacks.set(index, callback);
    }
}
