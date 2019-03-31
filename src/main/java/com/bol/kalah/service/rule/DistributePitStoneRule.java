package com.bol.kalah.service.rule;

import static com.bol.kalah.helpers.PitHelper.MIN_STONE_COUNT;

import com.bol.kalah.helpers.PitHelper;
import com.bol.kalah.model.Game;
import com.bol.kalah.model.Pit;

import lombok.extern.slf4j.Slf4j;

/**
 * Holds logic to distribute to the next pits except for the opponent house.
 *
 * @author AMDALI
 */
@Slf4j
public class DistributePitStoneRule extends KalahRuleProcessor {

    public DistributePitStoneRule(KalahRuleProcessor nextProcessor) {
	this.nextProcessor = nextProcessor;
    }

    @Override
    public void apply(Game game, Pit currentPit) {
	log.info("enter apply");
	log.debug("game = {}", game);
	log.debug("pit = {}", currentPit);

	var stoneToDistribute = currentPit.getStoneCount();
	currentPit.setStoneCount(MIN_STONE_COUNT);

	for (var i = 0; i < stoneToDistribute; i++) {
	    currentPit = game.getBoard().getNextPit(currentPit);

	    if (PitHelper.isDistributable(game.getGameStatus(), currentPit.getPitIndex())) {
		currentPit.setStoneCount(currentPit.getStoneCount() + 1);
	    } else {
		i--;
	    }

	}

	if (nextProcessor != null) {
	    nextProcessor.apply(game, currentPit);
	}

	log.info("exit apply");
    }

}
