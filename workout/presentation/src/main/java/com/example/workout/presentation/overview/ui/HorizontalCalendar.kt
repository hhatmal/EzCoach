package com.example.workout.presentation.overview.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.presentation.designsystem.EzCoachTheme
import com.example.workout.presentation.overview.model.CalendarItemUi
import com.example.workout.presentation.overview.model.CalendarUi

@Composable
private fun HorizontalCalendar(calendarUi: CalendarUi, onClick: () -> Unit) {
    LazyRow {
        items(items = calendarUi.calendarItems) { calendarDate ->
            ContentItem(
                calendarItemUi = calendarDate,
                onClick = onClick
            )
        }
    }
}

@Composable
fun ContentItem(calendarItemUi: CalendarItemUi, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .padding(horizontal = 4.dp, vertical = 4.dp),
        colors = CardDefaults.cardColors(
            containerColor = if (calendarItemUi.isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.secondary,
        ),
        onClick = onClick
    ) {
        Column(
            modifier = Modifier
                .width(48.dp)
                .height(48.dp)
                .padding(4.dp)
        ) {
            Text(
                text = calendarItemUi.dayWord,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodySmall
            )
            Text(
                text = calendarItemUi.dayNumber,
                modifier = Modifier.align(Alignment.CenterHorizontally),
                style = MaterialTheme.typography.bodyMedium,
            )

            if (calendarItemUi.isComplete) {
                val primaryColor = MaterialTheme.colorScheme.onPrimary
                Canvas(modifier = Modifier.size(100.dp), onDraw = {
                    drawCircle(color = primaryColor)
                })
            }
        }
    }
}

@Preview
@Composable
fun ContentItemPreview() {
    EzCoachTheme {
        val calendarItems = listOf(
            CalendarItemUi(id = 1, year = 1, month = 5, dayNumber = "21", dayWord = "Mon", isSelected = false, isComplete = true),
            CalendarItemUi(id = 2, year = 2, month = 5, dayNumber = "22", dayWord = "Tue", isSelected = false, isComplete = true),
            CalendarItemUi(id = 3, year = 3, month = 5, dayNumber = "23", dayWord = "Wed", isSelected = true, isComplete = true),
            CalendarItemUi(id = 4, year = 4, month = 5, dayNumber = "24", dayWord = "Thur", isSelected = false, isComplete = false),
            CalendarItemUi(id = 5, year = 5, month = 5, dayNumber = "25", dayWord = "Fri", isSelected = false, isComplete = false)
        )
        val calendarUi = CalendarUi(
            selectedItem = calendarItems.get(0),
            calendarItems = calendarItems
        )
        HorizontalCalendar(
            calendarUi = calendarUi,
            onClick = {}
        )
    }
}