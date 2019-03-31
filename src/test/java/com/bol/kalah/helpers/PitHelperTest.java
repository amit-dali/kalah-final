package com.bol.kalah.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bol.kalah.constants.GameStatus;
import com.bol.kalah.model.Board;
import com.bol.kalah.model.Pit;
import com.bol.kalah.model.Player;

import mockit.Deencapsulation;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PitHelperTest {

    @Autowired
    Pit pit;

    @Test
    public void testIsEmptyStart_with_no_stones() {
	pit.setStoneCount(PitHelper.MIN_STONE_COUNT);
	assertTrue(PitHelper.isEmpty(pit));
    }

    @Test
    public void testIsEmptyStart_with_stones() {
	pit.setStoneCount(PitHelper.MIN_STONE_COUNT + 1);
	assertFalse(PitHelper.isEmpty(pit));
    }

    @Ignore
    @Test
    // TODO: add assert
    public void testDetermineStoneCounts() {

    }

    @Ignore
    @Test
    // TODO: add assert
    public void testShouldApplyEmptyPitRule() {

    }

    @Test
    public void testIsDistributable_1() {
	var result = PitHelper.isDistributable(GameStatus.PLAYER2_TURN, Board.PLAYER1_HOUSE);
	assertFalse(result);
    }

    @Test
    public void testIsDistributable_2() {
	var result = PitHelper.isDistributable(GameStatus.PLAYER2_TURN, Board.PLAYER2_HOUSE);
	assertTrue(result);
    }

    @Test
    public void testIsDistributable_3() {
	var result = PitHelper.isDistributable(GameStatus.PLAYER1_TURN, Board.PLAYER1_HOUSE);
	assertTrue(result);
    }

    @Test
    public void testIsDistributable_4() {
	var result = PitHelper.isDistributable(GameStatus.PLAYER1_TURN, Board.PLAYER2_HOUSE);
	assertFalse(result);
    }

    @Test
    public void testIsPlayerOwnerOfPit_player1() {
	boolean result = Deencapsulation.invoke(PitHelper.class, "isPlayerOwnerOfPit", GameStatus.PLAYER1_TURN,
		Player.PLAYER1_INDEX);
	assertTrue(result);
    }

    @Test
    public void testIsPlayerOwnerOfPit_player2() {
	boolean result = Deencapsulation.invoke(PitHelper.class, "isPlayerOwnerOfPit", GameStatus.PLAYER2_TURN,
		Player.PLAYER2_INDEX);
	assertTrue(result);
    }

    @Test
    public void testIsPlayerOwnerOfPit_player1_not() {
	boolean result = Deencapsulation.invoke(PitHelper.class, "isPlayerOwnerOfPit", GameStatus.PLAYER2_TURN,
		Player.PLAYER1_INDEX);
	assertFalse(result);
    }

    @Test
    public void testIsHouse_player1() {
	assertTrue(PitHelper.isHouse(Board.PLAYER1_HOUSE));
    }

    @Test
    public void testIsHouse_player2() {
	assertTrue(PitHelper.isHouse(Board.PLAYER2_HOUSE));
    }

    @Test
    public void testIsHouse_not() {
	assertFalse(PitHelper.isHouse(Board.PLAYER1_HOUSE + 1));
    }

    @Test
    public void testNextPitIndex_1() {
	int result = PitHelper.nextPitIndex(Board.PLAYER1_HOUSE + 1);
	assertEquals(Board.PLAYER1_HOUSE + 2, result);
    }

    @Test
    public void testNextPitIndex_2() {
	Integer result = PitHelper.nextPitIndex(Board.PLAYER2_HOUSE);
	assertEquals(Board.PIT_START_INDEX, result);
    }

    @Test
    // TODO: add negative test case
    public void testIsPlayer1House() {
	assertTrue(PitHelper.isPlayer1House(Player.PLAYER1_INDEX, Board.PLAYER1_HOUSE));
    }

    @Test
    // TODO: add negative test case
    public void testIsPlayer2House() {
	assertTrue(PitHelper.isPlayer2House(Player.PLAYER2_INDEX, Board.PLAYER2_HOUSE));
    }

    @Test
    // TODO: can add one more test case
    public void testGetOppositePitIndex() {
	int result = PitHelper.getOppositePitIndex(Board.PIT_START_INDEX + 1);
	assertEquals((Board.PIT_START_INDEX + Board.PIT_END_INDEX - 1) - (Board.PIT_START_INDEX + 1), result);
    }

    @Test
    public void testIsPitOutOfBoard_1() {
	assertTrue(PitHelper.isPitOutOfBoard(Board.PIT_END_INDEX + 1));
    }

    @Test
    public void testIsPitOutOfBoard_2() {
	assertTrue(PitHelper.isPitOutOfBoard(Board.PIT_START_INDEX - 1));
    }

    @Test
    public void testIsPitOutOfBoard_3() {
	assertFalse(PitHelper.isPitOutOfBoard(Board.PIT_START_INDEX + 1));
    }

    @Ignore
    @Test
    // TODO: add assert and one more test case
    public void testGetPlayersPitStoneCount() {

    }
}