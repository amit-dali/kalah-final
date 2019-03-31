package com.bol.kalah.service.rule;

import com.bol.kalah.helpers.GameHelper;
import com.bol.kalah.model.Game;
import com.bol.kalah.model.Pit;
import com.bol.kalah.validators.PitValidator;

import lombok.extern.slf4j.Slf4j;

/**
 * Holds logic to verify the distributing stones rule at start of game.
 *
 * @author AMDALI
 */
@Slf4j
public class StartPitRule extends KalahRuleProcessor {

    public StartPitRule(KalahRuleProcessor nextProcessor) {
	this.nextProcessor = nextProcessor;
    }

    @Override
    public void apply(Game game, Pit startPit) {
	log.info("enter apply");
	log.debug("game = {}", game);
	log.debug("pit = {}", startPit);

	checkAndUpdatePlayerTurn(game, startPit);

	PitValidator.validateForTurn(game, startPit);

	PitValidator.validateForEmptyStart(startPit);

	if (nextProcessor != null) {
	    nextProcessor.apply(game, startPit);
	}

	log.info("exit apply");
    }

    /**
     * @param game
     * @param startPit
     */
    private void checkAndUpdatePlayerTurn(Game game, Pit startPit) {
	log.info("enter checkAndUpdatePlayerTurn");
	log.debug("game = {}", game);
	log.debug("pit = {}", startPit);

	if (GameHelper.isGameAtInit(game)) {
	    updateGameStatus(game, startPit);
	}

	log.info("exit checkAndUpdatePlayerTurn");
    }

    /**
     * @param game
     * @param startPit
     */
    private void updateGameStatus(Game game, Pit startPit) {
	log.info("enter updateGameStatus");
	log.debug("game = {}", game);
	log.debug("pit = {}", startPit);

	var playersTurn = GameHelper.determinePlayersTurnByIndex(startPit);
	game.setGameStatus(playersTurn);

	log.info("exit updateGameStatus");
    }

}
