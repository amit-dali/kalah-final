package com.bol.kalah.service;

import com.bol.kalah.model.Game;

/**
 * @author AMDALI
 *
 */
public interface GameService {

    /**
     * @param initialPitStoneCount, initialize each pit with number of stones.
     * @return Game, initializes new game and returns it's object with loaded
     *         initial data.
     */
    Game initGame(Integer initialPitStoneCount);

    /**
     * @param gameId, unique id of a game.
     * @param pitId
     * @return Game
     */
    Game play(String gameId, Integer pitId);
}
