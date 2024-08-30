package com.example.workout.data.network

import com.example.workout.data.dto.WorkoutDto
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Query

interface WorkoutApi {
    @GET("/workouts")
    suspend fun getWorkouts(): List<WorkoutDto>

    @GET("/workout")
    suspend fun getWorkout(@Query("date") epochTime: Long): WorkoutDto

    @PUT("/workout")
    suspend fun putWorkout(@Body workout: WorkoutDto): WorkoutDto
}