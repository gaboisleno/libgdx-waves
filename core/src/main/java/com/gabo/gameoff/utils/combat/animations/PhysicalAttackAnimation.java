package com.gabo.gameoff.utils.combat.animations;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.gabo.gameoff.assets.Atlases;
import com.gabo.gameoff.entities.BaseUnit;
import com.gabo.gameoff.utils.Effect;
import com.gabo.gameoff.utils.combat.CombatUI;
import com.gabo.gameoff.utils.combat.Turn;

public class PhysicalAttackAnimation implements AnimationStrategy {
	@Override
	public void animate(Turn turn, Runnable onComplete, CombatUI combatUI) {

		Vector2 pos = new Vector2();
		BaseUnit unit = turn.getAttacked();
		unit.image
				.localToStageCoordinates(pos.set((unit.image.getWidth() / 2) - 8, (unit.image.getHeight() / 2) - 8));

		TextureAtlas atlas = combatUI.assets.getAtlas(Atlases.hit_effect);

		Gdx.app.postRunnable(() -> {
			Effect hit = new Effect(atlas, pos.x, pos.y);
			combatUI.stage.addActor(hit);
		});

		if (unit != null) {
			unit.image.addAction(
					Actions.sequence(
							Actions.repeat(5,
									Actions.sequence(
											Actions.alpha(0),
											Actions.delay(.1f),
											Actions.alpha(1),
											Actions.delay(.1f))),
							Actions.delay(.2f),
							Actions.run(onComplete)));
		}
	}
}
