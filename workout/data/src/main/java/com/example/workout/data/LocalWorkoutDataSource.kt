package com.example.workout.data

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.workout.data.local.WorkoutDao
import com.example.workout.data.mapper.toExerciseEntityList
import com.example.workout.data.mapper.toWorkout
import com.example.workout.domain.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

typealias WorkoutId = Int

// setup room
class LocalWorkoutDataSource(
    private val workoutDao: WorkoutDao,
) {
    // insert into room
    suspend fun upsertWorkout(workout: Workout): Result<WorkoutId, DataError.Local> {
        val targetWorkout = workoutDao
            .upsertExercises(workout.toExerciseEntityList())

        return Result.Success(workout.id)
    }

    // need to figure out way to do this with epochtime only?
    fun getWorkout(): Flow<Workout> {
        return workoutDao.getExercises()
            .map { it.toWorkout() }
    }
}