
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.example.core.presentation.designsystem.EzCoachTheme
import com.example.workout.presentation.R
import com.example.workout.presentation.overview.model.ExerciseUi
import com.example.workout.presentation.overview.model.ExerciseSetUi

@Composable
fun ExerciseDetail(
    state: ExerciseUi,
    setRowsComposable: @Composable () -> Unit,
) {
    Surface(
        modifier = Modifier
            .fillMaxSize(),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                text = state.name,
                style = MaterialTheme.typography.headlineLarge
            )
            Text(
                text = state.instructions,
                style = MaterialTheme.typography.bodyMedium
            )
            AsyncImage(
                model = state.imageUrl,
                contentDescription = null,
                modifier = Modifier.fillMaxWidth()
            )
            Column(
                modifier = Modifier.fillMaxWidth()
            ) {
                // title
                SetRowHeader(
                    setNumber = "Set",
                    targetWeight = "Weight",
                    targetReps = "Reps",
                    completedReps = "Status"
                )
                setRowsComposable()
            }
        }
    }
}

@Composable
fun SetRowHeader(
    setNumber: String,
    targetWeight: String,
    targetReps: String,
    completedReps: String,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Text(
            text = setNumber,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1F),
            textAlign = TextAlign.Center
        )
        Text(
            text = targetWeight,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1F),
            textAlign = TextAlign.Center
        )
        Text(
            text = targetReps,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1F),
            textAlign = TextAlign.Center,
        )
        Text(
            text = completedReps,
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.weight(1F),
            textAlign = TextAlign.Center
        )
    }
}

@Composable
fun SetRowItem(
    onClick: () -> Unit,
    setNumber: String,
    targetWeight: String,
    targetReps: String,
    completedReps: String,
    setComplete: Boolean,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp),
        horizontalArrangement = Arrangement.SpaceAround,
    ) {
        Text(
            text = setNumber,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.weight(1F),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = targetWeight,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.weight(1F),
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
        Text(
            text = targetReps,
            style = MaterialTheme.typography.labelLarge,
            modifier = Modifier.weight(1F),
            fontSize = 16.sp,
            textAlign = TextAlign.Center,
        )
        Box(
            modifier = Modifier
                .weight(1F)
                .then(
                    if (completedReps == (-1).toString()) {
                        Modifier.border(
                            width = 2.dp,
                            color = Color.Red,
                            shape = CircleShape
                        )
                    } else {
                        Modifier.background(
                            color = if (setComplete) Color.Green else Color.Red,
                            shape = CircleShape
                        )
                    }
                )
                .clickable {
                    onClick()
                },
            contentAlignment = Alignment.Center,
        ) {
            if (completedReps >= (-1).toString()) {
                Text(
                    text = completedReps,
                    style = MaterialTheme.typography.labelLarge,
                    fontSize = 16.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@Preview
@Composable
fun ExerciseDetailPreview() {
    EzCoachTheme {
        val state = ExerciseUi(
            id = 1,
            name = "Squats",
            instructions = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it to make a type specimen book. It has survived not only five centuries, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised in the 1960s with the release of Letraset sheets containing Lorem Ipsum passages, and more recently with desktop publishing software like Aldus PageMaker including versions of Lorem Ipsum.",
            imageUrl = "https://barbend.com/wp-content/uploads/2019/05/Brarbend.com-Article-Image-760x427-A-person-positioned-to-perform-a-bench-press.png",
            comments = "my comment",
            sets = listOf(
                ExerciseSetUi(
                    id = 1,
                    exerciseId = 1,
                    targetWeight = 135F,
                    targetReps = 5,
                    completedReps = 5
                ),
                ExerciseSetUi(
                    id = 2,
                    exerciseId = 1,
                    targetWeight = 135F,
                    targetReps = 5,
                    completedReps = 4
                )
            )
        )
        ExerciseDetail(state = state) {
            // need these callbacks to be here or else can't access
            state.sets?.let { sets ->
                for (set in sets) {
                    SetRowItem(
                        onClick = { },
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