package com.pmstudios.stronger.exercisePr;

import com.pmstudios.stronger.exercise.Exercise;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class ExercisePrServiceImpl implements ExercisePrService {

    ExercisePrRepository exercisePrRepository;

    @Override
    public void deleteExercisePR(Long Id) { exercisePrRepository.deleteById(Id); }

    @Override
    public ExercisePr saveExercisePr(ExercisePr exercisePr) {
        return exercisePrRepository.save(exercisePr);
    }

    @Override
    public ExercisePr getByRepsAndExerciseAndUserId(int reps, Long exerciseId, Long userId) {
        return exercisePrRepository.findByRepsAndExerciseIdAndUserId(reps, exerciseId, userId);
    }

    @Override
    public List<ExercisePr> getSpecificExercisePrs(Long exerciseId, Long userId) {
        return exercisePrRepository.findByExerciseIdAndUserId(exerciseId, userId);
    }

    @Override
    public List<ExercisePr> getAllExercisePrs(Long userId) {
        return exercisePrRepository.findByUserId(userId);
    }

    @Override
    public ExercisePrDto toDto(ExercisePr exercisePr) {

        if (exercisePr == null) return null;

        Long id = exercisePr.getId();
        Double weight = exercisePr.getWeight();
        Integer reps = exercisePr.getReps();
        Double estimatedOneRepMax = exercisePr.getEstimatedOneRepMax();
        Long userId = exercisePr.getUser().getId();
        Exercise exercise = exercisePr.getExercise();
        Long workoutId = exercisePr.getLoggedSet().getLoggedExercise().getWorkout().getId();
        Long loggedSetId = exercisePr.getLoggedSet().getId();
        Long loggedExerciseId = exercisePr.getLoggedSet().getLoggedExercise().getId();

        return new ExercisePrDto(id, weight, reps, estimatedOneRepMax, userId, workoutId, loggedSetId, loggedExerciseId, exercise);
    }


}
