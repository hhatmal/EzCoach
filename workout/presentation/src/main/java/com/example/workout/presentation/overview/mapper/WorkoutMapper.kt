package com.example.workout.presentation.overview.mapper

import com.example.workout.domain.Exercise
import com.example.workout.domain.ExerciseSet
import com.example.workout.domain.Workout
import com.example.workout.presentation.overview.model.ExerciseUi
import com.example.workout.presentation.overview.model.ExerciseSetUi
import com.example.workout.presentation.overview.model.WorkoutUi

fun Workout.toWorkoutUi(): WorkoutUi {
    val exerciseUis: MutableList<ExerciseUi> = mutableListOf()
    for (i in exercises.indices) {
        exerciseUis.add(exercises[i].toExerciseUi())
    }
    return WorkoutUi(
        id = id,
        date = date,
        exercises = exerciseUis
    )
}

fun Exercise.toExerciseUi(): ExerciseUi {
    val exerciseSetUis: MutableList<ExerciseSetUi> = mutableListOf()

    exerciseSets?.let {
        for (exerciseSet in exerciseSets!!) {
            exerciseSetUis.add(exerciseSet.toExerciseSetUi())
        }
    }

    return ExerciseUi(
        id = id,
        name = name,
        instructions = instructions,
        imageUrl = imageUrl,
        comments = comments,
        sets = exerciseSetUis
    )
}

fun ExerciseSet.toExerciseSetUi(): ExerciseSetUi {
    return ExerciseSetUi(
        id = id,
        exerciseId = exerciseId,
        targetWeight = targetWeight,
        targetReps = targetReps,
        completedReps = completedReps,
        setComplete = completedReps >= targetReps
    )
}

fun WorkoutUi.toWorkout(): Workout {
    val exerciseList: MutableList<Exercise> = mutableListOf()
    for (exercise in exercises) {
        exerciseList.add(exercise.toExercise())
    }
    return Workout(
        id = id,
        date = date,
        exercises = exerciseList
    )
}

fun ExerciseUi.toExercise(): Exercise {
    val exerciseSets: MutableList<ExerciseSet> = mutableListOf()
    sets?.let {
        for (set in sets) {
            exerciseSets.add(set.toExerciseSet())
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

fun ExerciseSetUi.toExerciseSet(): ExerciseSet {
    return ExerciseSet(
        id = id,
        exerciseId = exerciseId,
        targetWeight = targetWeight,
        targetReps = targetReps,
        completedReps = completedReps
    )
}