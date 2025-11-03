package com.gabo.gameoff.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.gabo.gameoff.Core;

public class DialogueBox extends Table {
    private Label textLabel;
    private float timePerChar = 0.02f;
    private float elapsed;
    private boolean finished = true;
    private int visibleChars;
    private String fullText;

    private Runnable onFinish;

    List<String> lines = new ArrayList<>();
    int currentLine = 0;

    public DialogueBox(Skin skin) {
        super(skin);
        setFillParent(true);
        align(Align.bottom); // Alinea los hijos al fondo
        setDebug(false); // activar true para ver los bordes de la tabla si querés

        textLabel = new Label("", skin);
        textLabel.setWrap(true);

        add(textLabel)
                .width(Core.VIEW_WIDTH * 0.9f)
                .padBottom(10f)
                .center()
                .bottom()
                .row();
    }

    public void setDialogue(String text) {
        this.textLabel.setText("");
        this.fullText = text;
        elapsed = 0;
        finished = false;
        visibleChars = 0;
    }

    public void nextLine() {
        if (currentLine < lines.size() - 1) {
            currentLine++;
            setDialogue(lines.get(currentLine));
        } else {
            textLabel.setText("");
            lines.clear();
            if (onFinish != null) {
                onFinish.run();
                onFinish = null;
            }
            finished = true;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (finished || fullText == null)
            return;

        elapsed += delta;

        int charsToShow = Math.min(fullText.length(), (int) (elapsed / timePerChar));

        if (charsToShow != visibleChars) {
            visibleChars = charsToShow;
            textLabel.setText(fullText.substring(0, charsToShow));
        }

        if (charsToShow >= fullText.length()) {
            this.finished = true;
        }
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setLines(List<String> lines2, Runnable runnable) {
        lines = lines2;
        currentLine = 0;
        onFinish = runnable;
        setDialogue(lines.get(currentLine));
    }

    public void skip() {
        if (fullText != null) {
            textLabel.setText(fullText);
            finished = true;
        }
    }

    public boolean isAllLinesDone() {
        return lines.isEmpty();
    }
}
