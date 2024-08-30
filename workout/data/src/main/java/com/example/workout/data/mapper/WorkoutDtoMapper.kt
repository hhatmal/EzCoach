package com.example.workout.data.mapper

import com.example.workout.data.dto.ExerciseDto
import com.example.workout.data.dto.ExerciseSetDto
import com.example.workout.data.dto.WorkoutDto
import com.example.workout.domain.Exercise
import com.example.workout.domain.ExerciseSet
import com.example.workout.domain.Workout

fun WorkoutDto.toWorkout(): Workout {
    val exercises: MutableList<Exercise> = mutableListOf()
    for (exerciseDto in exerciseDtos) {
        exercises.add(exerciseDto.toExercise())
    }
    return Workout(
        id = id,
        date = date,
        exercises = exercises
    )
}

fun ExerciseDto.toExercise(): Exercise {
    val exerciseSets: MutableList<ExerciseSet> = mutableListOf()
    exerciseSetDtos?.let {
        for (exerciseSetDto in exerciseSetDtos) {
            exerciseSets.add(exerciseSetDto.toExerciseSet())
        }
    }
    return Exercise(
        id = id,
        name = name,
        instructions = instructions,
        imageUrl = imageUrl,
        comments = comments,
        exerciseSets = exerciseSets
    )
}

fun ExerciseSetDto.toExerciseSet(): ExerciseSet {
    return ExerciseSet(
        id = id,
        exerciseId = setNumber,
        targetWeight = targetWeight,
        targetReps = targetReps,
        completedReps = completedReps
    )
}

fun Workout.toWorkoutDto(): WorkoutDto {
    val exerciseDtos: MutableList<ExerciseDto> = mutableListOf()
    for (exercise in exercises) {
        exerciseDtos.add(exercise.toExerciseDto())
    }
    return WorkoutDto(
        id = id,
        date = date,
        exerciseDtos = exerciseDtos
    )
}

fun Exercise.toExerciseDto(): ExerciseDto {
    val exerciseSetDtos: MutableList<ExerciseSetDto> = mutableListOf()
    exerciseSets?.let {
        for (exerciseSet in exerciseSets!!) {
            exerciseSetDtos.add(exerciseSet.toExerciseSetDto())
        }
    }
    return ExerciseDto(
        id = id,
        name = name,
        instructions = instructions,
        imageUrl = imageUrl,
        comments = comments,
        exerciseSetDtos = exerciseSetDtos
    )
}

fun ExerciseSet.toExerciseSetDto(): ExerciseSetDto {
    return ExerciseSetDto(
        id = id,
        setNumber = exerciseId,
        targetWeight = targetWeight,
        targetReps = targetReps,
        completedReps = completedReps
    )
}