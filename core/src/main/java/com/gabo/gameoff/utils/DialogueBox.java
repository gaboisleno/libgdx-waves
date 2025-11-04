package com.gabo.gameoff.utils;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
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

    public DialogueBox(Skin skin) {
        super(skin);
        setDebug(Core.DEBUG);
        setFillParent(true);
        setVisible(false);

        dialogTable = new Table(skin);

        // Config label
        textLabel = new Label("", skin);
        textLabel.setWrap(true);
        textLabel.setFontScale(.75f);
        textLabel.setAlignment(Align.topLeft);

        // Config internal table
        dialogTable.top();
        dialogTable.add(textLabel)
                .width(Core.VIEW_WIDTH)
                .height(Core.VIEW_HEIGHT / 3)
                .padBottom(0f);

        // Background for table
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGBA8888);
        pixmap.setColor(0.1f, 0.1f, 0.5f, .8f);
        pixmap.fill();

        Texture texture = new Texture(pixmap);
        pixmap.dispose();

        dialogTable.setBackground(new TextureRegionDrawable(new TextureRegion(texture)));

        bottom().add(dialogTable).expandX();
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
