package com.pmstudios.stronger.service;

import com.pmstudios.stronger.exercise.Exercise;
import com.pmstudios.stronger.exerciseCategory.ExerciseCategory;
import com.pmstudios.stronger.exerciseCategory.MuscleCategory;
import com.pmstudios.stronger.exercisePr.ExercisePr;
import com.pmstudios.stronger.exercisePr.ExercisePrService;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedExercise.LoggedExerciseService;
import com.pmstudios.stronger.loggedSet.LoggedSet;
import com.pmstudios.stronger.loggedSet.LoggedSetRepository;
import com.pmstudios.stronger.loggedSet.LoggedSetServiceImpl;
import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.workout.Workout;
import com.pmstudios.stronger.workout.WorkoutStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoggedSetServiceTest {

    @Mock
    private LoggedSetRepository loggedSetRepository;
    @Mock
    private ExercisePrService exercisePrService;
    @Mock
    private LoggedExerciseService loggedExerciseService;
    @InjectMocks
    private LoggedSetServiceImpl loggedSetService;


    private final User userMock =
            new User(1L, "Martin", "Mosesson", "martin.mosesson@mail.com",
                    "pass123", List.of(), List.of());
    private final ExerciseCategory chestCategory =
            new ExerciseCategory(MuscleCategory.CHEST);;

    private final Exercise benchPress =
            new Exercise(1L, "Flat Barbell Bench Press", chestCategory, List.of(), Set.of());

    private final Workout workoutMock = new Workout(1L, "workout_name_1", LocalDateTime.now(),
            null, WorkoutStatus.IN_PROGRESS, userMock, List.of());

    private LoggedSet loggedSet1;
    private LoggedSet loggedSet2;
    private LoggedSet loggedSet3;
    private LoggedSet loggedSet4;

    private LoggedExercise loggedExercise1;
    private ExercisePr exercisePr1;

    @Before
    public void setup() {

        // logged exercise
        loggedExercise1 = new LoggedExercise(workoutMock, benchPress);

        // logged sets
        loggedSet1 = new LoggedSet(125.00, 5, 140.1, false );
        loggedSet2 = new LoggedSet(75.00, 5, 83.4, false );
        loggedSet3 = new LoggedSet(50.00, 5, 56.2, false);
        loggedSet4 = new LoggedSet(100.00, 5, 112.5, true);
        // exercise pr
        exercisePr1 = new ExercisePr(loggedSet4.getWeight(), loggedSet4.getReps(),
                loggedSet4.getEstimatedOneRepMax(), userMock, benchPress, loggedSet4);
    }

    @Test
    public void updateExercisePrWhenLoggedSetIsAddedTestWithoutPreviousPrs() {
        LoggedExercise loggedExerciseSetWillBeAddedTo = loggedExercise1;
        Long exerciseId = loggedExerciseSetWillBeAddedTo.getExercise().getId();
        // TODO: replace later when auth is implemented
        Long userId = loggedExerciseSetWillBeAddedTo.getWorkout().getUser().getId();

        LoggedSet toBeAddedLoggedSet = loggedSet1;
        toBeAddedLoggedSet.setLoggedExercise(loggedExerciseSetWillBeAddedTo);

        assertNull(toBeAddedLoggedSet.getExercisePr());

        // case when there are no previous PR:s logged for that exercise with those reps
        when(exercisePrService.getByRepsAndExerciseAndUserId(toBeAddedLoggedSet.getReps(), exerciseId, userId))
                .thenReturn(null);

        loggedSetService.updateExercisePrWhenLoggedSetIsAdded(loggedExerciseSetWillBeAddedTo, toBeAddedLoggedSet);

        // assert that toBeAddedLoggedSet has been set as new exercisePr
        Double result = toBeAddedLoggedSet.getExercisePr().getEstimatedOneRepMax();
        Double expected = toBeAddedLoggedSet.getEstimatedOneRepMax();
        assertEquals(expected, result);
    }

    @Test
    public void doNotUpdateExercisePrWhenLoggedSetIsAddedTest() {
        LoggedExercise loggedExerciseSetWillBeAddedTo = loggedExercise1;
        Long exerciseId = loggedExerciseSetWillBeAddedTo.getExercise().getId();
        // TODO: replace later when auth is implemented
        Long userId = loggedExerciseSetWillBeAddedTo.getWorkout().getUser().getId();

        LoggedSet toBeAddedLoggedSet = loggedSet2;
        toBeAddedLoggedSet.setLoggedExercise(loggedExerciseSetWillBeAddedTo);

        assertNull(toBeAddedLoggedSet.getExercisePr());

        when(exercisePrService.getByRepsAndExerciseAndUserId(toBeAddedLoggedSet.getReps(), exerciseId, userId))
                .thenReturn(exercisePr1);

        loggedSetService.updateExercisePrWhenLoggedSetIsAdded(loggedExerciseSetWillBeAddedTo, toBeAddedLoggedSet);

        // assert that toBeAddedLoggedSet was NOT set to new PR
        assertNull(toBeAddedLoggedSet.getExercisePr());
    }


    @Test
    public void updateExercisePrWhenLoggedSetIsAddedTest() {
        LoggedExercise loggedExerciseSetWillBeAddedTo = loggedExercise1;
        Long exerciseId = loggedExerciseSetWillBeAddedTo.getExercise().getId();
        // TODO: replace later when auth is implemented
        Long userId = loggedExerciseSetWillBeAddedTo.getWorkout().getUser().getId();

        LoggedSet toBeAddedLoggedSet = loggedSet1;
        toBeAddedLoggedSet.setLoggedExercise(loggedExerciseSetWillBeAddedTo);

        assertNull(toBeAddedLoggedSet.getExercisePr());

        when(exercisePrService.getByRepsAndExerciseAndUserId(toBeAddedLoggedSet.getReps(), exerciseId, userId))
                .thenReturn(exercisePr1);

        loggedSetService.updateExercisePrWhenLoggedSetIsAdded(loggedExerciseSetWillBeAddedTo, toBeAddedLoggedSet);

        verify(exercisePrService, times(1)).deleteExercisePr(exercisePr1);

        // assert that toBeAddedLoggedSet has been set as new exercisePr
        Double result = toBeAddedLoggedSet.getExercisePr().getEstimatedOneRepMax();
        Double expected = toBeAddedLoggedSet.getEstimatedOneRepMax();
        assertEquals(expected, result);
    }


    @Test
    public void isNewTopLoggedSetTest() {
        List<LoggedSet> loggedSetsMock = Arrays.asList(loggedSet2, loggedSet3);

        boolean result = loggedSetService.isNewTopLoggedSet(loggedSet1, loggedSetsMock);
        assertTrue(result);

        result = loggedSetService.isNewTopLoggedSet(loggedSet3, loggedSetsMock);
        assertFalse(result);

        result = loggedSetService.isNewTopLoggedSet(loggedSet2, loggedSetsMock);
        assertFalse(result);
    }

    @Test
    public void getLoggedSetWithHighestEORMTest() {
        List<LoggedSet> loggedSetsMock = Arrays.asList(loggedSet1, loggedSet2, loggedSet3);

        Double result = loggedSetService.getLoggedSetWithHighestEORM(loggedSetsMock).getEstimatedOneRepMax();
        Double expected = loggedSet1.getEstimatedOneRepMax();
        assertEquals(expected, result);
    }

    @Test
    public void doNotUpdateTopLoggedSetWhenLoggedSetIsAddedTest() {
        LoggedSet loggedSetToBeAdded = loggedSet2;
        List<LoggedSet> previousLoggedSets = Arrays.asList(loggedSet1, loggedSet3);

        loggedSetService.updateTopLoggedSetWhenLoggedSetIsAdded(loggedSetToBeAdded, previousLoggedSets);

        verify(loggedSetRepository, never()).save(any(LoggedSet.class));

        boolean result = loggedSetToBeAdded.isTopLoggedSet();

        assertFalse(result);
    }

    @Test
    public void updateTopLoggedSetWhenLoggedSetIsAddedTest() {
        LoggedSet loggedSetToBeAdded = loggedSet1;
        List<LoggedSet> previousLoggedSets = Arrays.asList(loggedSet2, loggedSet4);

        loggedSetService.updateTopLoggedSetWhenLoggedSetIsAdded(loggedSetToBeAdded, previousLoggedSets);

        // save should update loggedSetMock4
        verify(loggedSetRepository, times(1)).save(loggedSet4);

        // previous topLoggedSet should be replaced
        assertFalse(loggedSet4.isTopLoggedSet());

        // newly added set should be topLoggedSet
        assertTrue(loggedSet1.isTopLoggedSet());
    }

    @Test
    public void updateTopLoggedSetWhenLoggedSetIsAddedWithoutPreviousSetsTest() {
        LoggedSet loggedSetToBeAdded = loggedSet1;
        List<LoggedSet> previousLoggedSets = List.of();

        loggedSetService.updateTopLoggedSetWhenLoggedSetIsAdded(loggedSetToBeAdded, previousLoggedSets);

        verify(loggedSetRepository, never()).save(any(LoggedSet.class));

        assertTrue(loggedSet1.isTopLoggedSet());
    }



//        when(loggedSetRepository.findByRepsAndLoggedExercise_Exercise_IdAndLoggedExercise_Workout_User_Id(125, 1L, 1L))
//                .thenReturn(Arrays.asList(loggedSet2, loggedSet3, loggedSet4));
}
