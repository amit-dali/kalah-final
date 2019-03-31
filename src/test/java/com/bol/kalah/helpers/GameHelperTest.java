package com.bol.kalah.helpers;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bol.kalah.constants.GameStatus;
import com.bol.kalah.model.Board;
import com.bol.kalah.model.Game;
import com.bol.kalah.model.Pit;
import com.bol.kalah.model.Player;

import mockit.Deencapsulation;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameHelperTest {

    @Autowired
    Game game;

    @Mock
    Game mockedGame;

    @Mock
    Board mockBoard;

    @Autowired
    Pit player1Pit;

    @Autowired
    Pit player2Pit;

    @Test
    public void testIsGameAtInit_at_game_start() {
	game.setGameStatus(GameStatus.INIT);
	assertTrue(GameHelper.isGameAtInit(game));
    }

    @Test
    public void testIsGameAtInit_at_after_start() {
	game.setGameStatus(GameStatus.PLAYER1_TURN);
	assertFalse(GameHelper.isGameAtInit(game));
    }

    @Test
    // TODO: assert to check it is player1
    public void testDetermineResult_player1() {
    }

    @Test
    // TODO: assert to check it is player2
    public void testDetermineResult_player2() {
    }

    @Test
    // TODO: assert to check empty result
    public void testDetermineResult_none() {
    }

    @Test
    public void testIsGameFinished_with_palyer1_winner() {
	when(mockBoard.getPlayer1PitStoneCount()).thenReturn(0);
	when(mockBoard.getPlayer2PitStoneCount()).thenReturn(1);

	assertTrue(GameHelper.isGameFinished(mockBoard));
    }

    @Test
    public void testIsGameFinished_with_palyer2_winner() {
	when(mockBoard.getPlayer1PitStoneCount()).thenReturn(1);
	when(mockBoard.getPlayer2PitStoneCount()).thenReturn(0);

	assertTrue(GameHelper.isGameFinished(mockBoard));
    }

    @Test
    public void testIsGameFinished_game_not_finished() {
	when(mockBoard.getPlayer1PitStoneCount()).thenReturn(1);
	when(mockBoard.getPlayer2PitStoneCount()).thenReturn(1);

	assertFalse(GameHelper.isGameFinished(mockBoard));
    }

    @Test
    public void testDeterminePlayersTurnByIndex_player1_turn() {
	player1Pit.setPlayerIndex(Player.PLAYER1_INDEX);
	assertEquals(GameStatus.PLAYER1_TURN, GameHelper.determinePlayersTurnByIndex(player1Pit));
    }

    @Test
    public void testDeterminePlayersTurnByIndex_player2_turn() {
	player2Pit.setPlayerIndex(Player.PLAYER2_INDEX);

	assertEquals(GameStatus.PLAYER2_TURN, GameHelper.determinePlayersTurnByIndex(player2Pit));
    }

    @Test
    public void testDetermineGameStatus_player1_turn() {
	game.setGameStatus(GameStatus.PLAYER1_TURN);
	player1Pit.setPitIndex(Board.PLAYER1_HOUSE);
	player1Pit.setPlayerIndex(Player.PLAYER1_INDEX);

	assertEquals(GameStatus.PLAYER1_TURN, GameHelper.determineGameStatus(game, player1Pit));
    }

    @Test
    public void testDetermineGameStatus_player2_turn() {
	game.setGameStatus(GameStatus.PLAYER2_TURN);
	player2Pit.setPitIndex(Board.PLAYER2_HOUSE);
	player2Pit.setPlayerIndex(Player.PLAYER2_INDEX);

	assertEquals(GameStatus.PLAYER2_TURN, GameHelper.determineGameStatus(game, player2Pit));
    }

    @Test
    public void testDetermineGameStatus_switch_player_turn() {
	game.setGameStatus(GameStatus.PLAYER1_TURN);
	player1Pit.setPitIndex(Board.PLAYER2_HOUSE);
	player1Pit.setPlayerIndex(Player.PLAYER1_INDEX);

	assertEquals(GameStatus.PLAYER2_TURN, GameHelper.determineGameStatus(game, player1Pit));
    }

    @Test
    public void testIsPlayerInHouse_player1() {
	assertTrue(GameHelper.isPlayerInHouse(Board.PLAYER1_HOUSE));
    }

    @Test
    public void testIsPlayerInHouse_player2() {
	assertTrue(GameHelper.isPlayerInHouse(Board.PLAYER2_HOUSE));
    }

    @Test
    public void testGetUnqId() {
	String id1 = GameHelper.getUnqId();
	String id2 = GameHelper.getUnqId();

	assertNotNull(id1);
	assertNotNull(id2);
	assertNotEquals(id1, id2);
    }

    @Test
    // TODO add test case which returns false
    public void testshouldNotSwitchPlayersTurn_check_player1() {
	boolean result = Deencapsulation.invoke(GameHelper.class, "shouldNotSwitchPlayersTurn", GameStatus.PLAYER1_TURN,
		Board.PLAYER1_HOUSE, Player.PLAYER1_INDEX);
	assertTrue(result);
    }

    @Test
    public void testshouldNotSwitchPlayersTurn_check_player2() {
	boolean result = Deencapsulation.invoke(GameHelper.class, "shouldNotSwitchPlayersTurn", GameStatus.PLAYER2_TURN,
		Board.PLAYER2_HOUSE, Player.PLAYER2_INDEX);
	assertTrue(result);
    }

    @Test
    public void testSwitchPlayersTurn_from_player1_to_player2() {
	var result = Deencapsulation.invoke(GameHelper.class, "switchPlayersTurn", GameStatus.PLAYER1_TURN);
	assertEquals(GameStatus.PLAYER2_TURN, result);
    }

    @Test
    public void testSwitchPlayersTurn_from_player2_to_player1() {
	var result = Deencapsulation.invoke(GameHelper.class, "switchPlayersTurn", GameStatus.PLAYER2_TURN);
	assertEquals(GameStatus.PLAYER1_TURN, result);
    }
}