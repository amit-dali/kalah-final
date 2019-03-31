package com.bol.kalah.validators;

import com.bol.kalah.constants.GameStatus;
import com.bol.kalah.exception.KalahIllegalMoveException;
import com.bol.kalah.helpers.PitHelper;
import com.bol.kalah.model.Board;
import com.bol.kalah.model.Game;
import com.bol.kalah.model.Pit;

import lombok.extern.slf4j.Slf4j;

/**
 * Holds logic to validate pit data
 * 
 * @author AMDALI
 *
 */
@Slf4j
public final class PitValidator {

    private PitValidator() {

    }

    /**
     * @param pit
     */
    public static void validateForEmptyStart(Pit pit) {
	log.info("enter validateForEmptyStart");
	log.debug("pit = {}", pit);

	if (PitHelper.isEmpty(pit)) {
	    throw new KalahIllegalMoveException("Can not start from empty pit");
	}

	log.info("exit validateForEmptyStart");
    }

    /**
     * @param game
     * @param startPit
     */
    public static void validateForTurn(Game game, Pit startPit) {
	log.info("enter validateForTurn");
	log.debug("game = {}", game);
	log.debug("pit = {}", startPit);

	if ((GameStatus.PLAYER1_TURN.equals(game.getGameStatus()) && startPit.getPitIndex() >= Board.PLAYER1_HOUSE)
		|| (GameStatus.PLAYER2_TURN.equals(game.getGameStatus())
			&& startPit.getPitIndex() <= Board.PLAYER1_HOUSE)) {
	    throw new KalahIllegalMoveException("Incorrect pit to play");
	}

	log.info("exit validateForTurn");
    }

}
