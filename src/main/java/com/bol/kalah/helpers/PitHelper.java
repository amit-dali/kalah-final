package com.bol.kalah.helpers;

import com.bol.kalah.constants.GameStatus;
import com.bol.kalah.model.Board;
import com.bol.kalah.model.Game;
import com.bol.kalah.model.Pit;
import com.bol.kalah.model.Player;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO: Remove these notes from production release code.
 * Why I have PitHelper class with pit related generalized static methods;
 * 1) To increase code re-usability and avoid code duplication.
 * 2) Most of the generalized public methods follow single responsibility principle.
 * 3) This class is restricted from extending and to construct an object of this type.
 *    This prevents code hack in future by subsystems or new changes,to keep existing
 *    working functionality in-tact.
 */
/**
 * Holds logic to support pit related generalized logic.
 * 
 * @author AMDALI
 *
 */
@Slf4j
public class PitHelper {

    /**
     * Default minimum stones in a pit
     */
    public static final Integer MIN_STONE_COUNT = 0;

    private PitHelper() {

    }

    /**
     * @param pit
     * @return boolean, whether the pit is empty or not.
     */
    public static boolean isEmpty(Pit pit) {
	log.info("enter isEmpty");
	log.debug("pit = {}", pit);

	log.info("exit isEmpty");
	return MIN_STONE_COUNT.equals(pit.getStoneCount());
    }

    /**
     * @param board
     * @param playersHouse
     * @return Integer, number of total stones hold by a player.
     */
    public static Integer determineStoneCounts(Board board, Integer playersHouse) {
	log.info("enter determineStoneCounts");
	log.debug("pit={}", board);
	log.debug("playersHouse={}", playersHouse);

	var house = board.getPits().get(playersHouse);
	var playersPitStoneCount = getPlayersPitStoneCount(board, playersHouse);

	log.info("exit determineStoneCounts");
	return house.getStoneCount() + playersPitStoneCount;
    }

    /**
     * @param game
     * @param endPit
     * @return boolean, whether pit is eligible to apply empty pit rule.
     */
    public static boolean shouldApplyEmptyPitRule(Game game, Pit endPit) {
	log.info("enter shouldApplyEmptyPitRule");
	log.debug("games={}", game);
	log.debug("pit={}", endPit);

	return !isHouse(endPit.getPitIndex()) && isPlayerOwnerOfPit(game.getGameStatus(), endPit.getPlayerIndex())
		&& endPit.getStoneCount().equals(1)
		&& game.getBoard().getOppositePit(endPit).getStoneCount() > MIN_STONE_COUNT;
    }

    /**
     * @param gameStatus
     * @param pitIndex
     * @return boolean
     */
    public static boolean isDistributable(GameStatus gameStatus, Integer pitIndex) {
	log.info("enter isDistributable");
	log.debug("gameStatus={}", gameStatus);
	log.debug("pitIndex={}", pitIndex);

	return (!GameStatus.PLAYER1_TURN.equals(gameStatus) || !Board.PLAYER2_HOUSE.equals(pitIndex))
		&& (!GameStatus.PLAYER2_TURN.equals(gameStatus) || !Board.PLAYER1_HOUSE.equals(pitIndex));
    }

    /**
     * @param gameStatus
     * @param playerIndex
     * @return boolean, whether it is current player's pit.
     */
    private static boolean isPlayerOwnerOfPit(GameStatus gameStatus, Integer playerIndex) {
	log.info("enter isPlayerOwnerOfPit");
	log.debug("gameStatus={}", gameStatus);
	log.debug("playerIndex={}", playerIndex);

	return (GameStatus.PLAYER1_TURN.equals(gameStatus) && Player.PLAYER1_INDEX.equals(playerIndex))
		|| (gameStatus.equals(GameStatus.PLAYER2_TURN) && Player.PLAYER2_INDEX.equals(playerIndex));
    }

    /**
     * @param pitIndex
     * @return boolean, whether it is is house of any of the player.
     */
    public static boolean isHouse(Integer pitIndex) {
	log.info("enter isHouse");
	log.debug("pitIndex={}", pitIndex);

	return Board.PLAYER1_HOUSE.equals(pitIndex) || Board.PLAYER2_HOUSE.equals(pitIndex);
    }

    /**
     * @param currentPitIndex
     * @return pit index, fetch next pit's index.
     */
    public static Integer nextPitIndex(Integer currentPitIndex) {
	log.info("enter nextPitIndex");
	log.debug("currentPitIndex={}", currentPitIndex);

	Integer nextIndex = currentPitIndex + 1;
	if (nextIndex > Board.PLAYER2_HOUSE) {
	    return 1;
	}

	log.info("exit nextPitIndex");
	return nextIndex;
    }

    /**
     * @param playerIndex
     * @param pitIndex
     * @return boolean, whether it is player1's house
     */
    public static boolean isPlayer1House(Integer playerIndex, Integer pitIndex) {
	log.info("enter isPlayer1House");
	log.debug("playerIndex={}", playerIndex);
	log.debug("pitIndex={}", pitIndex);

	return Player.PLAYER1_INDEX.equals(playerIndex) && Board.PLAYER1_HOUSE.equals(pitIndex);

    }

    /**
     * @param playerIndex
     * @param pitIndex
     * @return boolean, whether it is player2's house.
     */
    public static boolean isPlayer2House(Integer playerIndex, Integer pitIndex) {
	log.info("enter isPlayer2House");
	log.debug("playerIndex={}", playerIndex);
	log.debug("pitIndex={}", pitIndex);

	return Player.PLAYER2_INDEX.equals(playerIndex) && Board.PLAYER2_HOUSE.equals(pitIndex);
    }

    /**
     * @param currentPitIndex
     * @return pit index, index of other players pit index.
     */
    public static Integer getOppositePitIndex(Integer currentPitIndex) {
	log.info("enter getOppositePitIndex");
	log.debug("currentPitIndex={}", currentPitIndex);

	return (Board.PIT_START_INDEX + Board.PIT_END_INDEX - 1) - currentPitIndex;
    }

    /**
     * @param pitIndex
     * @return boolean, whether pit is valid or not.
     */
    public static boolean isPitOutOfBoard(Integer pitIndex) {
	log.info("enter isPitOutOfBoard");
	log.debug("pitIndex={}", pitIndex);

	return pitIndex > Board.PIT_END_INDEX || pitIndex < Board.PIT_START_INDEX;
    }

    /**
     * @param board
     * @param playersHouse
     * @return number of stones, based on player find stones on a pit of that
     *         player.
     */
    private static Integer getPlayersPitStoneCount(Board board, Integer playersHouse) {
	log.info("enter getPlayersPitStoneCount");
	log.debug("pit={}", board);
	log.debug("playersHouse={}", playersHouse);

	return Board.PLAYER1_HOUSE.equals(playersHouse) ? board.getPlayer1PitStoneCount()
		: board.getPlayer2PitStoneCount();
    }

}
