package com.pmstudios.stronger;

import com.pmstudios.stronger.entity.ExerciseCategory;
import com.pmstudios.stronger.entity.User;
import com.pmstudios.stronger.respository.ExerciseCategoryRepository;
import com.pmstudios.stronger.respository.ExerciseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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

		ExerciseCategory[] exerciseCategories = new ExerciseCategory[] {
				new ExerciseCategory(ExerciseCategory.MuscleCategory.CHEST),
				new ExerciseCategory(ExerciseCategory.MuscleCategory.LEGS),
				new ExerciseCategory(ExerciseCategory.MuscleCategory.BACK)
		};

		for (ExerciseCategory exerciseCategory : exerciseCategories) {
			exerciseCategoryRepository.save(exerciseCategory);
		}
	}
}
