package com.pmstudios.stronger.workout;

import com.pmstudios.stronger.exception.EntityNotFoundException;
import com.pmstudios.stronger.exception.WorkoutNotFoundException;
import com.pmstudios.stronger.user.UserService;
import lombok.AllArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@Service
public class WorkoutServiceImpl implements WorkoutService {

    WorkoutRepository workoutRepository;
    UserService userService;

    @Override
    public Workout getWorkoutById(Long id) {
        return workoutRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(id, Workout.class));
    }

    @Override
    public Workout saveWorkout(Workout workout) {
        checkValidWorkoutStatus(workout);
        return workoutRepository.save(workout);
    }

    @Override
    public Workout completeWorkout(Long id) {
        return null;
    }

    @Override
    public void deleteWorkoutById(Long id) {
        workoutRepository.deleteById(id);
    }

    @Override
    public List<Workout> getAllWorkouts() {
        return workoutRepository.findAll();
    }

    @Override
    public List<Workout> getWorkoutsByUserId(Long userId) {
        return workoutRepository.findByUserId(userId);
    }

    @Override
    public List<Workout> getUserWorkoutsBetweenDates(LocalDateTime fromDate, LocalDateTime toDate, Long userId) {
        if (fromDate.isAfter(toDate)) throw new DataIntegrityViolationException("fromDate must be before toDate");
        return workoutRepository.findAllByStartDateBetweenAndUserId(fromDate, toDate, userId);
    }

    @Override
    public Optional<Workout> getWorkoutByDateAndUserId(LocalDate date, Long userId) {
        LocalDateTime dateTime = date.atStartOfDay();
        return workoutRepository.findByStartDateAndUserId(dateTime, userId);
    }

    private void checkValidWorkoutStatus(Workout workout) {
        // TODO: is this the right way to check for this? Or should this be done in validators or a constraint in the db?
        LocalDateTime startDate = workout.getStartDate();
        WorkoutStatusEnum status = workout.getWorkoutStatus();

        if (startDate.isAfter(LocalDateTime.now()) && status != WorkoutStatusEnum.PLANNED) {
            throw new DataIntegrityViolationException("A scheduled workout needs to have 'PLANNED' status");
        }

        if (status == WorkoutStatusEnum.COMPLETED) {
            throw new DataIntegrityViolationException("You cannot create a workout with 'COMPLETED' status");
        }

    }

}
