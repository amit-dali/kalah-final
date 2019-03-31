package com.bol.kalah.service;

import org.springframework.stereotype.Component;

import com.bol.kalah.model.Game;
import com.bol.kalah.model.Pit;
import com.bol.kalah.service.rule.DistributePitStoneRule;
import com.bol.kalah.service.rule.EndPitRule;
import com.bol.kalah.service.rule.GameOver;
import com.bol.kalah.service.rule.KalahRuleProcessor;
import com.bol.kalah.service.rule.StartPitRule;

import lombok.extern.slf4j.Slf4j;

/**
 * This class represent the game rule chain.
 *
 * @author AMDALI
 */
@Slf4j
@Component
public class GameEngine {

    private final KalahRuleProcessor chain;

    public GameEngine() {
	chain = buildChain();
    }

    /**
     * @param game
     * @param pit
     */
    public void play(Game game, Pit pit) {
	log.info("enter play");
	log.debug("game = {}", game);
	log.debug("pit = {}", pit);

	chain.apply(game, pit);

	log.info("exit play");
    }

    /**
     * @return KalahRuleProcessor, builds chain of rule's to apply on each play of
     *         game.
     */
    private KalahRuleProcessor buildChain() {
	GameOver gameOver = new GameOver(null);
	EndPitRule endPitRule = new EndPitRule(gameOver);
	DistributePitStoneRule distributePitStoneRule = new DistributePitStoneRule(endPitRule);
	return new StartPitRule(distributePitStoneRule);
    }

}
