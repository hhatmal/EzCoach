package com.example.workout.domain

data class ExerciseSet(
    val id: Int,
    val exerciseId: Int,
    val targetWeight: Float,
    val targetReps: Int,
    val completedReps: Int
)