package com.pmstudios.stronger;

import com.pmstudios.stronger.exercise.Exercise;
import com.pmstudios.stronger.exerciseCategory.ExerciseCategory;
import com.pmstudios.stronger.exerciseCategory.MuscleCategory;
import com.pmstudios.stronger.exercisePr.ExercisePr;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedSet.LoggedSet;
import com.pmstudios.stronger.loggedSet.LoggedSetRepository;
import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.exerciseCategory.ExerciseCategoryRepository;
import com.pmstudios.stronger.exercise.ExerciseRepository;
import com.pmstudios.stronger.user.UserRepository;
import com.pmstudios.stronger.userRole.UserRole;
import com.pmstudios.stronger.userRole.UserRoleEnum;
import com.pmstudios.stronger.userRole.UserRoleRepository;
import com.pmstudios.stronger.workout.Workout;
import com.pmstudios.stronger.workout.WorkoutRepository;
import com.pmstudios.stronger.workout.WorkoutStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;

@Component
public class LoadInitialData implements ApplicationRunner {

    @Autowired
    ExerciseCategoryRepository exerciseCategoryRepository;
    @Autowired
    ExerciseRepository exerciseRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    WorkoutRepository workoutRepository;
    @Autowired
    UserRoleRepository userRoleRepository;
    User userWilliam = new User(
            "Toney the foney",
            "William",
            "Jonsson",
            "william@gmail.com",
            "test123");
    User userLinus = new User(
            "Hermonie",
            "Linus",
            "Ekelöf",
            "linus@gmail.com",
            "test123");
    User[] mockUsers = {userWilliam, userLinus};
    ExerciseCategory chestCategory = new ExerciseCategory(MuscleCategory.CHEST);
    ExerciseCategory shoulderCategory = new ExerciseCategory(MuscleCategory.SHOULDERS);
    ExerciseCategory tricepsCategory = new ExerciseCategory(MuscleCategory.TRICEPS);
    ExerciseCategory legsCategory = new ExerciseCategory(MuscleCategory.LEGS);
    ExerciseCategory backCategory = new ExerciseCategory(MuscleCategory.BACK);
    ExerciseCategory bicepsCategory = new ExerciseCategory(MuscleCategory.BICEPS);
    ExerciseCategory absCategory = new ExerciseCategory(MuscleCategory.ABS);
    Exercise benchPress = new Exercise("Flat Barbell Bench Press", chestCategory);
    List<Exercise> chestExercises = List.of(
            benchPress,
            new Exercise("Incline Dumbbell Bench Press", chestCategory)
    );
    Exercise squats = new Exercise("High-bar Barbell Squat", legsCategory);
    List<Exercise> legExercises = List.of(
            squats,
            new Exercise("Leg Press Machine", legsCategory)
    );
    ExerciseCategory[] exerciseCategories = new ExerciseCategory[]{
            chestCategory, shoulderCategory, tricepsCategory,
            legsCategory, backCategory, bicepsCategory, absCategory,
    };
    List<Exercise> shoulderExercises = List.of(
            new Exercise("Overhead Press", shoulderCategory),
            new Exercise("Lateral Raises", shoulderCategory)
    );

    List<Exercise> tricepsExercises = List.of(
            new Exercise("Barbell Skullcrushers", tricepsCategory),
            new Exercise("V-bar Pushdown", tricepsCategory)
    );
    List<Exercise> backExercises = List.of(
            new Exercise("Barbell Row", backCategory),
            new Exercise("Pull Ups", backCategory)
    );
    List<Exercise> bicepExercises = List.of(
            new Exercise("Dumbbell Hammer Curl", bicepsCategory),
            new Exercise("Ez-bar Curl", bicepsCategory)
    );
    List<Exercise> absExercises = List.of(
            new Exercise("Crunches", absCategory),
            new Exercise("Plank", absCategory)
    );
    LoggedSet loggedSet1 = new LoggedSet(125.0, 5, 140.1, false);
    LoggedSet loggedSet2_william = new LoggedSet(100.0, 5, 112.5, true);
    LoggedSet loggedSet3_william = new LoggedSet(75.0, 5, 83.4, false);
    LoggedSet loggedSet4_william = new LoggedSet(50.0, 5, 56.2, false);
    List<LoggedSet> mockLoggedSets = List.of(loggedSet2_william, loggedSet3_william, loggedSet4_william);
    ExercisePr exercisePr1_william;
    Workout workout1_william = new Workout(LocalDateTime.now(), WorkoutStatus.IN_PROGRESS, userWilliam);
    LoggedExercise loggedExercise1_william = new LoggedExercise(workout1_william, benchPress);
    Workout workout1_linus = new Workout(LocalDateTime.now(), WorkoutStatus.IN_PROGRESS, userLinus);
    List<Workout> mockWorkouts = List.of(workout1_william, workout1_linus);
    LoggedExercise loggedExercise1_linus = new LoggedExercise(workout1_linus, squats);
    @Autowired
    private LoggedSetRepository loggedSetRepository;

    @Override
    public void run(ApplicationArguments args) throws Exception {

        UserRole USER = new UserRole(UserRoleEnum.USER.toString());
        UserRole ADMIN = new UserRole(UserRoleEnum.ADMIN.toString());

        userRoleRepository.save(USER);
        userRoleRepository.save(ADMIN);

//         prepare loggedSets

        // set exercisePr
//        exercisePr1_william = ExercisePr.from(loggedSet2_william);
//        loggedSet2_william.setExercisePr(exercisePr1_william);
        // prep loggedExercise
//        loggedExercise1_william.setLoggedSets(List.of(loggedSet2_william, loggedSet3_william, loggedSet4_william));
        // prep workout
//        workout1_william.setLoggedExercises(List.of(loggedExercise1_william));
        List<Workout> williamWorkouts = List.of(workout1_william);
        // prep user


        chestCategory.setExercises(chestExercises);
        shoulderCategory.setExercises(shoulderExercises);
        tricepsCategory.setExercises(tricepsExercises);
        legsCategory.setExercises(legExercises);
        backCategory.setExercises(backExercises);
        bicepsCategory.setExercises(bicepExercises);
        absCategory.setExercises(absExercises);


        // populate db with test categories
        for (ExerciseCategory exerciseCategory : exerciseCategories)
            exerciseCategoryRepository.save(exerciseCategory);

        // populate db with test users
        for (User user : mockUsers) userRepository.save(user);

        workout1_william.setLoggedExercises(List.of(loggedExercise1_william));
        workout1_linus.setLoggedExercises(List.of(loggedExercise1_linus));
        // populate db with workouts
        for (Workout workout : mockWorkouts) workoutRepository.save(workout);

        loggedSet2_william.setLoggedExercise(loggedExercise1_william);
        loggedSet3_william.setLoggedExercise(loggedExercise1_william);

        // populate db with loggedSets
//        for(LoggedSet loggedSet : mockLoggedSets) loggedSetRepository.save(loggedSet);

    }
}
