package com.bol.kalah.model;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.IntStream;

import org.springframework.stereotype.Component;

import com.bol.kalah.exception.KalahException;
import com.bol.kalah.helpers.PitHelper;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 *
 * @author AMDALI
 */
@Slf4j
@Setter
@Getter
@NoArgsConstructor
@ToString
@Component
public class Board {

    public static final Integer PIT_START_INDEX = 1;
    public static final Integer PIT_END_INDEX = 14;
    public static final Integer PLAYER1_HOUSE = 7;
    public static final Integer PLAYER2_HOUSE = 14;
    public static final Integer INITIAL_STONE_ON_PIT = 6;
    public static final Integer INITIAL_STONE_ON_HOUSE = 0;

    private Map<Integer, Pit> pits;

    public Board(Integer initialStoneOnPit, Player player1, Player player2) {
	initPit(initialStoneOnPit, player1, player2);
    }

    /**
     * @param playerIndex
     * @return
     */
    public Pit getPlayerHouse(Integer playerIndex) {
	log.info("enter getPlayerHouse");
	log.debug("playerIndex={}", playerIndex);

	switch (playerIndex) {
	case 0:
	    return pits.get(Board.PLAYER1_HOUSE);
	case 1:
	    return pits.get(Board.PLAYER2_HOUSE);
	default:
	    throw new KalahException("playerIndex is not correct");
	}
    }

    /**
     * @param pitIndex
     * @return
     */
    public Pit getPitByPitIndex(Integer pitIndex) {
	return pits.get(pitIndex);
    }

    /**
     * @param pit
     * @return
     */
    public Pit getNextPit(Pit pit) {
	return pits.get(PitHelper.nextPitIndex(pit.getPitIndex()));
    }

    /**
     * @param pit
     * @return
     */
    public Pit getOppositePit(Pit pit) {
	return pits.get(PitHelper.getOppositePitIndex(pit.getPitIndex()));
    }

    public Integer getPlayer1PitStoneCount() {
	return IntStream.range(Board.PIT_START_INDEX, Board.PLAYER1_HOUSE)
		.map(index -> getPits().get(index).getStoneCount()).sum();
    }

    public Integer getPlayer2PitStoneCount() {
	return IntStream.range(PLAYER1_HOUSE + 1, Board.PLAYER2_HOUSE)
		.map(index -> getPits().get(index).getStoneCount()).sum();
    }

    private void initPit(Integer initialStoneOnPit, Player player1, Player player2) {
	log.info("enter initPit");
	log.debug("initialStoneOnPit={}", initialStoneOnPit);
	log.debug("player1={}", player1);
	log.debug("player2={}", player2);

	pits = new ConcurrentHashMap<>();

	IntStream.range(Board.PIT_START_INDEX, Board.PLAYER1_HOUSE)
		.forEachOrdered(index -> pits.put(index, new Pit(index, initialStoneOnPit, player1.getPlayerIndex())));

	Pit house1 = new Pit(Board.PLAYER1_HOUSE, Board.INITIAL_STONE_ON_HOUSE, player1.getPlayerIndex());
	pits.put(Board.PLAYER1_HOUSE, house1);

	IntStream.range(Board.PLAYER1_HOUSE + 1, Board.PLAYER2_HOUSE)
		.forEachOrdered(index -> pits.put(index, new Pit(index, initialStoneOnPit, player2.getPlayerIndex())));

	Pit house2 = new Pit(Board.PLAYER2_HOUSE, Board.INITIAL_STONE_ON_HOUSE, player2.getPlayerIndex());
	pits.put(Board.PLAYER2_HOUSE, house2);

	log.info("exit initPit");
    }

}
