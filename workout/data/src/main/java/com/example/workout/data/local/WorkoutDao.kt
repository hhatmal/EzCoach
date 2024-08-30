package com.example.workout.data.local

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow

@Dao
interface WorkoutDao {
    @Upsert
    suspend fun upsertExercise(exercise: ExerciseEntity)

    @Upsert
    suspend fun upsertExercises(exercises: List<ExerciseEntity>)

    @Query("SELECT * FROM exerciseentity")
    fun getExercises(): Flow<List<ExerciseEntity>>

    @Query("DELETE FROM exerciseentity WHERE date=:date AND name=:name")
    suspend fun deleteExercise(date: Long, name: String)

    @Query("DELETE FROM exerciseentity")
    suspend fun deleteExercises()
}