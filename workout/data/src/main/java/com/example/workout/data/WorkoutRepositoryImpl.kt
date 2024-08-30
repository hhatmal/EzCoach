package com.example.workout.data

import android.os.Build
import androidx.annotation.RequiresApi
import com.example.core.domain.util.DataError
import com.example.core.domain.util.EmptyResult
import com.example.core.domain.util.Result
import com.example.core.domain.util.asEmptyDataResult
import com.example.workout.domain.Workout
import com.example.workout.domain.WorkoutRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate
import java.time.ZoneId

class WorkoutRepositoryImpl(
    private val applicationScope: CoroutineScope,
    private val remoteWorkoutDataSource: RemoteWorkoutDataSource,
    private val localWorkoutDataSource: LocalWorkoutDataSource,
) : WorkoutRepository {

    override fun getWorkout(): Flow<Workout> {
        return localWorkoutDataSource.getWorkout()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun fetchWorkout(localDate: LocalDate): EmptyResult<DataError> {
        val epochTime = localDate.atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli()
        return when (val result = remoteWorkoutDataSource.getWorkout(epochTime)) {
            is Result.Error -> {
                result.asEmptyDataResult()
            }

            is Result.Success -> {
                applicationScope.async {
                    localWorkoutDataSource.upsertWorkout(result.data).asEmptyDataResult()
                }.await()
            }
        }
    }

    override suspend fun updateExerciseSetRepCount(
        workout: Workout,
    ): EmptyResult<DataError> {
        val localResult = localWorkoutDataSource.upsertWorkout(workout)
        if (localResult !is Result.Success) {
            return localResult.asEmptyDataResult()
        }

        val workoutWithId = workout.copy(id = localResult.data)
        val remoteResult = remoteWorkoutDataSource.putWorkout(
            workout = workoutWithId
        )

        return when (remoteResult) {
            is Result.Error -> {
                /*                applicationScope.launch {
                                    syncRunScheduler.scheduleSync(
                                        type = SyncRunScheduler.SyncType.CreateRun(
                                            run = runWithId,
                                            mapPictureBytes = mapPicture
                                        )
                                    )
                                }.join()*/
                Result.Success(Unit)
            }

            is Result.Success -> {
                applicationScope.async {
                    localWorkoutDataSource.upsertWorkout(remoteResult.data).asEmptyDataResult()
                }.await()
            }
        }
    }
}