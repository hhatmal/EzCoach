package com.example.workout.domain

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import java.util.stream.Collectors
import java.util.stream.Stream

class CalendarDataProcessor {
    @Suppress("NewApi")
    // if current day  is wed 5, then we want Mon, Tue, Wed, Thur, Fri
    fun getCalendarItems(currentDate: LocalDate): CalendarItems {
        val dates = Stream
            .iterate(currentDate.minusDays(2)) { date ->
                date.plusDays(1)
            }.limit(5)
            .collect(Collectors.toList())
        return CalendarItems(
            dates = dates
        )
    }

    @Suppress("NewApi")
    fun getLocalDate(year: Int, month: Int, day: Int): LocalDate {
/*        year = date.year.toString(),
        dayWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
        dayMonth = date.dayOfMonth.toString(),*/
        // year, month, day as int
        return LocalDate.of(year, month, day)
    }


    @Suppress("NewApi")
    fun isSelectedDate(year: Int, month: Int, day: Int): LocalDate {
        /*        year = date.year.toString(),
                dayWeek = date.dayOfWeek.getDisplayName(TextStyle.SHORT, Locale.getDefault()),
                dayMonth = date.dayOfMonth.toString(),*/
        // year, month, day as int
        return LocalDate.of(year, month, day)
    }
}