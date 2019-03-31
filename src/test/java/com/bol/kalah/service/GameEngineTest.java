package com.bol.kalah.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bol.kalah.constants.GameStatus;
import com.bol.kalah.exception.KalahIllegalMoveException;
import com.bol.kalah.helpers.PitHelper;
import com.bol.kalah.model.Board;
import com.bol.kalah.model.Game;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameEngineTest {

    @Autowired
    private GameEngine gameEngine;

    @Test
    public void test_shouldStartWithOwnPit() {

	var game = new Game(6);

	gameEngine.play(game, game.getBoard().getPitByPitIndex(1));

	assertEquals(GameStatus.PLAYER1_TURN, game.getGameStatus());
	assertEquals(Integer.valueOf(1), game.getBoard().getPits().get(Board.PLAYER1_HOUSE).getStoneCount());
	assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(Board.PLAYER2_HOUSE).getStoneCount());
    }

    @Test(expected = KalahIllegalMoveException.class)
    public void test_shouldNotStartWithEmptyPit() {
	var game = new Game(6);
	var pit = game.getBoard().getPits().get(2);
	pit.setStoneCount(0);

	gameEngine.play(game, game.getBoard().getPitByPitIndex(2));
    }

    @Test(expected = KalahIllegalMoveException.class)
    public void test_shouldNotStartWithOpponentPit() {
	var game = new Game(6);
	game.setGameStatus(GameStatus.PLAYER2_TURN);

	gameEngine.play(game, game.getBoard().getPitByPitIndex(2));
    }

    @Test
    public void test_shouldDistributeStoneFromPlayer2PitToPlayer1Pit() {
	var game = new Game(6);

	gameEngine.play(game, game.getBoard().getPitByPitIndex(12));

	assertEquals(GameStatus.PLAYER1_TURN, game.getGameStatus());
	assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(Board.PLAYER1_HOUSE).getStoneCount());
	assertEquals(Integer.valueOf(1), game.getBoard().getPits().get(Board.PLAYER2_HOUSE).getStoneCount());
    }

    @Test
    public void test_shouldDistributeStoneFromPlayer1PitToPlayer2Pit() {
	var game = new Game(6);

	gameEngine.play(game, game.getBoard().getPitByPitIndex(4));

	assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
	assertEquals(Integer.valueOf(1), game.getBoard().getPits().get(Board.PLAYER1_HOUSE).getStoneCount());
	assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(Board.PLAYER2_HOUSE).getStoneCount());
    }

    @Test
    public void test_shouldDistribute13Stone() {
	var game = new Game(6);
	game.getBoard().getPits().get(4).setStoneCount(13);
	game.getBoard().getPits().get(10).setStoneCount(10);

	gameEngine.play(game, game.getBoard().getPitByPitIndex(4));

	assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
	assertEquals(Integer.valueOf(13), game.getBoard().getPits().get(Board.PLAYER1_HOUSE).getStoneCount());
	assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(Board.PLAYER2_HOUSE).getStoneCount());
    }

    @Test
    public void test_shouldIncreaseHouseStoneOnOwnEmptyPit() {
	var game = new Game(6);
	var pit1 = game.getBoard().getPitByPitIndex(1);
	pit1.setStoneCount(2);
	var pit2 = game.getBoard().getPitByPitIndex(3);
	pit2.setStoneCount(0);

	gameEngine.play(game, game.getBoard().getPitByPitIndex(1));

	assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
	assertEquals(Integer.valueOf(7), game.getBoard().getPits().get(Board.PLAYER1_HOUSE).getStoneCount());
	assertEquals(Integer.valueOf(0), game.getBoard().getPits().get(Board.PLAYER2_HOUSE).getStoneCount());
    }

    @Test
    public void test_shouldChangeGameToPlayerTurn1() {
	var game = new Game(6);

	gameEngine.play(game, game.getBoard().getPitByPitIndex(1));

	assertEquals(GameStatus.PLAYER1_TURN, game.getGameStatus());
    }

    @Test
    public void test_shouldChangeGameToPlayerTurn2() {
	Game game = new Game(6);

	gameEngine.play(game, game.getBoard().getPitByPitIndex(2));

	assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
    }

    @Test
    public void test_shouldChangeGameToPlayerTurn2Again() {
	var game = new Game(6);
	var pit = game.getBoard().getPits().get(8);
	pit.setStoneCount(6);

	gameEngine.play(game, game.getBoard().getPitByPitIndex(8));

	assertEquals(GameStatus.PLAYER2_TURN, game.getGameStatus());
    }

    @Test
    public void test_shouldGameOver() {
	var game = new Game(6);

	for (Integer key : game.getBoard().getPits().keySet()) {
	    var pit = game.getBoard().getPits().get(key);

	    if (!PitHelper.isHouse(pit.getPitIndex())) {
		pit.setStoneCount(0);
	    }
	}

	game.getBoard().getPits().get(6).setStoneCount(1);

	gameEngine.play(game, game.getBoard().getPitByPitIndex(6));

	assertEquals(GameStatus.FINISHED, game.getGameStatus());
	assertEquals(game.getWinner(), game.getPlayer1());
    }

    @Test
    public void test_shouldPlayer1Win() {
	var game = new Game(6);

	for (Integer key : game.getBoard().getPits().keySet()) {
	    var pit = game.getBoard().getPits().get(key);
	    if (!PitHelper.isHouse(pit.getPitIndex())) {
		pit.setStoneCount(0);
	    }
	}

	var lastPit = game.getBoard().getPits().get(6);
	lastPit.setStoneCount(1);

	gameEngine.play(game, game.getBoard().getPitByPitIndex(6));

	assertEquals(GameStatus.FINISHED, game.getGameStatus());
	assertEquals(game.getWinner(), game.getPlayer1());
    }

    @Test
    public void test_shouldPlayer2Win() {
	var game = new Game(6);

	for (Integer key : game.getBoard().getPits().keySet()) {
	    var pit = game.getBoard().getPits().get(key);
	    if (!PitHelper.isHouse(pit.getPitIndex())) {
		pit.setStoneCount(0);
	    }
	}

	game.getBoard().getPits().get(13).setStoneCount(1);

	gameEngine.play(game, game.getBoard().getPitByPitIndex(13));

	assertEquals(GameStatus.FINISHED, game.getGameStatus());
	assertEquals(game.getWinner(), game.getPlayer2());
    }

    @Test
    public void test_shouldDraw() {
	var game = new Game(6);

	for (Integer key : game.getBoard().getPits().keySet()) {
	    var pit = game.getBoard().getPits().get(key);
	    if (!PitHelper.isHouse(pit.getPitIndex())) {
		pit.setStoneCount(0);
	    }
	}

	game.getBoard().getPits().get(6).setStoneCount(1);
	game.getBoard().getPits().get(14).setStoneCount(1);

	gameEngine.play(game, game.getBoard().getPitByPitIndex(6));

	assertEquals(GameStatus.FINISHED, game.getGameStatus());
	assertNull(game.getWinner());
    }

}
