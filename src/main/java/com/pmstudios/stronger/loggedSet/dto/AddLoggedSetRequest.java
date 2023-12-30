package com.pmstudios.stronger.loggedSet.dto;

import com.pmstudios.stronger.loggedSet.LoggedSet;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

public record AddLoggedSetRequest(Double weight, int reps) {
}
