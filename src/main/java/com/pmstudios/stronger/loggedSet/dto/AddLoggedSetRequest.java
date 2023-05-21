package com.pmstudios.stronger.loggedSet.dto;

import com.pmstudios.stronger.loggedSet.LoggedSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


@AllArgsConstructor
@Getter
@Setter
public class AddLoggedSetRequest {

    private Double weight;

    private int reps;


}
