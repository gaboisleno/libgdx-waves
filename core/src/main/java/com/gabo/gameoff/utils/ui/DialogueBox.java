package com.gabo.gameoff.utils.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.g2d.NinePatch;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.gabo.gameoff.Core;

public class DialogueBox extends Table {
    Table dialogTable;
    private Label textLabel;
    private float timePerChar = 0.02f;
    private float elapsed;
    private boolean finished = true;
    private int visibleChars;
    private String fullText;

    private Runnable onFinish;

    List<String> lines = new ArrayList<>();
    int currentLine = 0;

    NinePatch patch;

    public DialogueBox(Skin skin) {
        super(skin);
        // setDebug(true);
        setFillParent(true);
        setVisible(false);

        dialogTable = new Table(skin);

        // Config label
        textLabel = new Label("", skin);
        textLabel.setWrap(true);
        textLabel.setAlignment(Align.topLeft);

        // Config internal table
        dialogTable.top();
        dialogTable.add(textLabel)
                .width(Core.VIEW_WIDTH - 80f)
                .height(48f)
                .pad(10f)
                .top()
                .left();

        // Background for table
        dialogTable.setBackground(skin.getDrawable("dialogue"));

        float margin = 20f;

        bottom()
                .padBottom(margin)
                .padLeft(margin)
                .padRight(margin)
                .add(dialogTable)
                .expandX()
                .fillX();
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
                setVisible(false);
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

    public void setLines(List<String> dialogueList, Runnable runnable) {
        lines = new ArrayList<>(dialogueList);
        currentLine = 0;
        onFinish = runnable;
        setDialogue(lines.get(currentLine));
        setVisible(true);
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
