package com.example.workout.data.dto

import kotlinx.serialization.Serializable

@Serializable
data class ExerciseSetDto(
    val id: Int,
    val setNumber: Int,
    val targetWeight: Float,
    val targetReps: Int,
    val completedReps: Int
)