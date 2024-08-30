package com.example.workout.presentation.overview.di

import com.example.workout.presentation.overview.viewmodel.OverviewViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val overviewViewModelModule = module {
    viewModelOf(::OverviewViewModel)
}