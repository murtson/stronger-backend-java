package com.pmstudios.stronger;

import com.pmstudios.stronger.pojo.Exercise;
import com.pmstudios.stronger.respository.ExerciseRepository;
import com.pmstudios.stronger.service.ExerciseService;
import com.pmstudios.stronger.service.ExerciseServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExerciseServiceTest {

    // TODO: ta reda på vad det här gör
    @Mock
    private ExerciseRepository exerciseRepository;

    // TODO: ta reda på vad det här gör
    @InjectMocks
    private ExerciseServiceImpl exerciseService;

    Exercise mockExercise1 = new Exercise(1, "Bench Press");
    Exercise mockExercise2 = new Exercise(2, "Squats");
    List<Exercise> mockExercises = Arrays.asList(mockExercise1, mockExercise2);


    @Test
    public void getExercisesFromRepoTest() {
        when(exerciseRepository.getExercises()).thenReturn(mockExercises);
        List<Exercise> result = exerciseService.getExercises();

        assertEquals("Bench Press", result.get(0).getName());
        assertEquals(2, result.get(1).getId());

    }

    @Test
    public void getExerciseIndexTest() {
        when(exerciseRepository.getExercises()).thenReturn(mockExercises);
        when(exerciseRepository.getExercise(0)).thenReturn(mockExercise1);
        when(exerciseRepository.getExercise(1)).thenReturn(mockExercise2);

        int valid = exerciseService.getExerciseIndex(mockExercise1.getId());
        int notFound = exerciseService.getExerciseIndex(123);

        assertEquals(0, valid);
        assertEquals(Constants.NOT_FOUND, notFound);

    }

    @Test
    public void getExerciseTest() {
        when(exerciseRepository.getExercises()).thenReturn(mockExercises);
        when(exerciseRepository.getExercise(0)).thenReturn(mockExercise1);
        when(exerciseRepository.getExercise(1)).thenReturn(mockExercise2);

        int id = mockExercise2.getId();
        Exercise result = exerciseService.getExercise(id);
        assertEquals(mockExercise2, result);

    }

}
