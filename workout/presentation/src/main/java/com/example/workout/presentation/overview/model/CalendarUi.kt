package com.example.workout.presentation.overview.model

data class CalendarUi(
    val selectedItem: CalendarItemUi, // when this changes we need to update workout ui
    val calendarItems: List<CalendarItemUi>
)