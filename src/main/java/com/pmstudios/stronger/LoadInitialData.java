package com.pmstudios.stronger;

import com.pmstudios.stronger.entity.Exercise;
import com.pmstudios.stronger.entity.ExerciseCategory;
import com.pmstudios.stronger.entity.User;
import com.pmstudios.stronger.exception.EntityNotFoundException;
import com.pmstudios.stronger.respository.ExerciseCategoryRepository;
import com.pmstudios.stronger.respository.ExerciseRepository;
import com.pmstudios.stronger.respository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class LoadInitialData implements ApplicationRunner {

    @Autowired
    ExerciseCategoryRepository exerciseCategoryRepository;
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    UserRepository userRepository;

    User testUser1 = new User("William", "Jonsson", "william@gmail.com", "test123");
    User testUser2 = new User("Linus", "Ekelöf", "linus@gmail.com", "test123");
    User[] testUsers = { testUser1, testUser2 };

    ExerciseCategory[] exerciseCategories = new ExerciseCategory[] {
            new ExerciseCategory(ExerciseCategory.MuscleCategory.CHEST),
            new ExerciseCategory(ExerciseCategory.MuscleCategory.SHOULDERS),
            new ExerciseCategory(ExerciseCategory.MuscleCategory.TRICEPS),
            new ExerciseCategory(ExerciseCategory.MuscleCategory.LEGS),
            new ExerciseCategory(ExerciseCategory.MuscleCategory.BACK),
            new ExerciseCategory(ExerciseCategory.MuscleCategory.BICEPS),
            new ExerciseCategory(ExerciseCategory.MuscleCategory.ABS),
    };

    Exercise[] chestExercises = new Exercise[] {
            new Exercise("Flat Barbell Bench Press"),
            new Exercise("Incline Dumbbell Bench Press")
    };

    Exercise[] shoulderExercises = new Exercise[] {
            new Exercise("Overhead Press"),
            new Exercise("Lateral Raises")
    };

    Exercise[] tricepsExercises = new Exercise[] {
            new Exercise("Barbell Skullcrushers"),
            new Exercise("V-bar Pushdown")
    };

    Exercise[] legExercises = new Exercise[] {
            new Exercise("High-bar Barbell Squat"),
            new Exercise("Leg Press Machine")
    };

    Exercise[] backExercises = new Exercise[] {
            new Exercise("Barbell Row"),
            new Exercise("Pull Ups")
    };

    Exercise[] bicepExercises = new Exercise[] {
            new Exercise("Dumbbell Hammer Curl"),
            new Exercise("Ez-bar Curl")
    };

    Exercise[] absExercises = new Exercise[] {
            new Exercise("Crunches"),
            new Exercise("Plank")
    };

    public LoadInitialData() {
    }


    @Override
    public void run(ApplicationArguments args) throws Exception {

        // populate with db with test users
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
