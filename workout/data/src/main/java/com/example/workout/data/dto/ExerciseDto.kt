package com.example.workout.data.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ExerciseDto(
    val id: Int = 0,
    val name: String = "",
    val instructions: String = "",
    val imageUrl: String = "",
    val comments: String = "",
    @SerialName("exerciseSets")
    val exerciseSetDtos: List<ExerciseSetDto>? = listOf(),
)