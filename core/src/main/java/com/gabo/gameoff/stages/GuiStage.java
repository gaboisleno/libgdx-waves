package com.gabo.gameoff.stages;

import java.util.List;

import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.gabo.gameoff.Core;
import com.gabo.gameoff.screens.GameScreen;
import com.gabo.gameoff.utils.DialogueBox;

public class GuiStage extends Stage {
    public DialogueBox dialogueBox;
    public float elapsed = 0f;

    public GuiStage(GameScreen game) {
        setViewport(new FitViewport(Core.VIEW_WIDTH, Core.VIEW_HEIGHT));

        OrthographicCamera cam = (OrthographicCamera) getCamera();
        cam.position.set(Core.VIEW_WIDTH / 2f, Core.VIEW_HEIGHT / 2f, 0f);
        cam.zoom = 1.25f;
        cam.update();

        setDebugAll(Core.DEBUG);

        dialogueBox = new DialogueBox(game.skin);
        addActor(dialogueBox);

        addListener(new InputListener() {
            @Override
            public boolean keyDown(InputEvent event, int keycode) {
                if (keycode == Keys.ENTER) {
                    if (dialogueBox.isFinished()) {
                        dialogueBox.nextLine();
                        return true;
                    } else {
                        dialogueBox.skip();
                        return true;
                    }
                }
                return false;
            }
        });
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        elapsed += delta;
    }

    public void setDialogues(List<String> lines) {
        if (elapsed >= 1f && dialogueBox.isAllLinesDone()) {
            dialogueBox.setLines(lines, () -> {
                elapsed = 0f;
            });
        }
    }

    public boolean isDialogueFinish() {
        return dialogueBox.isAllLinesDone();
    }
}
