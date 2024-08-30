package com.example.workout.presentation.overview.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.workout.presentation.R
import com.example.core.presentation.designsystem.CheckIcon
import com.example.workout.presentation.overview.model.ExerciseUi
import com.example.workout.presentation.overview.model.ExerciseSetUi

@Composable
fun ExerciseCard(
    onCardClick: () -> Unit,
    exerciseUi: ExerciseUi
) {
    Surface(
        shape = RoundedCornerShape(24.dp),
        onClick = onCardClick,
        color = MaterialTheme.colorScheme.onPrimary
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            AsyncImage(
                model = exerciseUi.imageUrl,
                contentDescription = null,
                modifier = Modifier.size(150.dp)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = exerciseUi.name,
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = "${exerciseUi.setCount} sets",
                    fontWeight = FontWeight.SemiBold,
                    style = MaterialTheme.typography.labelLarge
                )
            }

            if (exerciseUi.complete) {
                Icon(
                    imageVector = CheckIcon,
                    modifier = Modifier.size(24.dp),
                    contentDescription = stringResource(id = R.string.test),
                    tint = Color.Green
                )
            }
        }
    }
}

@Preview
@Composable
fun ExerciseCardPreview() {
    ExerciseCard(
        onCardClick = {},
        exerciseUi = ExerciseUi(
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
        )
    )
}