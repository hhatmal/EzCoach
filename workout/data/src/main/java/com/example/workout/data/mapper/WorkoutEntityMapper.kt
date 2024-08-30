package com.example.workout.data.mapper

import com.example.workout.data.local.ExerciseEntity
import com.example.workout.domain.Exercise
import com.example.workout.domain.ExerciseSet
import com.example.workout.domain.Workout

fun List<ExerciseEntity>.toWorkout(): Workout {
    val exercises: MutableList<Exercise> = mutableListOf()
    val map: HashMap<String, MutableList<ExerciseSet>> = HashMap()
    this.forEachIndexed { index, exercise ->
        // TODO: match by name instead of id in future
        // this wont work for multiple exercise on same day
        if (!map.containsKey(exercise.name)) {
            map[exercise.name] = mutableListOf()
        }

        val exerciseSets: MutableList<ExerciseSet>? = map[exercise.name]
        exerciseSets?.add(
            ExerciseSet(
                id = exercise.setNumber,
                exerciseId = 0,
                targetWeight = exercise.targetWeight,
                targetReps = exercise.targetReps,
                completedReps = exercise.completedReps
            )
        )
    }

    this.forEachIndexed { index, exercise ->
        // avoid duplicate entries
        if (!exercises.any{ (it.name == exercise.name) }) {
            exercises.add(
                Exercise(
                    id = index,
                    name = exercise.name,
                    instructions = exercise.instructions,
                    imageUrl = exercise.imageUrl,
                    comments = exercise.comments,
                    exerciseSets = map.get(exercise.name)
                )
            )
        }
    }

    return Workout(
        id = 0,
        date = if (exercises.size == 0) "" else this[0].date.toString(),
        exercises = exercises
    )
}

fun Workout.toExerciseEntityList(): List<ExerciseEntity> {
    val exerciseEntities: MutableList<ExerciseEntity> = mutableListOf()
    for (exercise in exercises) {
        exercise.exerciseSets?.let {
            for (i in exercise.exerciseSets!!.indices) {
                exerciseEntities.add(
                    ExerciseEntity(
                        date = date.toLong(),
                        name = exercise.name,
                        instructions = exercise.instructions,
                        imageUrl = exercise.imageUrl,
                        comments = exercise.comments,
                        setNumber = i,
                        targetWeight = exercise.exerciseSets!![i].targetWeight,
                        targetReps = exercise.exerciseSets!![i].targetReps,
                        completedReps = exercise.exerciseSets!![i].completedReps
                    )
                )
            }
        }
    }
    return exerciseEntities
}
