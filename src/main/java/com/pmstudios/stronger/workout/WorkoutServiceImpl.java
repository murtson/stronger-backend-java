package com.pmstudios.stronger.workout;

import com.pmstudios.stronger.exception.BadRequestException;
import com.pmstudios.stronger.exception.EntityNotFoundException;
import com.pmstudios.stronger.user.UserService;
import lombok.AllArgsConstructor;
import org.hibernate.jdbc.Work;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
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
    public List<Workout> getUserWorkoutsBetweenDates(LocalDate startDate, LocalDate endDate, Long userId) {
        if (startDate.isAfter(endDate)) throw new DataIntegrityViolationException("fromDate must be before toDate");

        LocalDateTime startOfDay = startDate.atStartOfDay();
        LocalDateTime endOfDay = endDate.atTime(LocalTime.MAX);
        return workoutRepository.findAllByStartDateBetweenAndUserId(startOfDay, endOfDay, userId);
    }

    @Override
    public Optional<Workout> getWorkoutByDateAndUserId(LocalDate date, Long userId) {
        LocalDateTime startOfDay = date.atStartOfDay();
        LocalDateTime endOfDay = date.atTime(LocalTime.MAX);

        // Returns a list from repository, but should theoretically only be one
        return workoutRepository.findAllByStartDateBetweenAndUserId(startOfDay, endOfDay, userId)
                .stream().findFirst();
    }

    @Override
    public Workout createWorkout(Workout workout, LocalDateTime startDateTime, Long userId) {
        checkDuplicateWorkouts(startDateTime.toLocalDate(), userId);
        checkValidWorkoutStatus(workout);
        return workoutRepository.save(workout);
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

    private void checkDuplicateWorkouts(LocalDate startDate, Long userId) {
        // Checking to see if there already is a workout for this date
        this.getWorkoutByDateAndUserId(startDate, userId).ifPresent(workout -> {
            throw new BadRequestException("You already have a workout for this date: " + startDate);
        });
    }

}
