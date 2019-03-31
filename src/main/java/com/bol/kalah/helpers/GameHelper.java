package com.bol.kalah.helpers;

import java.util.Optional;
import java.util.UUID;

import com.bol.kalah.constants.GameStatus;
import com.bol.kalah.model.Board;
import com.bol.kalah.model.Game;
import com.bol.kalah.model.Pit;
import com.bol.kalah.model.Player;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO: Remove these notes from production release code.
 * Why I have GameHelper class with generalized static methods;
 * 1) To increase code re-usability and avoid code duplication.
 * 2) Most of the generalized public methods follow single responsibility principle.
 * 3) This class is restricted from extending and to construct an object of this type.
 *    This prevents code hack in future by subsystems or new changes,to keep existing
 *    working functionality in-tact.
 */

/**
 * Hold utility methods for Game related generalized logic.
 * 
 * @author AMDALI
 *
 */
@Slf4j
public final class GameHelper {

    private GameHelper() {

    }

    /**
     * @param game, current game data
     * @return boolean, whether the current game is in initial stage
     */
    public static boolean isGameAtInit(Game game) {
	log.info("enter isGameAtInit");
	log.debug("game={}", game);

	return GameStatus.INIT.equals(game.getGameStatus());
    }

    /**
     * @param game, current game data
     * @return player, player holding maximum stones
     */
    public static Optional<Player> determineResult(Game game) {
	log.info("enter determineResult");
	log.debug("game={}", game);

	var house1StoneCount = game.getBoard().getPits().get(Board.PLAYER1_HOUSE).getStoneCount();
	var house2StoneCount = game.getBoard().getPits().get(Board.PLAYER2_HOUSE).getStoneCount();

	if (house1StoneCount > house2StoneCount) {
	    return Optional.of(game.getPlayer1());
	} else if (house1StoneCount < house2StoneCount) {
	    return Optional.of(game.getPlayer2());
	} else {
	    return Optional.empty();
	}
    }

    /**
     * @param board, current board
     * @return boolean, whether game is finished or in progress
     */
    public static boolean isGameFinished(Board board) {
	log.info("enter isGameFinished");
	log.debug("board={}", board);

	return Board.INITIAL_STONE_ON_HOUSE.equals(board.getPlayer1PitStoneCount())
		|| Board.INITIAL_STONE_ON_HOUSE.equals(board.getPlayer2PitStoneCount());
    }

    /**
     * @param pit, current pit
     * @return gameStatus, find out turn of which player
     */
    public static GameStatus determinePlayersTurnByIndex(Pit pit) {
	log.info("enter determinePlayersTurnByIndex");
	log.debug("pit={}", pit);

	return Player.PLAYER1_INDEX.equals(pit.getPlayerIndex()) ? GameStatus.PLAYER1_TURN : GameStatus.PLAYER2_TURN;
    }

    /**
     * @param game
     * @param endPit
     * @return game status, find out correct players turn
     */
    public static GameStatus determineGameStatus(Game game, Pit endPit) {
	log.info("enter determineGameStatus");
	log.debug("game={}", game);
	log.debug("endPit={}", endPit);

	var gameStatus = game.getGameStatus();

	if (shouldNotSwitchPlayersTurn(gameStatus, endPit.getPitIndex(), endPit.getPlayerIndex())) {
	    return gameStatus;
	} else {
	    return switchPlayersTurn(gameStatus);
	}
    }

    /**
     * @param playerHouse
     * @return boolean, whether the pit is a valid one of the house
     */
    public static boolean isPlayerInHouse(Integer playerHouse) {
	log.info("enter isPlayerInHouse");
	log.debug("playerHouse={}", playerHouse);

	return Board.PLAYER1_HOUSE.equals(playerHouse) || Board.PLAYER2_HOUSE.equals(playerHouse);
    }

    /**
     * @return string, unique auto generated id.
     */
    public static String getUnqId() {
	log.info("enter getUnqId");

	return UUID.randomUUID().toString();
    }

    /**
     * @param gameStatus, status of game
     * @param pitIndex, index of pit
     * @param playerIndex, index of player
     * @return boolean, whether need to change players turn
     */
    private static boolean shouldNotSwitchPlayersTurn(GameStatus gameStatus, Integer pitIndex, Integer playerIndex) {
	log.info("enter shouldNotSwitchPlayersTurn");
	log.debug("gameStatus={}", gameStatus);
	log.debug("pitIndex={}", pitIndex);
	log.debug("playerIndex={}", playerIndex);

	return (PitHelper.isPlayer1House(playerIndex, pitIndex) && GameStatus.PLAYER1_TURN.equals(gameStatus))
		|| (PitHelper.isPlayer2House(playerIndex, pitIndex) && GameStatus.PLAYER2_TURN.equals(gameStatus));
    }

    /**
     * @param gameStatus, status of game
     * @return gameStatus, toggle players turn
     */
    private static GameStatus switchPlayersTurn(GameStatus gameStatus) {
	log.info("enter switchPlayersTurn");
	log.debug("gameStatus={}", gameStatus);

	return gameStatus == GameStatus.PLAYER1_TURN ? GameStatus.PLAYER2_TURN : GameStatus.PLAYER1_TURN;
    }

}
