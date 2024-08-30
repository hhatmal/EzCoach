package com.example.workout.presentation.overview.model

data class ExerciseSetUi(
    val id: Int,
    val exerciseId: Int,
    val targetWeight: Float,
    val targetReps: Int,
    val completedReps: Int,
    val setComplete: Boolean = completedReps >= targetReps
)
