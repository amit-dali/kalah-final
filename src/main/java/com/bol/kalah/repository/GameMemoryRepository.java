package com.bol.kalah.repository;

import java.util.concurrent.ConcurrentHashMap;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.bol.kalah.exception.KalahException;
import com.bol.kalah.helpers.GameHelper;
import com.bol.kalah.model.Game;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represent the storage of the game where store the game object in a
 * map & get the map by id.
 *
 * @author AMDALI
 */
@Slf4j
@Component
public class GameMemoryRepository {

    private final ConcurrentHashMap<String, Game> gameMap = new ConcurrentHashMap<>();
    private static final int OLD_GAME_CLEAR_INTERVAL = 300000;

    /**
     * @param initialPitStoneCount is the number of the stone of a pit.
     * @return Game, initializes new game and returns it's object with loaded
     *         initial data.
     */
    public Game create(Integer initialPitStoneCount) {
	log.info("enter create");
	log.debug("initialPitStoneCount = {}", initialPitStoneCount);

	var id = GameHelper.getUnqId();
	var game = new Game(initialPitStoneCount);
	game.setId(id);
	gameMap.put(id, game);

	log.info("exit create");
	return game;
    }

    /**
     * @param gameId
     * @return Game, retrieves game based on the unique game id.
     */
    public Game findById(String gameId) {
	log.info("enter findById");
	log.debug("gameId = {}", gameId);

	var game = gameMap.get(gameId);
	if (game == null) {
	    throw new KalahException("Game not found for the game id: " + gameId);
	}

	log.info("exit findById");
	return game;
    }

    /**
     * After Every 5 minutes(300000 seconds) interval this method tries to find the
     * 60 minutes old game and remove them from map. So, if there is no activity on
     * a game last 60 minutes, It will destroy.
     */
    @Scheduled(fixedRate = OLD_GAME_CLEAR_INTERVAL)
    private void deleteOldGame() {
	log.debug("enter deleteOldGame");

	for (var entry : gameMap.entrySet()) {
	    long diff = System.currentTimeMillis() - entry.getValue().getUpdateAt();
	    long diffMinutes = diff / (60 * 1000) % 60;
	    if (diffMinutes > 60) {
		gameMap.remove(entry.getKey());
	    }
	}

	log.debug("exit deleteOldGame");
    }
}
