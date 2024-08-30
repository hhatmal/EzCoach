package com.example.workout.domain

import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

interface WorkoutRepository {
    fun getWorkout(): Flow<Workout>
    suspend fun fetchWorkout(localDate: LocalDate): EmptyResult<DataError>
    suspend fun updateExerciseSetRepCount(workout: Workout): EmptyResult<DataError>
}