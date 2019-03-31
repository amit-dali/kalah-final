package com.bol.kalah.validators;

import com.bol.kalah.exception.KalahException;
import com.bol.kalah.exception.KalahIllegalMoveException;
import com.bol.kalah.helpers.PitHelper;

import lombok.extern.slf4j.Slf4j;

/**
 * Holds logic to validate game board data
 * 
 * @author AMDALI
 *
 */
@Slf4j
public final class BoardValidator {

    private BoardValidator() {

    }

    /**
     * @param pitIndex
     */
    public static void validate(Integer pitIndex) {
	log.info("enter validate");
	log.debug("pitIndex = {}", pitIndex);

	if (PitHelper.isPitOutOfBoard(pitIndex)) {
	    throw new KalahException("Incorrect pit index");
	} else if (PitHelper.isHouse(pitIndex)) {
	    throw new KalahIllegalMoveException("House stone is not allow to distribute");
	}

	log.info("exit validate");
    }

}
