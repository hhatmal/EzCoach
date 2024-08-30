package com.example.workout.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ExerciseEntity::class
    ],
    exportSchema = false,
    version = 2
)
abstract class WorkoutDatabase: RoomDatabase() {
    abstract val workoutDao: WorkoutDao
}