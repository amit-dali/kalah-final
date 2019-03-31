package com.bol.kalah.model;

import org.springframework.stereotype.Component;

import com.bol.kalah.constants.GameStatus;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

/**
 * This class represent the game. A game contain players, board, games status
 * and updateTime time.
 *
 * @author AMDALI
 */
@Slf4j
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class Game {

    private static final int NUM_OF_ALLOWED_PLAYERS = 2;
    private String id;
    private Board board;

    @Setter(AccessLevel.NONE)
    @Getter(AccessLevel.NONE)
    private Player[] players = new Player[NUM_OF_ALLOWED_PLAYERS];

    private Player winner;
    private GameStatus gameStatus;
    private Long updateAt;

    public Game(Integer initialStoneOnPit) {
	players[0] = new Player(Player.PLAYER1_INDEX, Player.PLAYER1_DFLT_NAME);
	players[1] = new Player(Player.PLAYER2_INDEX, Player.PLAYER2_DFLT_NAME);
	board = new Board(initialStoneOnPit, players[0], players[1]);
	gameStatus = GameStatus.INIT;
	updateAt = System.currentTimeMillis();
    }

    /**
     * This method is set the time of the last activity.
     */
    public void updateTime() {
	log.info("enter updateTime");

	setUpdateAt(System.currentTimeMillis());

	log.debug("getUpdateAt={}", getUpdateAt());
	log.info("exit updateTime");
    }

    public Player getPlayer1() {
	return players[0];
    }

    public Player getPlayer2() {
	return players[1];
    }

}
