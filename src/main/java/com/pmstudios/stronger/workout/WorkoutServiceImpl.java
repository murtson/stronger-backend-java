package com.pmstudios.stronger.workout;

import com.pmstudios.stronger.loggedExercise.LoggedExerciseService;
import com.pmstudios.stronger.user.User;
import com.pmstudios.stronger.exception.EntityNotFoundException;
import com.pmstudios.stronger.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Lazy;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@Service
public class WorkoutServiceImpl implements WorkoutService {

    WorkoutRepository workoutRepository;
    UserService userService;

    @Override
    public Workout getWorkout(Long id) {

        return workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Workout.class));

        // remove empty LoggedSets
//        workout.getLoggedExercises().forEach(loggedExercise -> {
//            if(loggedExercise.getLoggedSets().isEmpty())
//                loggedExerciseService.deleteLoggedExercise(loggedExercise.getId());
//        });


    }

    @Override
    public Workout createWorkout(Workout workout, Long userId) {
        checkValidWorkoutStatus(workout);
        User user = userService.getUser(userId);
        workout.setUser(user);
        return workoutRepository.save(workout);
    }

    @Override
    public Workout completeWorkout(Long id) {
        return null;
    }

    @Override
    public void deleteWorkout(Long id) {
        workoutRepository.deleteById(id);
    }

    @Override
    public List<Workout> getWorkouts() {
        return workoutRepository.findAll();
    }

    @Override
    public List<Workout> getUserWorkouts(Long userId) {
        return workoutRepository.findByUserId(userId);
    }

    @Override
    public List<Workout> getUserWorkoutsBetweenDates(LocalDateTime fromDate, LocalDateTime toDate, Long userId) {
        if(fromDate.isAfter(toDate)) throw new DataIntegrityViolationException("fromDate must be before toDate");
        return workoutRepository.findAllByStartDateBetweenAndUserId(fromDate, toDate, userId);
    }

    private void checkValidWorkoutStatus(Workout workout) {
        // TODO: is this the right way to check for this? Or should this be done in validators or a constraint in the db?
       LocalDateTime startDate = workout.getStartDate();
       WorkoutStatus status = workout.getWorkoutStatus();

       if(startDate.isAfter(LocalDateTime.now()) && status != WorkoutStatus.PLANNED) {
           throw new DataIntegrityViolationException("A scheduled workout needs to have 'PLANNED' status");
       }

       if(status == WorkoutStatus.COMPLETED) {
           throw new DataIntegrityViolationException("You cannot create a workout with 'COMPLETED' status");
       }

    }

}
