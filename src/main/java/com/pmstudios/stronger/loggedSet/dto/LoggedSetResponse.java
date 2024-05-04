package com.pmstudios.stronger.loggedSet.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.pmstudios.stronger.loggedSet.LoggedSet;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggedSetResponse {

    @JsonProperty("isPersonalRecord")
    boolean isPersonalRecord;
    @JsonProperty("isTopLoggedSet")
    boolean isTopLoggedSet;
    private Long loggedSetId;
    private Double weight;
    private Integer reps;
    private Double estimatedOneRepMax;

    public static LoggedSetResponse from(LoggedSet entity) {
        if (entity == null) return null;

        boolean isPersonalRecord = entity.getExercisePr() != null;

        return LoggedSetResponse.builder()
                .loggedSetId(entity.getId())
                .weight(entity.getWeight())
                .reps(entity.getReps())
                .estimatedOneRepMax(entity.getEstimatedOneRepMax())
                .isPersonalRecord(isPersonalRecord)
                .isTopLoggedSet(entity.isTopLoggedSet())
                .build();
    }

}
