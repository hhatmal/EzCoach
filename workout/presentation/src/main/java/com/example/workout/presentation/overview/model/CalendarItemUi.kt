package com.example.workout.presentation.overview.model

data class CalendarItemUi(
    val id: Int,
    val year: Int,
    val month: Int,
    val dayNumber: String,
    val dayWord: String,
    val isSelected: Boolean,
    val isComplete: Boolean
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true

        other as CalendarItemUi

        if (year != other.year) return false
        if (month != other.month) return false
        if (dayNumber != other.dayNumber) return false
        if (dayWord != other.dayWord) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + year
        result = 31 * result + month
        result = 31 * result + dayNumber.hashCode()
        result = 31 * result + dayWord.hashCode()
        result = 31 * result + isSelected.hashCode()
        result = 31 * result + isComplete.hashCode()
        return result
    }
}