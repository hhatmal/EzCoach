package com.example.workout.data.di

import com.example.workout.data.network.WorkoutApi
import retrofit2.Retrofit

fun provideWorkoutApi(retrofit: Retrofit): WorkoutApi =
    retrofit.create(WorkoutApi::class.java)