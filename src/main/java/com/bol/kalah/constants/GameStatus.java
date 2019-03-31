package com.bol.kalah.constants;

/**
 * TODO: Remove these notes from production release code.
 * Why I used Enum for holding constant Game Status  
 * 1) Enum is thread safe by default.
 * 2) Enum is static and final by default, so cannot be changed after once it is created.
 * 3) Better way to use in if-then-else and switch case comparisons.
 * 4) easy to group and use, game life cycle stages under one data type.
 *  
 */

/**
 * Identifies the current game status.
 *
 * @author AMDALI
 */
public enum GameStatus {

    /**
     * Game was initiated but not started.
     */
    INIT,

    /**
     * Player 1 is on the turn.
     */
    PLAYER1_TURN,

    /**
     * Player 2 is on the turn.
     */
    PLAYER2_TURN,

    /**
     * Game has finished.
     */
    FINISHED
}
