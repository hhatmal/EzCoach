package com.example.workout.presentation.overview.model

data class OverviewState(
    val calendarUi: CalendarUi = CalendarUi(
        calendarItems = listOf(
            CalendarItemUi(id = 1, year = 1, month = 5, dayNumber = "1", dayWord = "Mon", isSelected = false, isComplete = true),
            CalendarItemUi(id = 2, year = 2, month = 5, dayNumber = "2", dayWord = "Tue", isSelected = false, isComplete = true),
            CalendarItemUi(id = 3, year = 3, month = 5, dayNumber = "3", dayWord = "Wed", isSelected = false, isComplete = true),
            CalendarItemUi(id = 4, year = 4, month = 5, dayNumber = "4", dayWord = "Thur", isSelected = false, isComplete = false),
            CalendarItemUi(id = 5, year = 5, month = 5, dayNumber = "5", dayWord = "Fri", isSelected = false, isComplete = false)
        ),
        selectedItem = CalendarItemUi(id = 1, year = 1, month = 5, dayNumber = "1", dayWord = "Mon", isSelected = false, isComplete = true)
    ),
    val currentWorkout: WorkoutUi = WorkoutUi(id = 0, date = "",  exercises = listOf())
)