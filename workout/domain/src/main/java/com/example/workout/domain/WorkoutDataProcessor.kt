package com.example.workout.domain

class WorkoutDataProcessor {
    fun updatedRepCount(targetRepCount: Int, currentRepCount: Int): Int {
        if (currentRepCount >= 0) {
            return currentRepCount - 1
        }
         return targetRepCount
    }
}