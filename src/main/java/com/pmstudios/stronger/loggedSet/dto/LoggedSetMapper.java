package com.pmstudios.stronger.loggedSet.dto;

import com.pmstudios.stronger.NumberUtility;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedSet.LoggedSet;
import org.springframework.beans.factory.annotation.Autowired;
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
        return new LoggedSetDto(entity.getId(), entity.getWeight(), entity.getReps(), entity.getEstimatedOneRepMax(), isPersonalRecord, entity.isTopLoggedSet());
    }

    private Double getOneRepMaxEstimate(Double weight, int reps) {
        if(reps == 1) return weight;
        // We use the Brzycki formula from Matt Brzycki to calculate 1RM: weight / (1.0278 - 0.0287 * reps)
        Double divisor = 1.0278 - 0.0287 * reps;
        return NumberUtility.round(weight / divisor, 2);
    }



}
