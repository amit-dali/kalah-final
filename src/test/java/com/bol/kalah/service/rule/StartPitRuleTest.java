package com.bol.kalah.service.rule;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.util.ReflectionTestUtils;

import com.bol.kalah.constants.GameStatus;
import com.bol.kalah.exception.KalahIllegalMoveException;
import com.bol.kalah.model.Board;
import com.bol.kalah.model.Game;
import com.bol.kalah.model.Pit;
import com.bol.kalah.model.Player;

@RunWith(SpringRunner.class)
@SpringBootTest
public class StartPitRuleTest {

    @Autowired
    Game game;

    @Mock
    Game mockGame;

    @Autowired
    Pit pit;

    private StartPitRule createTestSubject() {
	return new StartPitRule(null);
    }

    @Test
    public void testApply_without_nextProcessor_player1() {
	StartPitRule testSubject = createTestSubject();
	game.setGameStatus(GameStatus.INIT);
	pit.setPitIndex(Board.PLAYER1_HOUSE - 1);
	pit.setPlayerIndex(Player.PLAYER1_INDEX);
	pit.setStoneCount(5);

	testSubject.apply(game, pit);

	assertEquals(GameStatus.PLAYER1_TURN, game.getGameStatus());
    }

    @Test(expected = KalahIllegalMoveException.class)
    public void testApply_without_nextProcessor_player1_invalid_move() {
	StartPitRule testSubject = createTestSubject();
	game.setGameStatus(GameStatus.INIT);
	pit.setPitIndex(Board.PLAYER1_HOUSE + 1);
	pit.setPlayerIndex(Player.PLAYER1_INDEX);
	pit.setStoneCount(5);

	testSubject.apply(game, pit);
    }

    @Test(expected = KalahIllegalMoveException.class)
    public void testApply_without_nextProcessor_player1_without_stone() {
	StartPitRule testSubject = createTestSubject();
	game.setGameStatus(GameStatus.INIT);
	pit.setPitIndex(Board.PLAYER1_HOUSE - 1);
	pit.setPlayerIndex(Player.PLAYER1_INDEX);
	pit.setStoneCount(0);

	testSubject.apply(game, pit);
    }

    @Test
    public void testCheckPlayerTurnRule_player1() {
	StartPitRule testSubject = createTestSubject();
	game.setGameStatus(GameStatus.PLAYER1_TURN);

	ReflectionTestUtils.invokeMethod(testSubject, "checkAndUpdatePlayerTurn", game, pit);

	assertEquals(GameStatus.PLAYER1_TURN, game.getGameStatus());
    }

    @Test
    public void testCheckPlayerTurnRule_player2() {
	StartPitRule testSubject = createTestSubject();
	game.setGameStatus(GameStatus.PLAYER2_TURN);

	ReflectionTestUtils.invokeMethod(testSubject, "checkAndUpdatePlayerTurn", game, pit);

	assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
    }

    @Test
    public void testCheckPlayerTurnRule_game_start_with_player1() {
	StartPitRule testSubject = createTestSubject();
	game.setGameStatus(GameStatus.INIT);
	pit.setPlayerIndex(Player.PLAYER1_INDEX);

	ReflectionTestUtils.invokeMethod(testSubject, "checkAndUpdatePlayerTurn", game, pit);

	assertEquals(GameStatus.PLAYER1_TURN, game.getGameStatus());
    }

    @Test
    public void testCheckPlayerTurnRule_game_start_with_player2() {
	StartPitRule testSubject = createTestSubject();
	game.setGameStatus(GameStatus.INIT);
	pit.setPlayerIndex(Player.PLAYER2_INDEX);

	ReflectionTestUtils.invokeMethod(testSubject, "checkAndUpdatePlayerTurn", game, pit);

	assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
    }

    @Test
    public void testUpdateGameStatus_player1() {
	StartPitRule testSubject = createTestSubject();
	game.setGameStatus(GameStatus.PLAYER1_TURN);
	pit.setPlayerIndex(Player.PLAYER1_INDEX);

	ReflectionTestUtils.invokeMethod(testSubject, "updateGameStatus", game, pit);

	assertEquals(GameStatus.PLAYER1_TURN, game.getGameStatus());
    }

    @Test
    public void testUpdateGameStatus_player2() {
	StartPitRule testSubject = createTestSubject();
	game.setGameStatus(GameStatus.PLAYER2_TURN);
	pit.setPlayerIndex(Player.PLAYER2_INDEX);

	ReflectionTestUtils.invokeMethod(testSubject, "updateGameStatus", game, pit);

	assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
    }
}