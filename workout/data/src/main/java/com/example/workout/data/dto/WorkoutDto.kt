package com.example.workout.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class WorkoutDto(
    val id: Int,
    val date: String,
    @SerialName("exercises")
    val exerciseDtos: List<ExerciseDto>
)
