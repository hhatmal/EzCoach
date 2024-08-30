package com.example.workout.presentation.overview.model

data class ExerciseUi(
    val id: Int = 0,
    val name: String = "",
    val instructions: String = "",
    val imageUrl: String = "",
    val comments: String = "",
    val sets: List<ExerciseSetUi>? = listOf(),
    val setCount: Int? = sets?.size,
    val complete: Boolean = sets == null || sets.none { it.completedReps != it.targetReps }
)
