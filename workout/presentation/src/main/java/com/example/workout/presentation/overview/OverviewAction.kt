package com.example.workout.presentation.overview

import com.example.workout.presentation.overview.model.CalendarItemUi
import com.example.workout.presentation.overview.model.ExerciseUi

sealed interface OverviewAction {
    data class OnDateClick(val calendarItemUi: CalendarItemUi) : OverviewAction
    data class OnCardClick(val exerciseUi: ExerciseUi) : OverviewAction
    data class OnDetailSetRepClick(val exerciseRep: Pair<String, Int>) : OverviewAction
}