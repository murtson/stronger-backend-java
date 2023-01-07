package com.pmstudios.stronger.loggedSet.dto;

import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedSet.LoggedSet;
import org.springframework.stereotype.Component;

@Component
public class LoggedSetMapper {

    public LoggedSet dtoToEntity(LoggedSetUpdateDto dto) {
        return new LoggedSet(dto.getWeight(), dto.getReps(),
                this.getOneRepMaxEstimate(dto.getWeight(), dto.getReps()), false);
    }

    public LoggedSetDto entityToDto(LoggedSet entity) {
        if(entity == null) return null;
        boolean isPersonalRecord = entity.getExercisePr() != null;
        return new LoggedSetDto(entity.getId(), entity.getWeight(), entity.getReps(), entity.getEstimatedOneRepMax(), isPersonalRecord);
    }

    private Double getOneRepMaxEstimate(Double weight, int reps) {
        // We use the Brzycki formula from Matt Brzycki to calculate 1RM: weight / (1.0278 - 0.0287 * reps)
        // TODO: fix rounding
        Double divisor = 1.0278 - 0.0287 * reps;
        return weight / divisor;
        // return weight.divide(divisor, 3, RoundingMode.DOWN);
    }


}
