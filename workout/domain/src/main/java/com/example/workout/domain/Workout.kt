package com.example.workout.domain

data class Workout(
    val id: Int,
    val date: String,
    val exercises: List<Exercise>
)
