package com.bol.kalah.repository;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameMemoryRepositoryTest {

    @Autowired
    private GameMemoryRepository gameMemoryRepository;

    @Test
    public void testCreate() {
	var game = gameMemoryRepository.create(6);
	var foundGame = gameMemoryRepository.findById(game.getId());

	assertEquals(game, foundGame);
    }

}
