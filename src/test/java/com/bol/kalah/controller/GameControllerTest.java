package com.bol.kalah.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.annotation.PostConstruct;

import org.hamcrest.Matchers;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.bol.kalah.constants.GameStatus;
import com.bol.kalah.model.Board;
import com.bol.kalah.model.Game;
import com.bol.kalah.model.Player;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

@RunWith(SpringRunner.class)
@SpringBootTest
public class GameControllerTest {

    @Mock
    Board boardMock;

    @Autowired
    private WebApplicationContext webApplicationContext;

    private MockMvc mockMvc;

    @PostConstruct
    public void setUp() {
	mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }

    @Test
    public void testInitBoard() throws Exception {

	mockMvc.perform(post("/api/kalah/games")).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id").isNotEmpty())
		.andExpect(MockMvcResultMatchers.jsonPath("$.gameStatus").value("INIT"))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.size()", Matchers.is(Board.PIT_END_INDEX)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.1.pitIndex").value(1))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.8.pitIndex").value(8))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.14.pitIndex").value(Board.PIT_END_INDEX))
		.andExpect(MockMvcResultMatchers.jsonPath("$.player1.playerIndex").value(Player.PLAYER1_INDEX))
		.andExpect(MockMvcResultMatchers.jsonPath("$.player2.playerIndex").value(Player.PLAYER2_INDEX))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.6.playerIndex").value(Player.PLAYER1_INDEX))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.13.playerIndex").value(Player.PLAYER2_INDEX))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.14.playerIndex").value(Player.PLAYER2_INDEX))
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.board.pits.5.stoneCount").value(Board.INITIAL_STONE_ON_PIT))
		.andExpect(
			MockMvcResultMatchers.jsonPath("$.board.pits.12.stoneCount").value(Board.INITIAL_STONE_ON_PIT))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.14.stoneCount")
			.value(Board.INITIAL_STONE_ON_HOUSE))
		.andReturn();
    }

    @Test
    public void testPlay() throws Exception {

	var response = mockMvc.perform(post("/api/kalah/games")).andReturn().getResponse().getContentAsString();

	var objectMapper = new ObjectMapper();
	objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
	var game = objectMapper.readValue(response, Game.class);

	mockMvc.perform(put("/api/kalah/games/" + game.getId() + "/pits/" + 1)).andExpect(status().isOk())
		.andExpect(MockMvcResultMatchers.jsonPath("$.id").value(game.getId()))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.size()", Matchers.is(Board.PIT_END_INDEX)))
		.andExpect(MockMvcResultMatchers.jsonPath("$.player1.playerIndex").value(Player.PLAYER1_INDEX))
		.andExpect(MockMvcResultMatchers.jsonPath("$.player2.playerIndex").value(Player.PLAYER2_INDEX))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.1.stoneCount").value(0))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.2.stoneCount").value(7))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.3.stoneCount").value(7))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.4.stoneCount").value(7))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.5.stoneCount").value(7))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.6.stoneCount").value(7))
		.andExpect(MockMvcResultMatchers.jsonPath("$.board.pits.7.stoneCount").value(1))
		.andExpect(MockMvcResultMatchers.jsonPath("$.gameStatus").value(GameStatus.PLAYER1_TURN.toString()))
		.andReturn();

    }

}