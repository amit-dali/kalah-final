package com.bol.kalah.model;

import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 *
 * @author AMDALI
 */

@Setter
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Player {

    private static final String PRE_FIX_DFLT_PLAYER_NAME = "player";

    public static final Integer PLAYER1_INDEX = 0;
    public static final Integer PLAYER2_INDEX = 1;
    public static final String PLAYER1_DFLT_NAME = PRE_FIX_DFLT_PLAYER_NAME + PLAYER1_INDEX;
    public static final String PLAYER2_DFLT_NAME = PRE_FIX_DFLT_PLAYER_NAME + PLAYER2_INDEX;

    @NotNull
    private Integer playerIndex;

    @NotNull
    private String name;

}
