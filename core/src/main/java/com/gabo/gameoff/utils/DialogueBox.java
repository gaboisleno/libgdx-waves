package com.gabo.gameoff.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;

public class DialogueBox extends Table {
    private Label textLabel;
    private float timePerChar = 0.02f;
    private float elapsed;
    private boolean finished;
    private int visibleChars;
    private String fullText;

    List<String> lines = new ArrayList<>();
    int currentLine = 0;

    public DialogueBox(Skin skin) {
        super(skin);
        setFillParent(true);

        textLabel = new Label("", skin);
        textLabel.setWrap(true);
        add(textLabel).width(400).row();
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

    public void setLines(List<String> lines2) {
        lines = lines2;
        currentLine = 0;
        setDialogue(lines.get(currentLine));
    }

    public void skip() {
        if (fullText != null) {
            textLabel.setText(fullText);
            finished = true;
        }
    }
}
