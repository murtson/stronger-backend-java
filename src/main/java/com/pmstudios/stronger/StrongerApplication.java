package com.pmstudios.stronger;

import com.pmstudios.stronger.pojo.Exercise;
import com.pmstudios.stronger.pojo.ExerciseCategory;
import com.pmstudios.stronger.respository.ExerciseCategoryRepository;
import com.pmstudios.stronger.respository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;


@SpringBootApplication
public class StrongerApplication implements CommandLineRunner {

	@Autowired
	ExerciseRepository exerciseRepository;

	@Autowired
	ExerciseCategoryRepository exerciseCategoryRepository;

	public static void main(String[] args) {
		SpringApplication.run(StrongerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Exercise[] exercises = new Exercise[] {
				new Exercise(1L,"Bench press"),
				new Exercise(2L,"Squats"),
				new Exercise(3L,"Deadlift"),
		};

		for (Exercise exercise : exercises) {
			exerciseRepository.save(exercise);
		}

		ExerciseCategory[] exerciseCategories = new ExerciseCategory[] {
				new ExerciseCategory(1L, ExerciseCategory.MuscleCategory.CHEST),
				new ExerciseCategory(2L, ExerciseCategory.MuscleCategory.LEGS),
				new ExerciseCategory(3L, ExerciseCategory.MuscleCategory.BACK)
		};

		for (ExerciseCategory exerciseCategory : exerciseCategories) {
			exerciseCategoryRepository.save(exerciseCategory);
		}
	}
}
