package com.bol.kalah.service.rule;

import com.bol.kalah.model.Game;
import com.bol.kalah.model.Pit;

/**
 * @author AMDALI
 *
 */
public abstract class KalahRuleProcessor {

    protected KalahRuleProcessor nextProcessor;

    /**
     * @param game
     * @param currentPit
     */
    public abstract void apply(Game game, Pit currentPit);
}
