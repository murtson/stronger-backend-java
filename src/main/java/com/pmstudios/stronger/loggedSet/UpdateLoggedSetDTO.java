package com.pmstudios.stronger.loggedSet;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Getter
@Setter
public class UpdateLoggedSetDTO {

    private BigDecimal weight;

    private int reps;

}
