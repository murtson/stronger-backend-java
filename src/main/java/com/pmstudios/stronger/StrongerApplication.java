package com.pmstudios.stronger;

import com.pmstudios.stronger.entity.Exercise;
import com.pmstudios.stronger.entity.ExerciseCategory;
import com.pmstudios.stronger.entity.User;
import com.pmstudios.stronger.respository.ExerciseCategoryRepository;
import com.pmstudios.stronger.respository.ExerciseRepository;
import com.pmstudios.stronger.respository.UserRepository;
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

	@Autowired
	UserRepository userRepository;

	public static void main(String[] args) {
		SpringApplication.run(StrongerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		User[] users = new User[] {
				new User("Olle", "Johan"),
				new User("Kalle", "Svensson")
		};

		for (User user : users) {
			userRepository.save(user);
		}

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
