@file:OptIn(ExperimentalMaterial3AdaptiveApi::class)

package com.example.workout.presentation.overview

import ExerciseDetail
import SetRowItem
import android.os.Build
import androidx.activity.compose.BackHandler
import androidx.annotation.RequiresApi
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.ExperimentalMaterial3AdaptiveApi
import androidx.compose.material3.adaptive.layout.AnimatedPane
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffold
import androidx.compose.material3.adaptive.layout.ListDetailPaneScaffoldRole
import androidx.compose.material3.adaptive.navigation.ThreePaneScaffoldNavigator
import androidx.compose.material3.adaptive.navigation.rememberListDetailPaneScaffoldNavigator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.core.presentation.designsystem.EzCoachTheme
import com.example.workout.presentation.overview.model.CalendarItemUi
import com.example.workout.presentation.overview.model.CalendarUi
import com.example.workout.presentation.overview.model.ExerciseUi
import com.example.workout.presentation.overview.model.OverviewState
import com.example.workout.presentation.overview.model.ExerciseSetUi
import com.example.workout.presentation.overview.model.WorkoutUi
import com.example.workout.presentation.overview.ui.ContentItem
import com.example.workout.presentation.overview.ui.ExerciseCard
import com.example.workout.presentation.overview.viewmodel.OverviewViewModel
import org.koin.androidx.compose.koinViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun OverviewScreenRoot(viewModel: OverviewViewModel = koinViewModel()) {
    val navigator = rememberListDetailPaneScaffoldNavigator<ExerciseUi>()
    BackHandler(navigator.canNavigateBack()) {
        navigator.navigateBack()
    }

    val overviewState by viewModel.state.collectAsState()
    OverviewScreen(
        state = OverviewState(
            calendarUi = overviewState.calendarUi,
            currentWorkout = overviewState.currentWorkout
        ),
        navigator = navigator,
        onAction = { action ->
            when (action) {
                is OverviewAction.OnCardClick -> navigator.navigateTo(
                    ListDetailPaneScaffoldRole.Detail,
                    action.exerciseUi
                )
                else -> viewModel.onAction(action)
            }
        }
    )
}

@Composable
fun OverviewScreen(
    state: OverviewState,
    navigator: ThreePaneScaffoldNavigator<ExerciseUi>,
    onAction: (OverviewAction) -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = "Workout",
            style = MaterialTheme.typography.titleLarge
        )

        // calendar
        LazyRow(
            modifier = Modifier.border(
                width = 2.dp,
                color = MaterialTheme.colorScheme.onBackground,
                shape = RoundedCornerShape(8.dp))
        ) {
            items(items = state.calendarUi.calendarItems) {
                ContentItem(
                    calendarItemUi = it,
                    onClick = { onAction(OverviewAction.OnDateClick(it)) }
                )
            }
        }
        ListDetailPaneScaffold(
            directive = navigator.scaffoldDirective,
            value = navigator.scaffoldValue,
            listPane = {
                AnimatedPane {
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(16.dp)
                    ) {
                        itemsIndexed(
                            items = state.currentWorkout.exercises
                        ) { index, it ->
                            ExerciseCard(
                                onCardClick = { onAction(OverviewAction.OnCardClick(it)) },
                                exerciseUi = it
                            )
                        }
                    }
                }
            },
            detailPane = {
                AnimatedPane {
                    // Show the detail pane content if selected item is available
                    // not sure how to get updated value, this is the remembered value
                    // might be good to not use this library need to use workaround
                    navigator.currentDestination?.content?.let { exercise ->
                        val exerciseUi = state.currentWorkout.exercises.single { e -> e.name == exercise.name }
                        ExerciseDetail(state = exerciseUi) {
                            // need these callbacks to be here or else can't access
                            exerciseUi.sets?.let { sets ->
                                for (set in sets) {
                                    SetRowItem(
                                        onClick = {
                                            onAction(OverviewAction.OnDetailSetRepClick(Pair(exercise.name, set.id)))
                                        },
                                        setNumber = set.id.toString(),
                                        targetWeight = set.targetWeight.toString(),
                                        targetReps = set.targetReps.toString(),
                                        completedReps = set.completedReps.toString(),
                                        setComplete = set.setComplete
                                    )
                                }
                            }
                        }
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun OverviewScreenPreview() {
    EzCoachTheme {
        val calendarItems = listOf(
            CalendarItemUi(id = 1, year = 1, month = 10, dayNumber = "1", dayWord = "Mon", isSelected = false, isComplete = true),
            CalendarItemUi(id = 2, year = 2, month = 10, dayNumber = "2", dayWord = "Tue", isSelected = false, isComplete = true),
            CalendarItemUi(id = 3, year = 3, month = 10, dayNumber = "3", dayWord = "Wed", isSelected = true, isComplete = true),
            CalendarItemUi(id = 4, year = 4, month = 10, dayNumber = "4", dayWord = "Thur", isSelected = false, isComplete = false),
            CalendarItemUi(id = 5, year = 5, month = 10, dayNumber = "5", dayWord = "Fri", isSelected = false, isComplete = false)
        )
        val calendarUi = CalendarUi(
            selectedItem = calendarItems.get(0),
            calendarItems = calendarItems
        )

        val exerciseItems = listOf(
            ExerciseUi(
                id = 1,
                name = "Squats",
                instructions = "BLABLABLA",
                imageUrl = "https://barbend.com/wp-content/uploads/2019/05/Brarbend.com-Article-Image-760x427-A-person-positioned-to-perform-a-bench-press.png",
                comments = "my comment",
                sets = listOf(
                    ExerciseSetUi(
                        id = 1,
                        exerciseId = 1,
                        targetWeight = 135F,
                        targetReps = 5,
                        completedReps = 5
                    )
                )
            ),
            ExerciseUi(
                id = 2,
                name = "Bench Press",
                instructions = "BLABLABLA",
                imageUrl = "https://barbend.com/wp-content/uploads/2019/05/Brarbend.com-Article-Image-760x427-A-person-positioned-to-perform-a-bench-press.png",
                comments = "my comment",
                sets = listOf(
                    ExerciseSetUi(
                        id = 1,
                        exerciseId = 1,
                        targetWeight = 135F,
                        targetReps = 5,
                        completedReps = 4
                    )
                )
            )
        )
        val currentWorkout = WorkoutUi(
            id = 1,
            date = "abc",
            exercises = exerciseItems
        )

        val navigator = rememberListDetailPaneScaffoldNavigator<ExerciseUi>()
        BackHandler(navigator.canNavigateBack()) {
            navigator.navigateBack()
        }
        OverviewScreen(
            state = OverviewState(
                calendarUi = calendarUi,
                currentWorkout = currentWorkout
            ),
            navigator = navigator,
            onAction = { action ->
                when (action) {
                    is OverviewAction.OnCardClick -> navigator.navigateTo(
                        ListDetailPaneScaffoldRole.Detail,
                        action.exerciseUi
                    )
                    else -> Unit
                }
                //viewModel.onAction(action)
            }
        )
    }
}