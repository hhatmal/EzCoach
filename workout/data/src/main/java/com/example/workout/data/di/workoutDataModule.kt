package com.example.workout.data.di

import androidx.room.Room
import com.example.workout.data.WorkoutRepositoryImpl
import com.example.workout.data.LocalWorkoutDataSource
import com.example.workout.data.RemoteWorkoutDataSource
import com.example.workout.data.local.WorkoutDatabase
import com.example.workout.domain.WorkoutRepository
import com.example.workout.domain.WorkoutDataProcessor
import com.example.workout.domain.CalendarDataProcessor
import okhttp3.OkHttpClient
import org.koin.android.ext.koin.androidApplication
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val workoutDataModule = module {
    single { provideWorkoutApi(get()) }
    single { RemoteWorkoutDataSource(get()) }
    single {
        Room.databaseBuilder(
            androidApplication(),
            WorkoutDatabase::class.java,
            "workout"
        ).fallbackToDestructiveMigration().build()
    }
    single { get<WorkoutDatabase>().workoutDao }
    single { LocalWorkoutDataSource(get()) }
    singleOf(::CalendarDataProcessor)
    singleOf(::WorkoutDataProcessor)
    singleOf(::WorkoutRepositoryImpl).bind<WorkoutRepository>()
}