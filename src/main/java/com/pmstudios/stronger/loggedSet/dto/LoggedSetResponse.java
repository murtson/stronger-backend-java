package com.pmstudios.stronger.loggedSet.dto;

import com.pmstudios.stronger.loggedSet.LoggedSet;
import lombok.*;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoggedSetResponse {

    boolean isPersonalRecord;
    boolean isTopLoggedSet;
    private Long id;
    private Double weight;
    private Integer reps;
    private Double estimatedOneRepMax;

    public static LoggedSetResponse from(LoggedSet entity) {
        if (entity == null) return null;

        boolean isPersonalRecord = entity.getExercisePr() != null;

        return LoggedSetResponse.builder()
                .id(entity.getId())
                .weight(entity.getWeight())
                .reps(entity.getReps())
                .estimatedOneRepMax(entity.getEstimatedOneRepMax())
                .isPersonalRecord(isPersonalRecord)
                .isTopLoggedSet(entity.isTopLoggedSet())
                .build();
    }

}
