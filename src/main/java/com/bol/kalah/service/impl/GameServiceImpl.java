package com.bol.kalah.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bol.kalah.model.Game;
import com.bol.kalah.repository.GameMemoryRepository;
import com.bol.kalah.service.GameEngine;
import com.bol.kalah.service.GameService;

import lombok.extern.slf4j.Slf4j;

/**
 * This class works as bridge between controller and game engine.
 *
 * @author AMDALI
 */
@Slf4j
@Service
public class GameServiceImpl implements GameService {

    @Autowired
    private GameMemoryRepository gameMemoryRepository;

    @Autowired
    private GameEngine gameEngine;

    /**
     * This method is responsible to initialize new game
     *
     * @param initialPitStoneCount is the initial number of stones.
     * @return Game
     */
    @Override
    public Game initGame(Integer initialPitStoneCount) {
	log.info("enter initGame");
	log.debug("initialPitStoneCount = {}", initialPitStoneCount);

	log.info("exit initGame");
	return gameMemoryRepository.create(initialPitStoneCount);
    }

    /**
     * This method is responsible for every new move of the stones from a pit.
     *
     * @param gameId   game id
     * @param pitIndex index of the pit
     * @return Game
     */
    @Override
    public Game play(String gameId, Integer pitIndex) {
	log.info("enter play");
	log.debug("gameId = {}", gameId);
	log.debug("pitIndex = {}", gameId);

	var game = gameMemoryRepository.findById(gameId);
	var pit = game.getBoard().getPitByPitIndex(pitIndex);
	gameEngine.play(game, pit);

	log.info("exit play");
	return game;
    }

}
