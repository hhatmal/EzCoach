package com.example.workout.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(primaryKeys = ["date", "name"])
data class ExerciseEntity(
    val date: Long,
    val name: String,
    val instructions: String,
    val imageUrl: String,
    val comments: String,
    val setNumber: Int,
    val targetWeight: Float,
    val targetReps: Int,
    val completedReps: Int
)