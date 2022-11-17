package com.pmstudios.stronger;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@SpringBootTest
@AutoConfigureMockMvc
class StrongerApplicationTests {


    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

	/*// TODO: ta reda på vad MockMvc gör
	@Autowired
	private MockMvc mockMvc;

	@Autowired
	ExerciseRepository exerciseRepository;

	@Test
	void contextLoads() {
		assertNotNull(mockMvc);
		assertNotNull(exerciseRepository);
	}

	private Exercise[] exercises = new Exercise[] {
			new Exercise(1, "Bench Press"),
			new Exercise(2, "Squat"),
			new Exercise(3, "Deadlift")
	};

	@BeforeEach
	void setup() {
		for (Exercise exercise : exercises) {
			exerciseRepository.saveExercise(exercise);
		}
	}

	@AfterEach
	void clear() { exerciseRepository.getExercises().clear(); }

	@Test
	public void getExerciseByIdTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/exercise/1");

		mockMvc.perform(request)
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.id").value(exercises[0].getId()))
				.andExpect(jsonPath("$.name").value(exercises[0].getName()));
	}

	@Test
	public void getAllExercisesTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/exercise/all");

		mockMvc.perform(request)
				.andExpect(status().is2xxSuccessful())
				.andExpect(content().contentType(MediaType.APPLICATION_JSON))
				.andExpect(jsonPath("$.size()").value(exercises.length))
				.andExpect(jsonPath("$.[?(@.id == \"2\" && @.name == \"Squat\")]").exists());

	}

	@Test
	public void exerciseNotFoundTest() throws Exception {
		RequestBuilder request = MockMvcRequestBuilders.get("/exercise/4");

		mockMvc.perform(request).andExpect(status().isNotFound());

	}

}
