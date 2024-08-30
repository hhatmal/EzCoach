package com.example.workout.data

import com.example.core.domain.util.DataError
import com.example.core.domain.util.Result
import com.example.workout.data.mapper.toWorkout
import com.example.workout.data.mapper.toWorkoutDto
import com.example.workout.data.network.WorkoutApi
import com.example.workout.domain.Workout

class RemoteWorkoutDataSource(
    private val workoutApi: WorkoutApi,
) {
    suspend fun getWorkout(epochTime: Long): Result<Workout, DataError.Network> {
        val workout = workoutApi.getWorkout(epochTime).toWorkout()
        return Result.Success(workout)
    }

    suspend fun putWorkout(workout: Workout): Result<Workout, DataError.Network> {
        val updatedWorkout = workoutApi.putWorkout(workout.toWorkoutDto())
            .toWorkout()
        return Result.Success(updatedWorkout)
    }
}