package com.pmstudios.stronger.service;

import com.pmstudios.stronger.entity.Exercise;
import com.pmstudios.stronger.entity.ExerciseCategory;
import com.pmstudios.stronger.entity.LoggedExercise;
import com.pmstudios.stronger.respository.ExerciseRepository;
import com.pmstudios.stronger.service.implementation.ExerciseServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ExerciseServiceTest {

    @Mock
    private ExerciseRepository exerciseRepository;
    @Mock
    private ExerciseService exerciseService;



    ExerciseCategory exerciseCategoryChest = new ExerciseCategory(ExerciseCategory.MuscleCategory.CHEST);
    List<LoggedExercise> loggedExercises;

    Exercise mockExercise1 = new Exercise("Flat Barbell Bench Press");
    Exercise mockExercise2 = new Exercise( "Barbell Squat");
    List<Exercise> mockExercises = Arrays.asList(mockExercise1, mockExercise2);


    @Test
    public void getExercisesFromRepoTest() {
        when(exerciseRepository.findAll()).thenReturn(mockExercises);
        List<Exercise> result = exerciseService.getExercises();
        assertEquals("Bench Press", result.get(0).getName());
        assertEquals(2, result.get(1).getId());
    }

    @Test
    public void getExerciseIndexTest() {
//        when(exerciseRepository.getExercises()).thenReturn(mockExercises);
//        when(exerciseRepository.getExercise(0)).thenReturn(mockExercise1);
//        when(exerciseRepository.getExercise(1)).thenReturn(mockExercise2);
//
//        int valid = exerciseService.getExerciseIndex(mockExercise1.getId());
//        int notFound = exerciseService.getExerciseIndex(123);
//
//        assertEquals(0, valid);
//        Assertions.assertEquals(Constants.NOT_FOUND, notFound);
    }

    @Test
    public void getExerciseTest() {
//        when(exerciseRepository.getExercises()).thenReturn(mockExercises);
//        when(exerciseRepository.getExercise(0)).thenReturn(mockExercise1);
//        when(exerciseRepository.getExercise(1)).thenReturn(mockExercise2);
//
//        int id = mockExercise2.getId();
//        Exercise result = exerciseService.getExercise(id);
//        assertEquals(mockExercise2, result);

    }

}
