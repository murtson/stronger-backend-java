package com.pmstudios.stronger;

import com.pmstudios.stronger.exercise.Exercise;
import com.pmstudios.stronger.exerciseCategory.ExerciseCategory;
import com.pmstudios.stronger.exerciseCategory.MuscleCategory;
import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.exception.EntityNotFoundException;
import com.pmstudios.stronger.exerciseCategory.ExerciseCategoryRepository;
import com.pmstudios.stronger.exercise.ExerciseRepository;
import com.pmstudios.stronger.user.UserRepository;
import com.pmstudios.stronger.workout.WorkoutRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;

@Component
public class LoadInitialData implements ApplicationRunner {

    @Autowired
    ExerciseCategoryRepository exerciseCategoryRepository;
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    UserRepository userRepository;

    User testUser1 = new User(1L, "William", "Jonsson", "william@gmail.com", "test123", List.of(), List.of());
    User testUser2 = new User(2L, "Linus", "Ekelöf", "linus@gmail.com", "test123", List.of(), List.of());
    User[] testUsers = { testUser1, testUser2 };

    ExerciseCategory chestCategory = new ExerciseCategory(MuscleCategory.CHEST);
    ExerciseCategory shoulderCategory = new ExerciseCategory(MuscleCategory.SHOULDERS);
    ExerciseCategory tricepsCategory =   new ExerciseCategory(MuscleCategory.TRICEPS);
    ExerciseCategory legsCategory =   new ExerciseCategory(MuscleCategory.LEGS);
    ExerciseCategory backCategory =  new ExerciseCategory(MuscleCategory.BACK);
    ExerciseCategory bicepsCategory =  new ExerciseCategory(MuscleCategory.BICEPS);
    ExerciseCategory absCategory =    new ExerciseCategory(MuscleCategory.ABS);


    ExerciseCategory[] exerciseCategories = new ExerciseCategory[] {
            new ExerciseCategory(MuscleCategory.CHEST),
            new ExerciseCategory(MuscleCategory.SHOULDERS),
            new ExerciseCategory(MuscleCategory.TRICEPS),
            new ExerciseCategory(MuscleCategory.LEGS),
            new ExerciseCategory(MuscleCategory.BACK),
            new ExerciseCategory(MuscleCategory.BICEPS),
            new ExerciseCategory(MuscleCategory.ABS),
    };

    Exercise[] chestExercises = new Exercise[] {
            new Exercise(1L, "Flat Barbell Bench Press", chestCategory, List.of(), Set.of()),
            new Exercise(2L, "Incline Dumbbell Bench Press", chestCategory, List.of(), Set.of())
    };

    Exercise[] shoulderExercises = new Exercise[] {
            new Exercise(3L, "Overhead Press", shoulderCategory, List.of(), Set.of()),
            new Exercise(4L, "Lateral Raises", shoulderCategory, List.of(), Set.of())
    };

    Exercise[] tricepsExercises = new Exercise[] {
            new Exercise(5L, "Barbell Skullcrushers", tricepsCategory, List.of(), Set.of()),
            new Exercise(6L, "V-bar Pushdown", tricepsCategory, List.of(), Set.of())
    };

    Exercise[] legExercises = new Exercise[] {
            new Exercise(7L, "High-bar Barbell Squat", legsCategory, List.of(), Set.of()),
            new Exercise(8L, "Leg Press Machine", legsCategory, List.of(), Set.of())
    };

    Exercise[] backExercises = new Exercise[] {
            new Exercise(9L, "Barbell Row", backCategory, List.of(), Set.of()),
            new Exercise(10L, "Pull Ups", backCategory, List.of(), Set.of())
    };

    Exercise[] bicepExercises = new Exercise[] {
            new Exercise(11L, "Dumbbell Hammer Curl", bicepsCategory, List.of(), Set.of()),
            new Exercise(12L, "Ez-bar Curl", bicepsCategory, List.of(), Set.of())
    };

    Exercise[] absExercises = new Exercise[] {
            new Exercise(13L, "Crunches", absCategory, List.of(), Set.of()),
            new Exercise(14L, "Plank", absCategory, List.of(), Set.of())
    };
    private final WorkoutRepository workoutRepository;

    public LoadInitialData(WorkoutRepository workoutRepository) {
        this.workoutRepository = workoutRepository;
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

        // populate db with test users
        for (User user : testUsers) userRepository.save(user);

        // populate db with test categories
        for (ExerciseCategory exerciseCategory : exerciseCategories)
            exerciseCategoryRepository.save(exerciseCategory);

        ExerciseCategory chestCategory = exerciseCategoryRepository.findById(1L)
                .orElseThrow(() -> new EntityNotFoundException(1L, ExerciseCategory.class));

        ExerciseCategory shoulderCategory = exerciseCategoryRepository.findById(2L)
                .orElseThrow(() -> new EntityNotFoundException(2L, ExerciseCategory.class));

        ExerciseCategory tricepsCategory = exerciseCategoryRepository.findById(3L)
                .orElseThrow(() -> new EntityNotFoundException(3L, ExerciseCategory.class));

        ExerciseCategory legCategory = exerciseCategoryRepository.findById(4L)
                .orElseThrow(() -> new EntityNotFoundException(4L, ExerciseCategory.class));

        ExerciseCategory backCategory = exerciseCategoryRepository.findById(5L)
                .orElseThrow(() -> new EntityNotFoundException(5L, ExerciseCategory.class));

        ExerciseCategory bicepCategory = exerciseCategoryRepository.findById(6L)
                .orElseThrow(() -> new EntityNotFoundException(6L, ExerciseCategory.class));

        ExerciseCategory absCategory = exerciseCategoryRepository.findById(7L)
                .orElseThrow(() -> new EntityNotFoundException(7L, ExerciseCategory.class));

        for(Exercise exercise : chestExercises) {
            exercise.setExerciseCategory(chestCategory);
            exerciseRepository.save(exercise);
        }

        for(Exercise exercise : shoulderExercises) {
            exercise.setExerciseCategory(shoulderCategory);
            exerciseRepository.save(exercise);
        }

        for(Exercise exercise : tricepsExercises) {
            exercise.setExerciseCategory(tricepsCategory);
            exerciseRepository.save(exercise);
        }

        for(Exercise exercise : legExercises) {
            exercise.setExerciseCategory(legCategory);
            exerciseRepository.save(exercise);
        }

        for(Exercise exercise : backExercises) {
            exercise.setExerciseCategory(backCategory);
            exerciseRepository.save(exercise);
        }

        for(Exercise exercise : bicepExercises) {
            exercise.setExerciseCategory(bicepCategory);
            exerciseRepository.save(exercise);
        }

        for(Exercise exercise : absExercises) {
            exercise.setExerciseCategory(absCategory);
            exerciseRepository.save(exercise);
        }

    }
}
