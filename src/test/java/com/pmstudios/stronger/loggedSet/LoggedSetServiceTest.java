package com.pmstudios.stronger.loggedSet;

import com.pmstudios.stronger.exercise.Exercise;
import com.pmstudios.stronger.exerciseCategory.ExerciseCategory;
import com.pmstudios.stronger.exerciseCategory.MuscleCategory;
import com.pmstudios.stronger.exercisePr.ExercisePr;
import com.pmstudios.stronger.exercisePr.ExercisePrService;
import com.pmstudios.stronger.loggedExercise.LoggedExercise;
import com.pmstudios.stronger.loggedExercise.LoggedExerciseService;
import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.workout.Workout;
import com.pmstudios.stronger.workout.WorkoutStatusEnum;
import org.junit.Before;
// import org.junit.jupiter.api.Test; cannot use the new for some reason...
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class LoggedSetServiceTest {

    private final User userMock = new User(
            "Dazekorean",
            "Martin",
            "Mosesson",
            "martin.mosesson@mail.com",
            "pass123");
    private final ExerciseCategory chestCategory = new ExerciseCategory(MuscleCategory.CHEST);
    private final Exercise benchPress = new Exercise("Flat Barbell Bench Press", chestCategory);
    private final Workout workoutMock = new Workout("Mock Workout", LocalDateTime.now(), WorkoutStatusEnum.IN_PROGRESS, userMock);
    @Mock
    private LoggedSetRepository loggedSetRepository;
    @Mock
    private ExercisePrService exercisePrService;
    @Mock
    private LoggedExerciseService loggedExerciseService;
    @InjectMocks
    private LoggedSetServiceImpl loggedSetServiceImpl;
    private LoggedSet loggedSet1;
    private LoggedSet loggedSet2;
    private LoggedSet loggedSet3;
    private LoggedSet loggedSet4;
    private LoggedExercise loggedExercise1;
    private ExercisePr exercisePr1;

    @Before
    public void setup() {
        benchPress.setId(1L);
        userMock.setId(1L);

        loggedExercise1 = new LoggedExercise(workoutMock, benchPress);

        // loggedSet1 usually used as request input
        loggedSet1 = new LoggedSet(125.0, 5, 140.1, false);
        loggedSet2 = new LoggedSet(75.0, 5, 83.4, false);
        loggedSet3 = new LoggedSet(50.0, 5, 56.2, false);
        // loggedSet4 - topLoggedSet and exercisePr
        loggedSet4 = new LoggedSet(100.0, 5, 112.5, true);

        loggedSet1.setId(1L);
        loggedSet2.setId(2L);
        loggedSet3.setId(3L);
        loggedSet4.setId(4L);

        loggedSet2.setLoggedExercise(loggedExercise1);
        loggedSet3.setLoggedExercise(loggedExercise1);
        loggedSet4.setLoggedExercise(loggedExercise1);

        // exercise pr
        exercisePr1 = new ExercisePr(loggedSet4.getWeight(), loggedSet4.getReps(),
                loggedSet4.getEstimatedOneRepMax(), userMock, benchPress, loggedSet4);

        loggedSet4.setExercisePr(exercisePr1);

        loggedExercise1.setLoggedSets(Arrays.asList(loggedSet2, loggedSet3, loggedSet4));
    }

    @Test
    public void removeLoggedSetTest__withoutUpdatingExercisePrOrTopLoggedSet() {
        LoggedSet loggedSetToBeRemoved = loggedSet2;

        LoggedExercise loggedExerciseAfterDeletion = new LoggedExercise(workoutMock, benchPress);
        loggedExerciseAfterDeletion.setLoggedSets(Arrays.asList(loggedSet3, loggedSet4));

//        when(loggedSetRepository.findById(loggedSetToBeRemoved.getId())).thenReturn(Optional.of(loggedSetToBeRemoved));

        when(loggedExerciseService.getById(loggedSetToBeRemoved.getLoggedExercise().getId()))
                .thenReturn(loggedExerciseAfterDeletion);

        List<LoggedSet> result = loggedSetServiceImpl.removeLoggedSet(loggedSetToBeRemoved);

        // loggedSet should get deleted
        verify(loggedSetRepository, times(1)).delete(loggedSetToBeRemoved);

        // should not update ExercisePr
        verify(loggedSetRepository, never()).findByRepsAndLoggedExercise_Exercise_IdAndLoggedExercise_Workout_User_Id(any(), any(), any());

        // should not update TopLoggedSet
        verify(loggedSetRepository, never()).save(any());

        Assertions.assertIterableEquals(result, loggedExerciseAfterDeletion.getLoggedSets());
    }

    @Test
    public void removeLoggedSetTest__andUpdateExercisePr() {
        LoggedSet loggedSetToBeRemoved = loggedSet4;
        loggedSetToBeRemoved.setTopLoggedSet(false);

        Integer repsToEvaluate = loggedSetToBeRemoved.getReps();
        Long exerciseIdToEvaluate = loggedSetToBeRemoved.getLoggedExercise().getExercise().getId();
        Long userId = loggedSetToBeRemoved.getLoggedExercise().getWorkout().getUser().getId();

        // should have higher EORM than loggedSet3
        LoggedSet toBeNextExercisePrSet = loggedSet2;

        LoggedExercise loggedExerciseAfterDeletion = new LoggedExercise(workoutMock, benchPress);
        loggedExerciseAfterDeletion.setLoggedSets(Arrays.asList(toBeNextExercisePrSet, loggedSet3));

        assertNull(toBeNextExercisePrSet.getExercisePr());

//        when(loggedSetRepository.findById(loggedSetToBeRemoved.getId()))
//                .thenReturn(Optional.of((loggedSetToBeRemoved)));
        when(loggedExerciseService.getById(loggedSetToBeRemoved.getLoggedExercise().getId()))
                .thenReturn(loggedExerciseAfterDeletion);
        when(loggedSetRepository.findByRepsAndLoggedExercise_Exercise_IdAndLoggedExercise_Workout_User_Id(repsToEvaluate, exerciseIdToEvaluate, userId))
                .thenReturn(Arrays.asList(loggedSet2, loggedSet3));

        List<LoggedSet> result = loggedSetServiceImpl.removeLoggedSet(loggedSetToBeRemoved);

        // loggedSet should get deleted
        verify(loggedSetRepository, times(1)).delete(loggedSetToBeRemoved);

        // should update ExercisePr
        verify(loggedSetRepository, times(1)).findByRepsAndLoggedExercise_Exercise_IdAndLoggedExercise_Workout_User_Id(repsToEvaluate, exerciseIdToEvaluate, userId);

        // toBeNextExercisePrSet should have gotten an ExercisePr
        // should not update TopLoggedSet, hence times 1 (otherwise 2)
        assertNotNull(toBeNextExercisePrSet.getExercisePr());
        verify(loggedSetRepository, times(1)).save(any());

        Assertions.assertIterableEquals(result, loggedExerciseAfterDeletion.getLoggedSets());
    }

    @Test
    public void removeLoggedSetTest__andUpdateTopLoggedSet() {

    }

    @Test
    public void updateExercisePrWhenLoggedSetIsDeletedTest__withoutHistorySets() {
        Integer repsToEvaluate = 5;
        Long exerciseId = 1L;
        Long userId = 1L;

        List<LoggedSet> historyLoggedSets = List.of();

        when(loggedSetRepository.findByRepsAndLoggedExercise_Exercise_IdAndLoggedExercise_Workout_User_Id
                (repsToEvaluate, exerciseId, userId)).thenReturn(historyLoggedSets);

        loggedSetServiceImpl.updateExercisePrWhenLoggedSetIsDeleted(repsToEvaluate, exerciseId, userId);

        verify(loggedSetRepository, never()).save(any());
    }

    @Test
    public void updateExercisePrWhenLoggedSetIsDeletedTest__WithHistorySets() {
        int repsToEvaluate = 5;
        Long exerciseId = 1L;
        Long userId = 1L;

        LoggedSet toBeNextExercisePrSet = loggedSet1;
        toBeNextExercisePrSet.setLoggedExercise(loggedExercise1);
        List<LoggedSet> historyLoggedSets = Arrays.asList(loggedSet2, loggedSet3, toBeNextExercisePrSet);

        assertNull(toBeNextExercisePrSet.getExercisePr());

        when(loggedSetServiceImpl.getByRepsAndExerciseAndUserId(repsToEvaluate, exerciseId, userId))
                .thenReturn(historyLoggedSets);

        loggedSetServiceImpl.updateExercisePrWhenLoggedSetIsDeleted(repsToEvaluate, exerciseId, userId);

        assertNotNull(toBeNextExercisePrSet.getExercisePr());
        verify(loggedSetRepository, times(1)).save(toBeNextExercisePrSet);
    }

    @Test
    public void updateTopLoggedSetWhenLoggedSetIsDeletedTest() {
        List<LoggedSet> loggedSetsAfterDeletion = Arrays.asList(loggedSet2, loggedSet3);

        assertFalse(loggedSet2.isTopLoggedSet());

        loggedSetServiceImpl.updateTopLoggedSetWhenLoggedSetIsDeleted(loggedSetsAfterDeletion);

        assertTrue(loggedSet2.isTopLoggedSet());

        verify(loggedSetRepository, times(1)).save(loggedSet2);
    }

    @Test
    public void addLoggedSetTest() {
        Long mockId = 1L;

        LoggedExercise loggedExerciseMock = loggedExercise1;
        loggedExerciseMock.setId(mockId);
        Long exerciseId = loggedExerciseMock.getExercise().getId();
        // TODO: replace later when auth is implemented
        Long userId = loggedExerciseMock.getWorkout().getUser().getId();

        LoggedSet toBeAddedLoggedSet = loggedSet1;

        assertNull(toBeAddedLoggedSet.getExercisePr());
        assertNull(toBeAddedLoggedSet.getLoggedExercise());
        assertFalse(toBeAddedLoggedSet.isTopLoggedSet());

//        when(loggedExerciseService.getById(mockId)).thenReturn(loggedExerciseMock);

        when(exercisePrService.getByRepsAndExerciseAndUserId(toBeAddedLoggedSet.getReps(), exerciseId, userId))
                .thenReturn(null);

        List<LoggedSet> result = loggedSetServiceImpl.addLoggedSet(loggedExerciseMock, toBeAddedLoggedSet);

        // assert that toBeAddedLoggedSet got the loggedExercise
        LoggedExercise resultLoggedExercise = toBeAddedLoggedSet.getLoggedExercise();
        assertEquals(loggedExerciseMock, resultLoggedExercise);

        // assert that toBeAddedLoggedSet has been set as new exercisePr
        Double resultEORM = toBeAddedLoggedSet.getExercisePr().getEstimatedOneRepMax();
        Double expectedEORM = toBeAddedLoggedSet.getEstimatedOneRepMax();
        assertEquals(expectedEORM, resultEORM);

        // assert that toBeAddedLoggedSet is also topLoggedSet
        assertTrue(toBeAddedLoggedSet.isTopLoggedSet());

        verify(loggedSetRepository, times(1)).save(toBeAddedLoggedSet);

        Assertions.assertIterableEquals(result, Arrays.asList(loggedSet2, loggedSet3, loggedSet4, loggedSet1));

    }

    @Test
    public void updateExercisePrWhenLoggedSetIsAddedTest__withoutPreviousPrs() {
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

        loggedSetServiceImpl.updateExercisePrWhenLoggedSetIsAdded(toBeAddedLoggedSet);

        // assert that toBeAddedLoggedSet has been set as new exercisePr
        Double result = toBeAddedLoggedSet.getExercisePr().getEstimatedOneRepMax();
        Double expected = toBeAddedLoggedSet.getEstimatedOneRepMax();
        assertEquals(expected, result);
    }

    @Test
    public void updateExercisePrWhenLoggedSetIsAddedTest__withPreviousPrs__withoutUpdate() {
        LoggedExercise loggedExerciseSetWillBeAddedTo = loggedExercise1;
        Long exerciseId = loggedExerciseSetWillBeAddedTo.getExercise().getId();
        // TODO: replace later when auth is implemented
        Long userId = loggedExerciseSetWillBeAddedTo.getWorkout().getUser().getId();

        LoggedSet toBeAddedLoggedSet = loggedSet2;
        toBeAddedLoggedSet.setLoggedExercise(loggedExerciseSetWillBeAddedTo);

        assertNull(toBeAddedLoggedSet.getExercisePr());

        when(exercisePrService.getByRepsAndExerciseAndUserId(toBeAddedLoggedSet.getReps(), exerciseId, userId))
                .thenReturn(exercisePr1);

        loggedSetServiceImpl.updateExercisePrWhenLoggedSetIsAdded(toBeAddedLoggedSet);

        // assert that toBeAddedLoggedSet was NOT set to new PR
        assertNull(toBeAddedLoggedSet.getExercisePr());
    }


    @Test
    public void updateExercisePrWhenLoggedSetIsAddedTest__withPreviousPrs__withUpdate() {
        LoggedExercise loggedExerciseSetWillBeAddedTo = loggedExercise1;
        Long exerciseId = loggedExerciseSetWillBeAddedTo.getExercise().getId();
        // TODO: replace later when auth is implemented
        Long userId = loggedExerciseSetWillBeAddedTo.getWorkout().getUser().getId();

        LoggedSet toBeAddedLoggedSet = loggedSet1;
        toBeAddedLoggedSet.setLoggedExercise(loggedExerciseSetWillBeAddedTo);

        assertNull(toBeAddedLoggedSet.getExercisePr());

        when(exercisePrService.getByRepsAndExerciseAndUserId(toBeAddedLoggedSet.getReps(), exerciseId, userId))
                .thenReturn(exercisePr1);

        loggedSetServiceImpl.updateExercisePrWhenLoggedSetIsAdded(toBeAddedLoggedSet);

        verify(exercisePrService, times(1)).deleteByExercisePr(exercisePr1);

        // assert that toBeAddedLoggedSet has been set as new exercisePr
        Double result = toBeAddedLoggedSet.getExercisePr().getEstimatedOneRepMax();
        Double expected = toBeAddedLoggedSet.getEstimatedOneRepMax();
        assertEquals(expected, result);
    }


    @Test
    public void isNewTopLoggedSetTest() {
        List<LoggedSet> loggedSetsMock = Arrays.asList(loggedSet2, loggedSet3);

        boolean result = loggedSetServiceImpl.isNewTopLoggedSet(loggedSet1, loggedSetsMock);
        assertTrue(result);

        result = loggedSetServiceImpl.isNewTopLoggedSet(loggedSet3, loggedSetsMock);
        assertFalse(result);

        result = loggedSetServiceImpl.isNewTopLoggedSet(loggedSet2, loggedSetsMock);
        assertFalse(result);
    }

    @Test
    public void getLoggedSetWithHighestEORMTest__withFilledList() {
        List<LoggedSet> loggedSetsMock = Arrays.asList(loggedSet1, loggedSet2, loggedSet3);

        LoggedSet result = loggedSetServiceImpl.getLoggedSetWithHighestEORM(loggedSetsMock);
        assertNotNull(result);

        Double expected = loggedSet1.getEstimatedOneRepMax();
        assertEquals(expected, result.getEstimatedOneRepMax());
    }

    @Test
    public void getLoggedSetWithHighestEORMTest__withEmptyList() {
        List<LoggedSet> emptyLoggedSets = new ArrayList<>();

        LoggedSet result = loggedSetServiceImpl.getLoggedSetWithHighestEORM(emptyLoggedSets);

        assertNull(result);
    }

    @Test
    public void updateTopLoggedSetWhenLoggedSetIsAddedTest__withoutUpdating() {
        LoggedSet loggedSetToBeAdded = loggedSet2;
        List<LoggedSet> previousLoggedSets = Arrays.asList(loggedSet1, loggedSet3);

        loggedSetServiceImpl.updateTopLoggedSetWhenLoggedSetIsAdded(loggedSetToBeAdded, previousLoggedSets);

        verify(loggedSetRepository, never()).save(any(LoggedSet.class));

        assertFalse(loggedSetToBeAdded.isTopLoggedSet());
    }

    @Test
    public void updateTopLoggedSetWhenLoggedSetIsAddedTest__withUpdating() {
        LoggedSet loggedSetToBeAdded = loggedSet1;
        List<LoggedSet> previousLoggedSets = Arrays.asList(loggedSet2, loggedSet4);

        loggedSetServiceImpl.updateTopLoggedSetWhenLoggedSetIsAdded(loggedSetToBeAdded, previousLoggedSets);

        // save should update loggedSetMock4
        verify(loggedSetRepository, times(1)).save(loggedSet4);

        // previous topLoggedSet should be replaced
        assertFalse(loggedSet4.isTopLoggedSet());

        // newly added set should be topLoggedSet
        assertTrue(loggedSet1.isTopLoggedSet());
    }

    @Test
    public void updateTopLoggedSetWhenLoggedSetIsAddedTest__withNoPreviousSets() {
        LoggedSet loggedSetToBeAdded = loggedSet1;
        List<LoggedSet> previousLoggedSets = List.of();

        loggedSetServiceImpl.updateTopLoggedSetWhenLoggedSetIsAdded(loggedSetToBeAdded, previousLoggedSets);

        verify(loggedSetRepository, never()).save(any(LoggedSet.class));

        assertTrue(loggedSet1.isTopLoggedSet());
    }

}
