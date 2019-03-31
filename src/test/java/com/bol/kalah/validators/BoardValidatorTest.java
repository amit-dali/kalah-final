package com.bol.kalah.validators;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.bol.kalah.exception.KalahException;
import com.bol.kalah.exception.KalahIllegalMoveException;
import com.bol.kalah.model.Board;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BoardValidatorTest {

    @Test
    public void testValidate_valid_pit_move() {
	BoardValidator.validate(Board.PIT_START_INDEX + 1);
    }

    @Test(expected = KalahException.class)
    public void testValidate_invalid_pit_move() {
	BoardValidator.validate(Board.PIT_END_INDEX + 1);
    }

    @Test(expected = KalahIllegalMoveException.class)
    public void testValidate_pit_move_to_player1_house() {
	BoardValidator.validate(Board.PLAYER1_HOUSE);
    }

    @Test(expected = KalahIllegalMoveException.class)
    public void testValidate_pit_move_to_player2_house() {
	BoardValidator.validate(Board.PLAYER2_HOUSE);
    }
}