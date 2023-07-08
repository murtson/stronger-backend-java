package com.pmstudios.stronger.exercisePr;

import com.pmstudios.stronger.exercise.Exercise;
import com.pmstudios.stronger.exercisePr.dto.ExercisePrResponse;
import com.pmstudios.stronger.loggedSet.LoggedSet;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ExercisePrServiceImpl implements ExercisePrService {

    ExercisePrRepository exercisePrRepository;

    @Override
    public void deleteByExercisePr(ExercisePr exercisePr) {
        LoggedSet loggedSet = exercisePr.getLoggedSet();
        loggedSet.setExercisePr(null);
        exercisePrRepository.delete(exercisePr);
    }

    @Override
    public void deleteById(Long exercisePrId) {
        exercisePrRepository.deleteById(exercisePrId);
    }

    @Override
    public ExercisePr save(ExercisePr exercisePr) {
        return exercisePrRepository.save(exercisePr);
    }

    @Override
    public ExercisePr getByRepsAndExerciseAndUserId(int reps, Long exerciseId, Long userId) {
        return exercisePrRepository.findByRepsAndExerciseIdAndUserId(reps, exerciseId, userId);
    }

    @Override
    public List<ExercisePr> getByExercise(Long exerciseId, Long userId) {
        return exercisePrRepository.findByExerciseIdAndUserId(exerciseId, userId);
    }

    @Override
    public List<ExercisePr> getAll(Long userId) {
        return exercisePrRepository.findByUserId(userId);
    }

}
