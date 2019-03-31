package com.bol.kalah.model;

import javax.validation.constraints.NotNull;

import org.springframework.stereotype.Component;

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
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Component
public class Pit {

    @NotNull
    private Integer pitIndex;

    @NotNull
    private Integer stoneCount;

    @NotNull
    private Integer playerIndex;

}
