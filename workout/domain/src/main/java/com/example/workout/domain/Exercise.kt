package com.example.workout.domain

data class Exercise(
    val id: Int = 0,
    val name: String = "",
    val instructions: String = "",
    val imageUrl: String = "",
    val comments: String = "",
    val exerciseSets: List<ExerciseSet>? = listOf(),
)