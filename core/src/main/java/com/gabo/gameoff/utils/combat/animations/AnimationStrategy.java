package com.gabo.gameoff.utils.combat.animations;

import com.gabo.gameoff.utils.combat.CombatUI;
import com.gabo.gameoff.utils.combat.Turn;

public interface AnimationStrategy {
    void animate(Turn turn, Runnable onComplete, CombatUI combatUI);
}
