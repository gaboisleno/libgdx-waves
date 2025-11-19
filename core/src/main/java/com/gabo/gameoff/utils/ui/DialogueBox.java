package com.gabo.gameoff.utils.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.Align;
import com.gabo.gameoff.Core;

public class DialogueBox extends Table {
    @FunctionalInterface
    public interface LineEndListener {
        void run();
    }

    @FunctionalInterface
    public interface DialogEndListener {
        void run();
    }

    Table dialogTable;
    private Label textLabel;
    private float timePerChar = 0.02f;
    private float elapsed;
    private boolean finished = true;
    private int visibleChars;
    private String fullText;

    private DialogEndListener dialogEndListener;
    private LineEndListener lineEndListener;

    private boolean lineFinished = false;

    List<String> lines = new ArrayList<>();
    int currentLine = 0;

    public DialogueBox(Skin skin) {
        super(skin);
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
            if (dialogEndListener != null) {
                setVisible(false);
                dialogEndListener.run();
                dialogEndListener = null;
            }
            finished = true;
        }
    }

    @Override
    public void act(float delta) {
        super.act(delta);

        if (finished || fullText == null) {
            if (lineEndListener != null && lineFinished) {
                lineEndListener.run();
                lineFinished = false;
            }
            return;
        }

        elapsed += delta;

        int charsToShow = Math.min(fullText.length(), (int) (elapsed / timePerChar));

        if (charsToShow != visibleChars) {
            visibleChars = charsToShow;
            textLabel.setText(fullText.substring(0, charsToShow));
        }

        if (charsToShow >= fullText.length()) {
            this.finished = true;
            lineFinished = true;
        }
    }

    public boolean isFinished() {
        return this.finished;
    }

    public void setLines(List<String> dialogueList, DialogEndListener runnable) {
        lines = new ArrayList<>(dialogueList);
        currentLine = 0;
        dialogEndListener = runnable;
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

    public void addLineEndListener(LineEndListener listener) {
        this.lineEndListener = listener;
    }

}
