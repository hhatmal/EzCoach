@file:Suppress("OPT_IN_USAGE")

package com.example.workout.presentation.overview.viewmodel

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.workout.domain.CalendarDataProcessor
import com.example.workout.domain.Exercise
import com.example.workout.domain.ExerciseSet
import com.example.workout.domain.Workout
import com.example.workout.domain.WorkoutDataProcessor
import com.example.workout.domain.WorkoutRepository
import com.example.workout.presentation.overview.OverviewAction
import com.example.workout.presentation.overview.mapper.toCalendarItemsUi
import com.example.workout.presentation.overview.mapper.toWorkout
import com.example.workout.presentation.overview.mapper.toWorkoutUi
import com.example.workout.presentation.overview.model.CalendarItemUi
import com.example.workout.presentation.overview.model.CalendarUi
import com.example.workout.presentation.overview.model.OverviewState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flattenMerge
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.merge
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDate
import java.time.ZoneId

@RequiresApi(Build.VERSION_CODES.O)
class OverviewViewModel(
    private val workoutDataProcessor: WorkoutDataProcessor,
    private val calendarDataProcessor: CalendarDataProcessor,
    private val repository: WorkoutRepository,
) : ViewModel() {
    private val _state = MutableStateFlow(
        OverviewState(
            calendarUi = CalendarUi(
                calendarItems = listOf(
                    CalendarItemUi(
                        id = 1,
                        year = 1,
                        month = 5,
                        dayNumber = "1",
                        dayWord = "Mon",
                        isSelected = false,
                        isComplete = true
                    ),
                    CalendarItemUi(
                        id = 2,
                        year = 2,
                        month = 5,
                        dayNumber = "2",
                        dayWord = "Tue",
                        isSelected = false,
                        isComplete = true
                    ),
                    CalendarItemUi(
                        id = 3,
                        year = 3,
                        month = 5,
                        dayNumber = "3",
                        dayWord = "Wed",
                        isSelected = false,
                        isComplete = true
                    ),
                    CalendarItemUi(
                        id = 4,
                        year = 4,
                        month = 5,
                        dayNumber = "4",
                        dayWord = "Thur",
                        isSelected = false,
                        isComplete = false
                    ),
                    CalendarItemUi(
                        id = 5,
                        year = 5,
                        month = 5,
                        dayNumber = "5",
                        dayWord = "Fri",
                        isSelected = false,
                        isComplete = false
                    )
                ),
                selectedItem = CalendarItemUi(
                    id = 1,
                    year = 1,
                    month = 5,
                    dayNumber = "1",
                    dayWord = "Mon",
                    isSelected = false,
                    isComplete = true
                )
            )
        )
    )
    val state: StateFlow<OverviewState> = _state

    init {
        val currentDate = LocalDate.now()
        val calendarItems = calendarDataProcessor.getCalendarItems(currentDate)
        val calendarItemsUi = calendarItems.toCalendarItemsUi()

        // fetch workout for selected date
        state
            .combine(
                repository.getWorkout()
                    .filter { it.date.isNotEmpty() }
            ) { currentState, workout ->
                val selectedItem = currentState.calendarUi.selectedItem
                val selectedDate = calendarDataProcessor.getLocalDate(
                    selectedItem.year,
                    selectedItem.month,
                    selectedItem.dayNumber.toInt()
                )
                val workoutLocalDate =
                    Instant.ofEpochMilli(workout.date.toLong()).atZone(ZoneId.systemDefault())
                        .toLocalDate()

                if (!selectedDate.isEqual(workoutLocalDate)) {
                    return@combine Workout(
                        id = 0,
                        date = "",
                        exercises = listOf()
                    )
                }
                return@combine workout
            }
            .onEach { workout ->
                val workoutUi = workout.toWorkoutUi()
                _state.value = _state.value.copy(currentWorkout = workoutUi)
            }.launchIn(viewModelScope)

        // default selected will be current date
        val updatedCalendarUi = _state.value.calendarUi.copy(
            calendarItems = calendarItemsUi
        )

        _state.value = _state.value.copy(
            calendarUi = updatedCalendarUi
        )

        // select current date as current date initially
        for (calendarItemUi in calendarItemsUi) {
            if (LocalDate.of(
                    calendarItemUi.year,
                    calendarItemUi.month,
                    calendarItemUi.dayNumber.toInt()
                ) == currentDate
            ) {
                onAction(OverviewAction.OnDateClick(calendarItemUi))
            }
        }
    }

    fun onAction(action: OverviewAction) {
        when (action) {
            is OverviewAction.OnDateClick -> {
                // update selected item's state
                // should probably filter by date
                val selectedItem = _state.value.calendarUi
                    .calendarItems
                    .single { e ->
                        e.equals(action.calendarItemUi)
                    }
                    .copy(
                        isSelected = true
                    )

                val selectedDate = calendarDataProcessor.getLocalDate(
                    selectedItem.year,
                    selectedItem.month,
                    selectedItem.dayNumber.toInt()
                )
                val calendarItemsUi =
                    calendarDataProcessor.getCalendarItems(selectedDate).toCalendarItemsUi()

                // replace in list
                val updatedCalendarItemsUi: MutableList<CalendarItemUi> = mutableListOf()
                calendarItemsUi.forEach {
                    if (it.equals(selectedItem)) {
                        updatedCalendarItemsUi.add(selectedItem)
                    } else {
                        updatedCalendarItemsUi.add(it)
                    }
                }

                // update state list and selected item
                val updatedCalendarUi = CalendarUi(
                    selectedItem = selectedItem,
                    calendarItems = updatedCalendarItemsUi
                )
                _state.value = _state.value.copy(
                    calendarUi = updatedCalendarUi
                )

                viewModelScope.launch {
                    repository.fetchWorkout(selectedDate)
                }
            }

            is OverviewAction.OnCardClick -> {}
            is OverviewAction.OnDetailSetRepClick -> {
                viewModelScope.launch {
                    // current rep == target rep -> decrease
                    val workoutId = _state.value.currentWorkout.id
                    val exerciseName = action.exerciseRep.first
                    val setId = action.exerciseRep.second
                    val exercise =
                        _state.value.currentWorkout.exercises.single { e -> e.name == exerciseName }
                    val set = exercise.sets?.single { s -> s.id == setId }

                    val updatedRepCount = set?.let {
                        workoutDataProcessor.updatedRepCount(
                            targetRepCount = it.targetReps,
                            currentRepCount = set.completedReps
                        )
                    }

                    // TODO: this is a lot of work to just update a nested data object, maybe better way? use mutable list?
                    val targetWorkout = _state.value.currentWorkout.toWorkout()
                    val targetExercise = targetWorkout.exercises
                        .single { e -> e.name == exerciseName }

                    // need to copy exercise set list to exercise to deep copy
                    val updatedTargetSetList: MutableList<ExerciseSet> = mutableListOf()
                    targetExercise.exerciseSets?.forEach { exerciseSet ->
                        if (exerciseSet.id == setId) {
                            updatedTargetSetList.add(
                                exerciseSet.copy(
                                    completedReps = updatedRepCount!!
                                )
                            )
                        } else {
                            updatedTargetSetList.add(exerciseSet)
                        }
                    }
                    val updatedExercise = targetExercise.copy(
                        exerciseSets = updatedTargetSetList
                    )
                    val updatedExerciseList: MutableList<Exercise> = mutableListOf()
                    targetWorkout.exercises.forEach {
                        if (it.name == exerciseName) {
                            updatedExerciseList.add(updatedExercise)
                        } else {
                            updatedExerciseList.add(it)
                        }
                    }
                    val updatedWorkout = targetWorkout.copy(
                        exercises = updatedExerciseList
                    )

                    updatedRepCount?.let {
                        repository.updateExerciseSetRepCount(
                            workout = updatedWorkout
                        )
                    }
                }
            }
        }
    }
}