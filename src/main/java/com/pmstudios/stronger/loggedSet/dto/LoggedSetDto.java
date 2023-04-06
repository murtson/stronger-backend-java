package com.pmstudios.stronger.loggedSet.dto;

import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class LoggedSetDto {

    @NonNull
    private Long id;

    @NonNull
    private Double weight;

    @NonNull
    private Integer reps;

    @NonNull
    private Double estimatedOneRepMax;

    @NonNull
    boolean isPersonalRecord;

    @NonNull
    boolean isTopLoggedSet;

}
