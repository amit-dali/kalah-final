package com.bol.kalah.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bol.kalah.model.Game;
import com.bol.kalah.service.GameService;
import com.bol.kalah.validators.BoardValidator;

/**
 * This is the end point of this game.
 *
 * @author AMDALI
 */
@Slf4j
@RestController
@RequestMapping("/api/kalah")
public class GameController {

    @Autowired
    private GameService gameService;

    // TODO: Remove Notes from production code.
    /**
     * Notes: making resource from URI as constants to make sure any change to these
     * values will be at one place. To avoid changes at multiple places. And these
     * constants are specific to this class only, so encapsulated in it. URI's
     */
    private static final String URI_GAMES = "/games";
    private static final String URI_GAMES_PITS = URI_GAMES + "/{gameId}/pits/{pitIndex}";

    // TODO: Remove Notes from production code.
    /**
     * Notes: To avoid changes at multiple places.Candidate for reference at
     * multiple use in future.
     * 
     * Default value for number of stones.
     */
    private static final String DFLT_NUM_OF_STONES = "6";

    /**
     * Rest API end point to initiate new kalah game.
     * 
     * @param numberOfStone, number of stones
     * @return game, current game data
     */
    @PostMapping(value = URI_GAMES, produces = MediaType.APPLICATION_JSON_VALUE)
    public Game initBoard(
	    @RequestParam(name = "stone", defaultValue = DFLT_NUM_OF_STONES, required = false) Integer numberOfStone) {
	log.info("enter initBoard");
	log.debug("numberOfStone={}", numberOfStone);

	return gameService.initGame(numberOfStone);
    }

    /**
     * Rest API end point to play kalah game.
     * 
     * @param gameId, uniquely identify a game
     * @param pitIndex, index of current pit
     * @return game, current game data
     */
    @PutMapping(URI_GAMES_PITS)
    public Game play(@PathVariable String gameId, @PathVariable Integer pitIndex) {
	log.info("enter play");
	log.debug("gameId={}", gameId);
	log.debug("pitIndex={}", pitIndex);

	BoardValidator.validate(pitIndex);

	log.info("exit play");
	return gameService.play(gameId, pitIndex);
    }

}
