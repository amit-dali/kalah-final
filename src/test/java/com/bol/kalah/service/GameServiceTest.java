package com.bol.kalah.service;

import static org.junit.Assert.assertEquals;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;

import com.bol.kalah.constants.GameStatus;
import com.bol.kalah.model.Board;
import com.bol.kalah.model.Game;
import com.bol.kalah.model.Pit;
import com.bol.kalah.model.Player;
import com.bol.kalah.repository.GameMemoryRepository;

@RunWith(SpringRunner.class)
@SpringBootTest

public class GameServiceTest {

    @MockBean
    private GameMemoryRepository gameRepository;

    @MockBean
    private GameEngine gameEngine;

    @Autowired
    private GameService gameService;

    private Player getPlayer1() {
	return new Player(Player.PLAYER1_INDEX, "Player 1");
    }

    private Player getPlayer2() {
	return new Player(Player.PLAYER2_INDEX, "Player 2");
    }

    private Map<Integer, Pit> initPit() {
	Map<Integer, Pit> pits = new HashMap<>();

	for (int i = Board.PIT_START_INDEX; i < Board.PLAYER1_HOUSE; i++) {
	    Pit pit = new Pit(i, Board.INITIAL_STONE_ON_PIT, Player.PLAYER1_INDEX);
	    pits.put(i, pit);
	}

	var house1 = new Pit(Board.PLAYER1_HOUSE, Board.INITIAL_STONE_ON_HOUSE, Player.PLAYER1_INDEX);
	pits.put(Board.PLAYER1_HOUSE, house1);

	for (int i = Board.PLAYER1_HOUSE + 1; i < Board.PLAYER2_HOUSE; i++) {
	    Pit pit = new Pit(i, Board.INITIAL_STONE_ON_PIT, Player.PLAYER2_INDEX);
	    pits.put(i, pit);
	}

	var house2 = new Pit(Board.PLAYER2_HOUSE, Board.INITIAL_STONE_ON_HOUSE, Player.PLAYER2_INDEX);
	pits.put(Board.PLAYER2_HOUSE, house2);

	return pits;
    }

    @Test
    public void testInitGame() {

	var board = new Board();
	board.setPits(initPit());

	var game = new Game(Board.INITIAL_STONE_ON_PIT);
	game.setId(UUID.randomUUID().toString());
	game.setBoard(board);

	BDDMockito.given(gameRepository.create(BDDMockito.any())).willReturn(game);

	var mockGame = gameService.initGame(6);

	assertEquals(game, mockGame);
    }

    @Test
    public void testPlay() {

	var board = new Board();
	board.setPits(initPit());

	var id = UUID.randomUUID().toString();
	var game = new Game(Board.INITIAL_STONE_ON_PIT);
	game.setGameStatus(GameStatus.INIT);
	game.setId(id);
	game.setBoard(board);

	BDDMockito.given(gameRepository.findById(id)).willReturn(game);

	game.setGameStatus(GameStatus.PLAYER1_TURN);
	BDDMockito.given(gameRepository.create(BDDMockito.any())).willReturn(game);

	var mockGame = gameService.play(game.getId(), game.getBoard().getPits().get(1).getPitIndex());

	assertEquals(GameStatus.PLAYER1_TURN, mockGame.getGameStatus());
    }

}
