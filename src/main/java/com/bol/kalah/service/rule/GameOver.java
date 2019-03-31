package com.bol.kalah.service.rule;

import com.bol.kalah.constants.GameStatus;
import com.bol.kalah.helpers.GameHelper;
import com.bol.kalah.helpers.PitHelper;
import com.bol.kalah.model.Board;
import com.bol.kalah.model.Game;
import com.bol.kalah.model.Pit;

import lombok.extern.slf4j.Slf4j;

/**
 * Hold logic to verify end of the game.
 *
 * @author AMDALI
 */
@Slf4j
public class GameOver extends KalahRuleProcessor {

    public GameOver(KalahRuleProcessor nextProcessor) {
	this.nextProcessor = nextProcessor;
    }

    @Override
    public void apply(Game game, Pit currentPit) {
	log.info("enter apply");
	log.debug("game = {}", game);
	log.debug("pit = {}", currentPit);

	if (GameHelper.isGameFinished(game.getBoard())) {

	    game.setGameStatus(GameStatus.FINISHED);

	    updateHouse(game.getBoard());

	    updateWinner(game);

	    resetBoard(game);
	}

	if (nextProcessor != null) {
	    nextProcessor.apply(game, currentPit);
	}

	log.info("exit apply");
    }

    /**
     * @param board
     */
    private void updateHouse(Board board) {
	log.info("enter updateHouse");
	log.debug("board = {}", board);

	var house1 = board.getPits().get(Board.PLAYER1_HOUSE);
	var stoneCounts1 = PitHelper.determineStoneCounts(board, Board.PLAYER1_HOUSE);
	house1.setStoneCount(stoneCounts1);

	var house2 = board.getPits().get(Board.PLAYER2_HOUSE);
	var stoneCounts2 = PitHelper.determineStoneCounts(board, Board.PLAYER2_HOUSE);
	house2.setStoneCount(stoneCounts2);

	log.info("exit updateHouse");
    }

    /**
     * @param game
     */
    private void updateWinner(Game game) {
	log.info("enter updateWinner");
	log.debug("game = {}", game);

	var winner = GameHelper.determineResult(game);
	game.setWinner(winner.orElse(null));

	log.info("exit updateWinner");
    }

    /**
     * @param game
     */
    private void resetBoard(Game game) {
	log.info("enter resetBoard");
	log.debug("game = {}", game);

	game.getBoard().getPits().keySet().stream().filter(playerHouse -> !GameHelper.isPlayerInHouse(playerHouse))
		.forEach(key -> game.getBoard().getPits().get(key).setStoneCount(PitHelper.MIN_STONE_COUNT));

	log.info("exit resetBoard");
    }

}
