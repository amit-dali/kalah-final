package com.bol.kalah.service.rule;

import static com.bol.kalah.helpers.PitHelper.MIN_STONE_COUNT;

import com.bol.kalah.helpers.GameHelper;
import com.bol.kalah.helpers.PitHelper;
import com.bol.kalah.model.Game;
import com.bol.kalah.model.Pit;

import lombok.extern.slf4j.Slf4j;

/**
 * Holds logic to verify the last stone placing.
 *
 * @author AMDALI
 */
@Slf4j
public class EndPitRule extends KalahRuleProcessor {

    public EndPitRule(KalahRuleProcessor nextProcessor) {
	this.nextProcessor = nextProcessor;
    }

    @Override
    public void apply(Game game, Pit endPit) {
	log.info("enter apply");
	log.debug("game = {}", game);
	log.debug("pit = {}", endPit);

	lastEmptyPitRule(game, endPit);

	nextPlayerTurnRule(game, endPit);

	if (nextProcessor != null) {
	    nextProcessor.apply(game, endPit);
	}

	log.info("exit apply");
    }

    /**
     * @param game
     * @param endPit
     */
    private void lastEmptyPitRule(Game game, Pit endPit) {
	log.info("enter lastEmptyPitRule");
	log.debug("game = {}", game);
	log.debug("pit = {}", endPit);

	if (PitHelper.shouldApplyEmptyPitRule(game, endPit)) {
	    var oppositePit = game.getBoard().getOppositePit(endPit);
	    var house = game.getBoard().getPlayerHouse(endPit.getPlayerIndex());
	    var totalStoneCount = house.getStoneCount() + oppositePit.getStoneCount() + endPit.getStoneCount();

	    house.setStoneCount(totalStoneCount);
	    oppositePit.setStoneCount(MIN_STONE_COUNT);
	    endPit.setStoneCount(MIN_STONE_COUNT);
	}

	log.info("exit lastEmptyPitRule");
    }

    /**
     * @param game
     * @param endPit
     */
    private void nextPlayerTurnRule(Game game, Pit endPit) {
	log.info("enter nextPlayerTurnRue");
	log.debug("game = {}", game);
	log.debug("pit = {}", endPit);

	var gameStatus = GameHelper.determineGameStatus(game, endPit);
	game.setGameStatus(gameStatus);
	game.updateTime();

	log.info("exit nextPlayerTurnRue");
    }
}
