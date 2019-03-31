package com.bol.kalah.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bol.kalah.constants.GameStatus;
import com.bol.kalah.exception.KalahIllegalMoveException;
import com.bol.kalah.model.Game;
import com.bol.kalah.model.Pit;

@RunWith(SpringRunner.class)
@SpringBootTest
public class PitValidatorTest {

    @Autowired
    Pit pit;

    @Autowired
    Game game;

    @Test
    public void testValidateForEmptyStartWithNonEmptyPit() {
	pit.setStoneCount(1);
	PitValidator.validateForEmptyStart(pit);
    }

    @Test(expected = KalahIllegalMoveException.class)
    public void testValidateForEmptyStartWithEmptyPit() {
	pit.setStoneCount(0);
	PitValidator.validateForEmptyStart(pit);
    }

    @Test
    public void testValidateForPlayer1ValidTurn() {
	game.setGameStatus(GameStatus.PLAYER1_TURN);
	pit.setPitIndex(2);
	PitValidator.validateForTurn(game, pit);
    }

    @Test
    public void testValidateForPlayer2ValidTurn() {
	game.setGameStatus(GameStatus.PLAYER2_TURN);
	pit.setPitIndex(9);
	PitValidator.validateForTurn(game, pit);
    }

    @Test(expected = KalahIllegalMoveException.class)
    public void testValidateForPlayer1InValidTurn() {
	game.setGameStatus(GameStatus.PLAYER1_TURN);
	pit.setPitIndex(10);
	PitValidator.validateForTurn(game, pit);
    }

    @Test(expected = KalahIllegalMoveException.class)
    public void testValidateForPlayer2InValidTurn() {
	game.setGameStatus(GameStatus.PLAYER2_TURN);
	pit.setPitIndex(3);
	PitValidator.validateForTurn(game, pit);
    }
}