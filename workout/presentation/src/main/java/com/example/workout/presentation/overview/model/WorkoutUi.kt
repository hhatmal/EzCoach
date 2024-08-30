package com.example.workout.presentation.overview.model

data class WorkoutUi(
    val id: Int,
    val date: String,
    val exercises: List<ExerciseUi>
)