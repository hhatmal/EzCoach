package com.example.workout.presentation.overview.mapper

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.workout.domain.CalendarItems
import com.example.workout.presentation.overview.model.CalendarItemUi
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun CalendarItems.toCalendarItemsUi(): List<CalendarItemUi> {
    val calendarItemsUi: MutableList<CalendarItemUi> = mutableListOf()
    for (i in dates.indices) {
        val date = dates[i]
        val calendarItemUi = CalendarItemUi(
            id = i,
            year = date.year,
            month = date.monthValue,
            dayNumber = date.dayOfMonth.toString(),
            dayWord = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
            isSelected = false,
            isComplete = false
        )
        calendarItemsUi.add(calendarItemUi)
    }
    return calendarItemsUi
}

