package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class LoggedSetServiceImpl implements LoggedSetService {

    LoggedSetRepository loggedSetRepository;

    @Override
    public LoggedSet saveLoggedSet(LoggedSet set, LoggedExercise loggedExercise) {
        set.setLoggedExercise(loggedExercise);
        return loggedSetRepository.save(set);
    }

    @Override
    public List<LoggedSet> updateLoggedSets(List<UpdateLoggedSetDTO> loggedSets, LoggedExercise loggedExercise) {

        deleteAllByLoggedExerciseId(loggedExercise.getId());

        return loggedSets.stream()
                .map(this::convertDTOtoEntity)
                .map(set -> saveLoggedSet(set, loggedExercise))
                .collect(Collectors.toList());
    }

    @Override
    public void deleteAllByLoggedExerciseId(Long loggedExerciseId) {
        loggedSetRepository.deleteAllByLoggedExerciseId(loggedExerciseId);
    }

    private LoggedSet convertDTOtoEntity(UpdateLoggedSetDTO dto) {
        return new LoggedSet(dto.getWeight(), dto.getReps(), estimateOneRepMax(dto.getWeight(), dto.getReps()));
    }

    private BigDecimal estimateOneRepMax(BigDecimal weight, int reps) {
        // We use the Brzycki formula from Matt Brzycki to calculate 1RM: weight / (1.0278 - 0.0287 * reps)
        BigDecimal divisor = BigDecimal.valueOf(1.0278 - 0.0287 * reps);
        return weight.divide(divisor, 3, RoundingMode.DOWN);
    }
}
